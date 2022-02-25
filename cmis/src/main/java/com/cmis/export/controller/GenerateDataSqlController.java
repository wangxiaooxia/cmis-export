package com.cmis.export.controller;

import com.cmis.export.entity.STreeDic;
import com.cmis.export.mapper.GenerateSqlMapper;
import com.cmis.export.service.ExportService;
import com.cmis.export.service.GenerateSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/generateSql")
public class GenerateDataSqlController {
    @Autowired
    private GenerateSqlService generateSqlService;

    @GetMapping
     public String make() {
       List<STreeDic> list =  generateSqlService.findAll("all");
        return "sussess";
     }

}
