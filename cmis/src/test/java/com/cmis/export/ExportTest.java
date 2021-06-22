package com.cmis.export;

import com.alibaba.excel.EasyExcel;
import com.cmis.export.entity.BizLine;
import com.cmis.export.entity.XmlData;
import com.cmis.export.service.ExportService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ExportTest {

    @Autowired
    private ExportService exportService;

    @Test
    public void queryAll() {
        List<BizLine> bizLines = exportService.queryAll();
        if(bizLines == null || bizLines.size() < 1) {
            System.out.println("暂无数据可导出...");
            return;
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

    }
    @Test
    public void testParseExcel(){
        val read = EasyExcel.read(new File("F:\\parseexcel\\行业类别.xlsx"));
        System.out.println(read.toString());
    }
}
