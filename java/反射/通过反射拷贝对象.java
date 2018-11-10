package com.pf.反射;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 */
public class 通过反射拷贝对象 {

    public static void main(String[] args) {
        Person p = new Person("张三", 20);
        Person p2 = new Person("李四", 30);
        p.setP(p2);
        System.out.println(p);
        Object o = copyObject(p);
        System.out.println(o);
    }

    public static Object copyObject(Object o) {
        if (o == null) {
            return null;
        }
        Class<?> clz = o.getClass();
        try {
            // 先搞一个构造方法，弄一个实例出来
            Constructor<?> constructor = clz.getDeclaredConstructor(new Class[]{});
            Object instance = constructor.newInstance();
            // 获取所有属性
            Field[] fields = clz.getDeclaredFields();
            for (Field f : fields) {
                // 设置可以访问
                f.setAccessible(true);
                // 获取值
                Object value = f.get(o);
                // 获取属性的类型
                Class<?> type = f.getType();
                // 是否是基础类型
                boolean checkType = type == Byte.class || type == Short.class || type == Integer.class || type == Long.class
                        || type == Float.class || type == Double.class || type == Boolean.class || type == Character.class
                        || type == byte.class || type == short.class || type == int.class || type == long.class
                        || type == float.class || type == double.class || type == boolean.class || type == char.class
                        || type == String.class;
                if (checkType) {
                    // 如果是基础类型直接赋值
                    f.set(instance, value);
                } else {
                    // 如果是对象类型，需要重新new一个对象出来
                    f.set(instance, copyObject(value));
                }
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}