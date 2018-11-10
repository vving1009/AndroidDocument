package com.pf.代理.静态代理;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 * 中介
 */
public class HireHouseProxy implements HireHouse {

    private HireHouse hireHouse;

    public HireHouseProxy(HireHouse hireHouse) {
        this.hireHouse = hireHouse;
    }

    @Override
    public void hire() {
        System.out.println("收中介费");

        // 帮他租房
        hireHouse.hire();

        System.out.println("扣押金");
    }
}