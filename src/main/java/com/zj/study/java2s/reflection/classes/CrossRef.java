package com.zj.study.java2s.reflection.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * CrossRef prints a cross-reference about all classes named in argv. For each
 * class, all public fields and methods are listed. "Reflectance" is used to
 * look up the information.
 *
 * It is expected that the output will be post-processed e.g., with sort and
 * awk/perl. Try: java CrossRef | uniq | # squeeze out polymorphic forms early
 * sort | awk '$2=="method" { ... }' > crossref-methods.txt The part in "{ ...
 * }" is left as an exercise for the reader. :-(
 *
 * @author Ian Darwin, Ian@DarwinSys.com
 * @version $Id: CrossRef.java,v 1.16 2003/04/08 20:01:44 ian Exp $
 */
public class CrossRef extends APIFormatter {

	/**
	 * Simple main program, construct self, process each .ZIP file found in
	 * CLASSPATH or in argv.
	 */
	public static void main(String[] argv) throws IOException {
		CrossRef xref = new CrossRef();
		xref.doArgs(argv);
	}

	/**
	 * Print the fields and methods of one class.
	 */
	protected void doClass(Class c) {
		int i, mods;
		startClass(c);
		try {
			Field[] fields = c.getDeclaredFields();
			Arrays.sort(fields, new Comparator() {
				public int compare(Object o1, Object o2) {
					return ((Field) o1).getName().compareTo(((Field) o2).getName());
				}
			});
			for (i = 0; i < fields.length; i++) {
				Field field = (Field) fields[i];
				if (!Modifier.isPrivate(field.getModifiers()))
					putField(field, c);
				// else System.err.println("private field ignored: " + field);
			}

			Method methods[] = c.getDeclaredMethods();
			// Arrays.sort(methods);
			for (i = 0; i < methods.length; i++) {
				if (!Modifier.isPrivate(methods[i].getModifiers()))
					putMethod(methods[i], c);
				// else System.err.println("pvt: " + methods[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		endClass();
	}

	/** put a Field's information to the standard output. */
	protected void putField(Field fld, Class c) {
		println(fld.getName() + " field " + c.getName() + " ");
	}

	/** put a Method's information to the standard output. */
	protected void putMethod(Method method, Class c) {
		String methName = method.getName();
		println(methName + " method " + c.getName() + " ");
	}

	/**
	 * Print the start of a class. Unused in this version, designed to be overridden
	 */
	protected void startClass(Class c) {
	}

	/**
	 * Print the end of a class. Unused in this version, designed to be overridden
	 */
	protected void endClass() {
	}

	/** Convenience routine, short for System.out.println */
	protected final void println(String s) {
		System.out.println(s);
	}
}