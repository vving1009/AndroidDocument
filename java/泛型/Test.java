package com.pf.泛型;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年03月10日
 */
public class Test {
    public static void main(String[] args) {
        String str = t(String.class);
        System.out.println("内容：" + str);
    }

    public static <T> T t(Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}