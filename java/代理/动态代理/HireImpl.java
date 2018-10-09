package com.pf.代理.动态代理;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 * 真正租的人
 */
public class HireImpl implements Hire {

    @Override
    public void hire(String something) {
        System.out.println("租" + something);
    }
}