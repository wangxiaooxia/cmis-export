package com.cmis.export.controller;

import com.alibaba.excel.EasyExcel;
import com.cmis.export.entity.BizLine;
import com.cmis.export.entity.XmlData;
import com.cmis.export.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/export")
public class ExportExcelController {
    @Autowired
    private ExportService exportService;

    @RequestMapping("/testExcel")
    public String testExcel(){
        List<BizLine> bizLines = exportService.queryAll();
        if(bizLines == null || bizLines.size() < 1) {
            System.out.println("暂无数据可导出...");
            return "fail";
        }

        //将数据库中返回的数据组装为excel中需要的数据项
        List<XmlData> dataList = new ArrayList<>();
        bizLines.forEach(e -> {
            XmlData data = new XmlData();
            data.setFirst(e.getSeq() + "");
            data.setSecond(e.getServiceLine());
            data.setThree(e.getServiceLineName());
            dataList.add(data);
        });
        //EasyExcel详见：https://www.yuque.com/easyexcel/doc/write
        String fileName = "D:\\Users\\hacker\\Desktop\\cmis.xlsx";
        EasyExcel.write(fileName, XmlData.class).sheet("cmis").doWrite(dataList);
        return "success";

    }
    @RequestMapping("/exportExcelReport")
    public String exportExcelReport(){

        return "success";
    }
}
