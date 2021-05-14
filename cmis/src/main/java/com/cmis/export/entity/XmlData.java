package com.cmis.export.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

@Data
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(20) //如果某列太宽或太窄，可在列上单独定义@ColumnWidth(50)
public class XmlData {
    @ExcelProperty({"某某某数据导出主标题", "第一列数据"})
    private String first;
    @ExcelProperty({"某某某数据导出主标题", "第二列数据"})
    private String second;
    @ExcelProperty({"某某某数据导出主标题", "三列列数据"})
    private String three;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
