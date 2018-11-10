package com.pf.代理.动态代理;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 */
public class Client {

    public static void main(String[] args) {
        // 被代理的接口实现类的对象
        Hire hire = new HireImpl();
        //  创建代理对象
        Hire hire1 = (Hire) java.lang.reflect.Proxy.newProxyInstance(hire.getClass().getClassLoader(),
                hire.getClass().getInterfaces(),
                new Proxy(hire));
        hire1.hire("房子");

        List list = new ArrayList();
        List list1 = (List) java.lang.reflect.Proxy.newProxyInstance(list.getClass().getClassLoader(),
                list.getClass().getInterfaces(),
                new Proxy(list));
        list1.add("你好");
    }
}