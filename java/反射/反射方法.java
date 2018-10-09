package com.pf.反射;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 */
public class 反射方法 {

    public static void main(String[] args) throws Exception {
        Class<?> clz = Class.forName("com.pf.反射.Person");

        // 获取构造方法，参数是构造方法的参数，如果没有参数可以传空数组或者null，建议传空数组
        Constructor constructor = clz.getDeclaredConstructor(new Class[]{String.class, int.class});
        Object instance = constructor.newInstance("张三", 20);
        System.out.println(instance);

        // 获取普通的对象方法,第一个参数是要获取的方法名，第二个参数是方法的参数,如果没有参数可以传空数组或者null
        Method setName = clz.getDeclaredMethod("setName", new Class[]{String.class});
        // 设置可以访问，相当于获取了权限
        setName.setAccessible(true);
        // 执行这个方法,第一个参数是执行那个对象的这个方法，第二个是参数
        Object invoke = setName.invoke(instance, "李四");
        System.out.println(instance + " -- " + invoke);

        // 获取静态方法
        Method printInfo = clz.getDeclaredMethod("printInfo", new Class[]{});
        printInfo.setAccessible(true);
        Object printInfoInvoke = printInfo.invoke(null, new Object[]{});
        System.out.println(instance + " -- " + printInfoInvoke);
    }
}