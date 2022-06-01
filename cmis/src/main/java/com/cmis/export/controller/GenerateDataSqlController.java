package com.cmis.export.controller;

import com.cmis.export.entity.STreeDic;
import com.cmis.export.mapper.GenerateSqlMapper;
import com.cmis.export.service.ExportService;
import com.cmis.export.service.GenerateSqlService;
import com.cmis.export.util.ExcleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/generateSql")
public class GenerateDataSqlController {
    @Autowired
    private GenerateSqlService generateSqlService;

    /**
     * 批量生成插入字典项脚本
     * @return
     */
    @GetMapping
     public String make() {
       List<STreeDic> list =  generateSqlService.findAll("all");
        return "sussess";
     }
     @GetMapping({"/gerertCooprSql"})
     public String gerertCooprSql(){

         ExcleUtil.parseExcel("",generateSqlService);
        return "success";
     }
     @GetMapping({"/genertUserExtSql"})
     public String genertUserExtSql(){
         ExcleUtil.parseExcelUser("F:\\平时工作\\任务相关\\2022年新任务\\5月\\uat.xlsx",generateSqlService);
        return "success";
     }

}
