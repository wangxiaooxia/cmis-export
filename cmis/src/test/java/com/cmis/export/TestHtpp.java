package com.cmis.export;

import java.math.BigDecimal;

public class TestHtpp {
    public static void main(String[] args) {
        BigDecimal payMoney = new BigDecimal(100);
        BigDecimal yMoneyOne = new BigDecimal(80);
        BigDecimal yMoneyTow = new BigDecimal(30);
        BigDecimal yMoneyThree = new BigDecimal(20);

        System.out.println("账号1:"+yMoneyOne);
        System.out.println("账号2:"+payMoney.subtract(yMoneyOne));

        payMoney = new BigDecimal(120);
        System.out.println("账号1:"+yMoneyOne);
        System.out.println("账号2:"+yMoneyTow);
        System.out.println("账号3:"+yMoneyTow.subtract(yMoneyThree));
    }
}
