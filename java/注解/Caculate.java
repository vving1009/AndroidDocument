package com.pf.注解;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 */
public class Caculate {

    /**
     * 相加方法
     *
     * @param a 第一个参数
     * @param b 第二个参数
     * @return 相加之后的结果
     */
    @AnnoTest2("相加的结果是")
    private int add(int a, int b) {
        return a + b;
    }
}