package com.pf.代理.动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 * 代理
 */
public class Proxy implements InvocationHandler {

    private Object hire;

    public Proxy(Object hire) {
        this.hire = hire;
    }

    /**
     * @param proxy  被代理的对象，必须实现接口
     * @param method 被代理的对象的方法
     * @param args   被代理的对象方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前置工作");

        // 帮他租
        Object invoke = method.invoke(hire, args);

        System.out.println("后置工作");
        return invoke;
    }
}