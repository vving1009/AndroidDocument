package com.pf.注解;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 */

/**
 * RetentionPolicy:注解的策略,有三个值
 * SOURCE:     只在源代码中有效，编译时就不在了
 * CLASS:      编译时存在，运行时就不在了
 * RUNTIME:    运行时存在
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 * 指明使用范围，这里是只能在方法上使用
 */
@Target(ElementType.METHOD)
@AnnoTest2("你好啊")
public @interface AnnoTest {

    /**
     * 注解内部可以定义值，这里面的值既不是属性也不是方法
     * 语法：数据类型 值的名字();
     *
     * @return
     */
    String name();

    Class[] types();

    HttpMethod httpMethod();
}