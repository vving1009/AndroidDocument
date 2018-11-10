package com.pf.反射;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 */
public class 获取类 {

    public static void main(String[] args) throws Exception {

        // 第一种方式
        Class<?> forName = Class.forName("com.pf.反射.Person");

        // 第二种方式
        Object o = new Object();
        Class<? extends Object> pClass = o.getClass();

        // 第三种方式
        Class<Object> oClass = Object.class;

        System.out.println("");
    }
}
