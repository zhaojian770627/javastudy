package com.zj.study.framework.lock.single;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 * 饿汉式
 */
public class SingleEHan {
    public static SingleEHan singleEHan = new SingleEHan();
    private SingleEHan(){}

}
