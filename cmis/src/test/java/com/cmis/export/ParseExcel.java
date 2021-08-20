package com.cmis.export;

import com.alibaba.fastjson.JSON;


public class ParseExcel {

    public static void main(String[] args) {
        String str = "";

        JsonRootBean response = JSON.parseObject(str, JsonRootBean.class);
        System.out.println(response.getStatus());


    }

}
