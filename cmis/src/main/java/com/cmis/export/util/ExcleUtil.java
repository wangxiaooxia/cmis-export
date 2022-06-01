package com.cmis.export.util;

import com.cmis.export.service.GenerateSqlService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcleUtil {
    public  static void parseExcel(String filePath, GenerateSqlService generateSqlService) {
        Workbook wb;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String columns[] = {"coopr_typ","coopr_cde","coopr_typ_class","coopr_name","group_desc","direct_bch","reg_mana","sub_area","area_mana","role_cate"
                ,"role_range","settle_prove_type","risk_flag","risk_flag_sec","coopr_sts","str_dt","end_dt","cont_no","gurt_amt","mar_coe","bef_amount","meth_red",
                "per_pha_gua_agre","org_pha_gua_agre","auth_face_agre","auth_face_agre","whol_bus","reco_period","service_line","witn_desc","coopr_province","coopr_city"
                ,"coopr_area","coopr_addr","coopr_zip_cde","coopr_contact_email","cont_tel","corporate_name","fax_zone","file_re_add_typ","coopr_re_province","coopr_re_city"
                ,"coopr_re_area","coopr_re_addr","recipient","recipient_tel","acct_name","inter_bank_pay","bank_nam","bch_nam","ac_province","card_no","ac_alipay_no"
                ,"pay_number","zip_code","taxpayer_id_number","business_legal","business_start_dt","business_capital_amt","business_legal_tel","business_province",
                "business_city","business_area","business_address","business_zip_code",""};
        wb = readExcel(filePath);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行:wq
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }

                //生成sql脚本
                    if(map.get("serviceLine").contains("、")) {
                        String arrs[] = map.get("serviceLine").split("、");
                        for (int j = 0; j < arrs.length; j++) {
                            String b =testCode(arrs[j]);
                            System.out.println("INSERT INTO S_COOPR_BIZ_LINE ( seq,coopr_cde,service_line,maxloanamout)values (LINE_SEQ.Nextval,'"+ map.get("cooprCde")+"','"+b+"', '200000');");
                        }
                    } else  {
                        String a =  testCode(map.get("serviceLine"));
                        System.out.println("INSERT INTO S_COOPR_BIZ_LINE ( seq,coopr_cde,service_line,maxloanamout)values (LINE_SEQ.Nextval,'"+ map.get("cooprCde")+"','"+a+"','200000');");
                    }
                }
                //生成sql脚本
            }
        }


    //读取excel
    public static Workbook readExcel (String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:{
                    cellValue = String.valueOf(cell.getNumericCellValue()).trim();
                    break;
                }
                case Cell.CELL_TYPE_FORMULA:{
                    //判断cell是否为日期格式
                    if(DateUtil.isCellDateFormatted(cell)){
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    }else{
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue()).trim();
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:{
                    cellValue = cell.getRichStringCellValue().getString().trim();
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }


    public  static void parseExcelUser(String filePath, GenerateSqlService generateSqlService) {
        Workbook wb;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        wb = readExcel(filePath);
        String columns[] = {"usr_cde","usr_name","usr_id_typ","id_is_temporary","usr_id_no","usr_tel","usr_email","usr_bch","usr_sts","role_cate","usr_rmk"};

        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(1);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行:wq
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }
                //生成审批端脚本
                String content = "INSERT INTO S_USR_EXT" +
                        " (INSTU_CDE, USR_CDE, USR_NAME, USR_ID_TYP, ID_IS_TEMPORARY,USR_ID_NO," +
                        " USR_TEL, USR_EMAIL, USR_BCH, USR_STS, USR_RMK, LAST_CHG_DT, LAST_CHG_USR, " +
                        " ROLE_CATE, STOR_COMP, CRT_USR, CRT_DT, DISTRIBUTOR,  LAST_SIGN_TIME, IS_UPLOAD," +
                        " ID_CARD_TEMP_PATH, CANCEL_DT)" +
                        " VALUES('900000000','"+map.get("usr_cde")+"','"+map.get("usr_name")+"','20','"+isYesOrNo(map.get("id_is_temporary"))+"','"+map.get("usr_id_no")
                        +"','"+ map.get("usr_tel")+"','"+map.get("usr_email")+"','"+queryScooprCde(map.get("usr_bch"),generateSqlService)+"','A','"+map.get("usr_rmk")+
                        "','"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"','admin','01','01','admin','"+
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"','','','N','','');";

                String contentN = "INSERT INTO CF_SALERINF" +
                        "(CSI_SALERNO, CSI_PASSWORD, CSI_INSTU_CDE, CSI_STORENO, CSI_NAME, CSI_SEX, CSI_IDNO, CSI_TEL, CSI_ADDRESS, CSI_TYPE, CSI_ROLE, CSI_STT," +
                        "  CSI_LASTCHGDT, CSI_LASTCHGUSR, " +
                        " IS_TEMPORARY_ID, IS_UPLOAD_IMAGE,CSI_LASTPWDMODDATE)" +
                        "VALUES('"+map.get("usr_cde")+"','a172ffc990129fe6f68b50f6037c54a1894ee3fd','900000000','"+queryScooprCde(map.get("usr_bch"),generateSqlService)+
                        "','"+map.get("usr_name")+"','','"+map.get("usr_id_no")+"','"+map.get("usr_tel")
                        +"','','01','01','A','"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"','admin','"+isYesOrNo(map.get("id_is_temporary"))+"','N','"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"');";

                //生成sql脚本
                FileOutputStream fos = null;
                FileOutputStream fosN = null;
                try {
                    // true代表叠加写入，没有则代表清空一次写一次
                    fos = new FileOutputStream("F:\\2022jiaoben\\userExt.sql",true);
                    fosN = new FileOutputStream("F:\\2022jiaoben\\cfSalerInf.sql",true);
                    byte[] bytes = content.getBytes();
                    fos.write(bytes);
                    // 换行
                    fos.write("\r\n".getBytes());

                    byte[] bytesN = contentN.getBytes();
                    fosN.write(bytesN);
                    // 换行
                    fosN.write("\r\n".getBytes());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                        fosN.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
    public static String testCode(String str){
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("Fleet-本品牌新车","flbppxc");
        hashMap.put("Fleet-全品牌新车","flqppxc");
        hashMap.put("全品牌新车-small lot","qpplot");
        hashMap.put("全品牌LCV-small lot","qlcvlot");
        hashMap.put("二手车","rsc");
        hashMap.put("全品牌准新车","qppzxc");
        hashMap.put("LCV准新车","lcvzxc");
        hashMap.put("全品牌新车","qppxc");
        hashMap.put("LCV","lcvqpp");
        hashMap.put("本品牌新车","bppxc");
        hashMap.put("本品牌新车-small lot","sl");

        return hashMap.get(str.trim());
    }

    private static String isYesOrNo(String str) {
        String ss = "";
        if ("是".equals(str)){
            ss = "Y";
        } else {
            ss = "N";
        }
        return ss;
    }
    private static String queryScooprCde(String str,GenerateSqlService generateSqlService) {
        String  ss  = generateSqlService.findScoopr(str);
        return ss;
    }

}
