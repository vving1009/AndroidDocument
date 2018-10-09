package com.pf.泛型;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年03月10日
 */
public class 苹果测试 {

    public static void main(String[] args) {

        List<? super 苹果> list = new ArrayList<苹果>();
        list.add(new 红苹果());
        list.add(new 小苹果());
        list.add(new 苹果() {
        });
//        list.add(new 水果(){}); // 这个不可行
        Object object = list.get(0);
        // ? super 苹果 表示 都是苹果或者苹果的父类，也就是水果
        // 在放的时候，红苹果，小苹果，都是苹果，所以可以放
        // 在取的时候，因为不知道具体是什么类型，所以用Object来接收

        List<? extends 苹果> list2 = new ArrayList<苹果>();
//        list2.add(new 红苹果());
//        list2.add(new 小苹果());
//        list2.add(new 水果(){});
//        list2.add(new 苹果(){});
        苹果 苹果 = list2.get(0);
        // ? extends 苹果 表示都是苹果或者苹果的子类
        // 在添加的时候因为不确定是哪一个子类，所以不能将苹果这个父类强行赋值给子类
        // 假如list2里是红苹果，把苹果添加进去就会造成子类接收父类的现象，这是错误的
        // 但是取出来的一定是苹果的子类，所以用苹果接口来接收是可以的
    }

    // 参考: http://www.importnew.com/17006.html
    void extendss() {
        /**
         * // Number "extends" Number (in this context)
         * List<? extends Number> foo3 = new ArrayList<? extends Number>();
         *
         * // Integer extends Number
         * List<? extends Number> foo3 = new ArrayList<? extends Integer>();
         *
         * // Double extends Number
         * List<? extends Number> foo3 = new ArrayList<? extends Double>();
         */
        /**
         * 1、读取操作通过以上给定的赋值语句，你一定能从foo3列表中读取到的元素的类型是什么呢？
         *      你可以读取到Number，因为以上的列表要么包含Number元素，要么包含Number的类元素。
         *      你不能保证读取到Integer，因为foo3可能指向的是List<Double>。
         *      你不能保证读取到Double，因为foo3可能指向的是List<Integer>。
         *
         * 2、写入操作过以上给定的赋值语句，你能把一个什么类型的元素合法地插入到foo3中呢？
         *      你不能插入一个Integer元素，因为foo3可能指向List<Double>。
         *      你不能插入一个Double元素，因为foo3可能指向List<Integer>。
         *      你不能插入一个Number元素，因为foo3可能指向List<Integer>。
         *      你不能往List<? extends T>中插入任何类型的对象，因为你不能保证列表实际指向的类型是什么，你并不能保证列表中实际存储什么类型的对象。
         *      唯一可以保证的是，你可以从中读取到T或者T的子类。
         */
    }

    void supers() {
        /**
         * // Integer is a "superclass" of Integer (in this context)
         * List<? super Integer> foo3 = new ArrayList<Integer>();
         *
         * // Number is a superclass of Integer
         * List<? super Integer> foo3 = new ArrayList<Number>();
         *
         * // Object is a superclass of Integer
         * List<? super Integer> foo3 = new ArrayList<Object>();
         */
        /**
         * 1、读取操作通过以上给定的赋值语句，你一定能从foo3列表中读取到的元素的类型是什么呢？
         *      你不能保证读取到Integer，因为foo3可能指向List<Number>或者List<Object>。
         *      你不能保证读取到Number，因为foo3可能指向List<Object>。
         *      唯一可以保证的是，你可以读取到Object或者Object子类的对象（你并不知道具体的子类是什么）。
         *
         * 2、写入操作通过以上给定的赋值语句，你能把一个什么类型的元素合法地插入到foo3中呢？
         *      你可以插入Integer对象，因为上述声明的列表都支持Integer。
         *      你可以插入Integer的子类的对象，因为Integer的子类同时也是Integer，原因同上。
         *      你不能插入Double对象，因为foo3可能指向ArrayList<Integer>。
         *      你不能插入Number对象，因为foo3可能指向ArrayList<Integer>。
         *      你不能插入Object对象，因为foo3可能指向ArrayList<Integer>
         */
    }
}