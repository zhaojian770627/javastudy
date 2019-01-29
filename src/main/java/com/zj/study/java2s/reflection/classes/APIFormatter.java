package com.zj.study.java2s.reflection.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * <p>
 * APIFormatter reads one or more Zip files, gets all entries from each
 * and, for each entry that ends in ".class", loads it with Class.forName()
 * and hands it off to a doClass(Class c) method declared in a subclass.
 * <br/>TODO<br/>
 * Use GETOPT to control doingStandardClasses, verbosity level, etc.
 * @author  Ian Darwin, Ian@DarwinSys.com
 * @version  $Id: APIFormatter.java,v 1.6 2004/03/14 14:00:34 ian Exp $
 */
abstract class APIFormatter {

  /** True if we are doing classpath, so only do java. and javax. */
  protected static boolean doingStandardClasses = true;
  
  protected int doArgs(String[] argv) throws IOException {
    /** Counter of fields/methods printed. */
    int n = 0;

    // TODO: options
    // -b - process bootclasspath
    // -c - process classpath (default)
    // -s - only process "java." and "javax."

    if (argv.length == 0) {
      // No arguments, look in CLASSPATH
      String s = System.getProperty("java.class.path");
      //  break apart with path sep.
      String pathSep = System.getProperty("path.separator");
      StringTokenizer st = new StringTokenizer(s, pathSep);
      // Process each zip in classpath
      while (st.hasMoreTokens()) {
        String thisFile = st.nextToken();
        System.err.println("Trying path " + thisFile);
        if (thisFile.endsWith(".zip") || thisFile.endsWith(".jar"))
          processOneZip(thisFile);
      }
    } else {
      // We have arguments, process them as zip/jar files
      // doingStandardClasses = false;
      for (int i=0; i<argv.length; i++)
        processOneZip(argv[i]);
    }

    return n;
  }

  /** For each Zip file, for each entry, xref it */
  public void processOneZip(String fileName) throws IOException {
      List entries = new ArrayList();
      ZipFile zipFile = null;

      try {
        zipFile = new ZipFile(new File(fileName));
      } catch (ZipException zz) {
        throw new FileNotFoundException(zz.toString() + fileName);
      }
      Enumeration all = zipFile.entries();

      // Put the entries into the List for sorting...
      while (all.hasMoreElements()) {
        ZipEntry zipEntry = (ZipEntry)all.nextElement();
        entries.add(zipEntry);
      }

      // Sort the entries (by class name)
      // Collections.sort(entries);

      // Process all the entries in this zip.
      Iterator it = entries.iterator();
      while (it.hasNext()) {
        ZipEntry zipEntry = (ZipEntry)it.next();
        String zipName = zipEntry.getName();

        // Ignore package/directory, other odd-ball stuff.
        if (zipEntry.isDirectory()) {
          continue;
        }

        // Ignore META-INF stuff
        if (zipName.startsWith("META-INF/")) {
          continue;
        }

        // Ignore images, HTML, whatever else we find.
        if (!zipName.endsWith(".class")) {
          continue;
        }

        // If doing CLASSPATH, Ignore com.* which are "internal API".
      //   if (doingStandardClasses && !zipName.startsWith("java")){
      //     continue;
      //   }
      
        // Convert the zip file entry name, like
        //  java/lang/Math.class
        // to a class name like
        //  java.lang.Math
        String className = zipName.replace('/', '.').
          substring(0, zipName.length() - 6);  // 6 for ".class"

        // Now get the Class object for it.
        Class c = null;
        try {
          c = Class.forName(className);
        } catch (ClassNotFoundException ex) {
          System.err.println("Error: " + ex);
        }

        // Hand it off to the subclass...
        doClass(c);
      }
  }

  /** Format the fields and methods of one class, given its name.
   */
  protected abstract void doClass(Class c) throws IOException;
}