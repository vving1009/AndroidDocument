package com.pf.代理.静态代理;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 */
public class Client {

    public static void main(String[] args){
        // 要租房的人
        HireHouse hireHouse = new HireHouseImpl();
        // 中介
        HireHouse hireHouse1 = new HireHouseProxy(hireHouse);

        hireHouse1.hire();
    }
}