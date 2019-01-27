package com.zj.study.java2s.reflection.classes;

import java.util.Calendar;

/**
 * Show the class keyword and getClass() method in action. The class keyword can
 * be applied to any type known at compile time.
 * 
 * @author Ian F. Darwin, http://www.darwinsys.com/
 * @version $Id: ClassKeyword.java,v 1.4 2004/02/09 03:33:51 ian Exp $
 */
public class ClassKeyword {
	public static void main(String[] argv) {
		// +
		System.out.println("Trying the ClassName.class keyword:");
		System.out.println("Object class: " + Object.class);
		System.out.println("String class: " + String.class);
		System.out.println("String[] class: " + String[].class);
		System.out.println("Calendar class: " + Calendar.class);
		System.out.println("Current class: " + ClassKeyword.class);
		System.out.println("Class for int: " + int.class);
		System.out.println();

		System.out.println("Trying the instance.getClass() method:");
		System.out.println("Sir Robin the Brave".getClass());
		System.out.println(Calendar.getInstance().getClass());
		// -
	}
}