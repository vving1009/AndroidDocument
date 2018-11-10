package com.pf.代理.静态代理;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 * 真正租房的人
 */
public class HireHouseImpl implements HireHouse {

    @Override
    public void hire() {
        System.out.println("租房");
    }
}