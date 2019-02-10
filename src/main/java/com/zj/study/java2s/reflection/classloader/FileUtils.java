package com.zj.study.java2s.reflection.classloader;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtils {

	static int BUFFER_SIZE = 10 * 1024;

	/**
	 * Attempts to list all the classes in the specified package as determined by
	 * the context class loader...
	 * 
	 * @param pckgname
	 *            the package name to search
	 * @return a list of classes that exist within that package
	 * @throws ClassNotFoundException
	 *             if something went wrong
	 */
	public static List<Class<?>> getClassesInSamePackage(Class<?>... classes) throws ClassNotFoundException {
		ArrayList<Class<?>> result = new ArrayList<Class<?>>();
		if (classes == null || classes.length == 0)
			return result;
		// This will hold a list of directories matching the pckgname. There may be more
		// than one if
		// a package is split over multiple jars/paths
		ArrayList<File> directories = new ArrayList<File>();
		HashMap<File, String> packageNames = null;
		String pckgname = null;
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null) {
				throw new ClassNotFoundException("Can't get class loader.");
			}
			for (Class<?> clazz : classes) {
				// TODO: this would not work in a non OSGi environment
				String syspath = getBundlePath(clazz);
				if (syspath.endsWith("/")) {
					syspath = syspath.substring(0, syspath.length() - 1);
				} else if (syspath.endsWith(".jar")) {
					getClassesInSamePackageFromJar(result, classes, syspath);
					continue;
				}
				pckgname = clazz.getPackage().getName();
				String path = pckgname.replace('.', '/');
				// Ask for all resources for the path
				Enumeration<URL> resources = cld.getResources(path);
				File directory = null;
				while (resources.hasMoreElements()) {
					String path2 = resources.nextElement().getPath();
					if (!path2.contains(syspath)) {
						// needed to get it working on Eclipse 3.5
						if (syspath.indexOf("/bin") < 1) {
							syspath = syspath + "/bin";
						}
						directory = new File(URLDecoder.decode(syspath + path2, "UTF-8"));
					} else
						directory = new File(URLDecoder.decode(path2, "UTF-8"));
					directories.add(directory);
				}
				if (packageNames == null)
					packageNames = new HashMap<File, String>();
				packageNames.put(directory, pckgname);
			}
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(
					pckgname + " does not appear to be a valid package (Null pointer exception)");
		} catch (UnsupportedEncodingException encex) {
			throw new ClassNotFoundException(
					pckgname + " does not appear to be a valid package (Unsupported encoding)");
		} catch (IOException ioex) {
			throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + pckgname);
		}

		// For every directory identified capture all the .class files
		for (File directory : directories) {
			if (directory.exists()) {
				// Get the list of the files contained in the package
				String[] files = directory.list();
				for (String file : files) {
					System.out.println(file);
					// we are only interested in .class files
					if (file.endsWith(".class")) {
						try {
							// removes the .class extension
							result.add(Class
									.forName(packageNames.get(directory) + '.' + file.substring(0, file.length() - 6)));
						} catch (Throwable e) {
							// ignore exception and continue
						}
					}
				}
			} else {
				throw new ClassNotFoundException(
						pckgname + " (" + directory.getPath() + ") does not appear to be a valid package");
			}
		}
		return result;
	}

	/**
	 * Returns the list of classes in the same directories as Classes in
	 * <code>classes</code>.
	 * 
	 * @param result
	 * @param classes
	 * @param jarPath
	 */
	private static void getClassesInSamePackageFromJar(List<Class<?>> result, Class<?>[] classes, String jarPath) {
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarPath);
			// ClassLoader cld = Thread.currentThread().getContextClassLoader();
			Enumeration<JarEntry> en = jarFile.entries();
			while (en.hasMoreElements()) {
				JarEntry entry = en.nextElement();
				String entryName = entry.getName();
				for (Class<?> clazz : classes) {
					String packageName = clazz.getPackage().getName().replace('.', '/');
					if (entryName != null && entryName.endsWith(".class") && entryName.startsWith(packageName)) {
						try {
							Class<?> entryClass = Class
									.forName(entryName.substring(0, entryName.length() - 6).replace('/', '.'));
							if (entryClass != null)
								result.add(entryClass);
						} catch (Throwable e) {
							System.out.println(e);
							// do nothing, just continue processing classes
						}
					}
				}
			}
		} catch (Exception e) {
			result.addAll(Arrays.asList(classes));
		} finally {
			try {
				if (jarFile != null)
					jarFile.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Returns the location of the
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getBundlePath(Class<?> clazz) {
		ProtectionDomain pd = clazz.getProtectionDomain();
		if (pd == null)
			return null;
		CodeSource cs = pd.getCodeSource();
		if (cs == null)
			return null;
		URL url = cs.getLocation();
		if (url == null)
			return null;
		String result = url.getFile();
		return result;
	}
}
