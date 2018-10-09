package com.pf.注解;

import java.lang.reflect.Method;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 */
public class Client {

    public static void main(String[] args) {
//        testMethod();
        testMethod1();
    }

    @AnnoTest2("你好")
    @AnnoTest(name = "你好", types = {String.class, int.class}, httpMethod = HttpMethod.POST)
    public static void testMethod() {
        Caculate c = new Caculate();
//        int result = c.add(1, 2);
//        System.out.println(result);
    }

    @AnnoTest2("你好")
    @AnnoTest(name = "你好", types = {String.class, int.class}, httpMethod = HttpMethod.POST)
    public static void testMethod1() {
        Class<?> clz = Caculate.class;
        try {
            // 拿到类的实例
            Object instance = clz.newInstance();
            // 拿到add方法
            Method addMethod = clz.getDeclaredMethod("add", new Class[]{int.class, int.class});
            // 设置可以访问
            addMethod.setAccessible(true);
            // 判断一个方法是否有某个注解
            boolean isPress = addMethod.isAnnotationPresent(AnnoTest2.class);
            if (isPress) {
                // 获取注解
                AnnoTest2 annoTest2 = addMethod.getAnnotation(AnnoTest2.class);
                // 获取注解上的值
                String value = annoTest2.value();
                Object result = addMethod.invoke(instance, new Object[]{1, 2});
                System.out.println(value + result);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}