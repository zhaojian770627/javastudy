package com.zj.study.jvm;

/*
 * 当前类加载器
 * 每个类都会使用自己的类加载器（即加载自身的类加载器）来加载其他类（所依赖的类）
 * 
 * 线程上下文加载器
 * 
 * 如果没有设置上下文加载器，线程将继承其父线程的上下文类加载器。
 * Java应用运行时的初始线程的上下文加载器是系统类加载器。在线程中运行的代码可以通过该类加载器来加载类与资源。
 * 
 * 线程上下文加载器的重要性：
 * 
 * SPI(Service Provider Interface)
 * 
 * 父ClassLoader可以使用当前线程Thread.currentThread().getContextClassLoader()所指定的classLoader加载的类。
 * 这就改变了父ClassLoader不能使用子ClassLOader或是其他没有直接父子关系的ClassLoader加载的类的情况，即改变了双亲委托模型。
 * 
 * 在双亲委托模型下，类加载器是由下至上的，即下层的类加载器会委托上层进行加载。但是对于SPI来说，有些接口是Java核心库所提供的，
 * 而Java核心库是由启动类加载器来加载的，而这些接口的实现却来自不同的jar包（厂商提供），Java的启动类加载器是不会加载其他来源
 * 的jar包，这样传统的双亲委托模型就无法满足SPI的要求。而通过给当前线程设置上下文类加载器，就可以由设置的上下文类加载器来实现对
 * 于接口实现类的加载。
 * 
 */
public class LookContextClassLoader {

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getContextClassLoader());
		System.out.println(Thread.class.getClassLoader());
		
		System.out.println(LookContextClassLoader.class.getClassLoader());
	}

}
