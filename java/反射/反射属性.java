package com.pf.反射;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 */
public class 反射属性 {

    public static void main(String[] args) throws Exception {
        Class clz = Class.forName("com.pf.反射.Person");
        // 获取构造方法，参数是构造方法的参数，如果没有参数可以传空数组或者null，建议传空数组
        Constructor constructor = clz.getDeclaredConstructor(new Class[]{String.class, int.class});
        Object instance = constructor.newInstance("张三", 20);
        // 拿到name属性
        Field name = clz.getDeclaredField("name");
        // 设置name属性可访问，如果已经是public的就不用了
        name.setAccessible(true);
        // 这里可以修改值
        name.set(instance, "你好");
        // 获取instance上的name的值
        Object nameValue = name.get(instance);

        System.out.println(nameValue);
    }
}