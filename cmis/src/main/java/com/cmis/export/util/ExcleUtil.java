package com.cmis.export.util;

import com.cmis.export.service.GenerateSqlService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcleUtil {
    @Autowired
    private GenerateSqlService generateSqlService;
    public static void parseExcel(String filePath, GenerateSqlService generateSqlService) {
        Workbook wb;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        String columns[] = {"coopr_typ", "coopr_cde", "coopr_typ_class", "coopr_name", "group_desc", "direct_bch", "reg_mana", "sub_area", "area_mana"
                , "role_range", "cred_com", "sales_rep", "special_com", "settle_prove_type", "risk_flag", "risk_flag_sec", "coopr_sts", "str_dt", "end_dt", "cont_no",
                "gurt_amt", "mar_coe", "bef_amount", "meth_red",
                "per_pha_gua_agre", "org_pha_gua_agre", "auth_face_agre", "whol_bus", "reco_period", "service_line", "witn_desc", "coopr_province", "coopr_city"
                , "coopr_area", "coopr_addr", "coopr_zip_cde", "coopr_contact_email", "cont_tel", "corporate_name", "fax_no", "file_re_add_typ", "coopr_re_province", "coopr_re_city"
                , "coopr_re_area", "coopr_re_addr", "recipient", "recipient_tel", "acct_name", "inter_bank_pay", "bank_nam", "bch_nam", "ac_province", "bank_number", "card_no", "ac_alipay_no"
                , "pay_number", "zip_code", "taxpayer_id_number", "business_legal", "business_start_dt", "business_capital_amt", "business_legal_tel", "business_province",
                "business_city", "business_area", "business_address", "business_zip_code", "code"};
        wb = readExcel(filePath);
        if (wb != null) {
            //用来存放表中数据
            list = new ArrayList<Map<String, String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行:wq
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            BigDecimal sum  = new BigDecimal(9467);
            for (int i = 1; i < rownum; i++) {
                sum = sum.add(BigDecimal.valueOf(i));
                Map<String, String> map = new LinkedHashMap<String, String>();
                row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < colnum; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                } else {
                    break;
                }

                String biz = ""; //业务条线sql
                String bussinessScope = ""; //业务范围

                //生成sql脚本
                if (map.get("service_line").contains("，")) {
                    Map<String,String> map1 = getMap(map.get("service_line"));
                    bussinessScope =  getString(map1,generateSqlService);

                    Set set = map1.keySet();
                    for (Object o: set){
                        biz = "INSERT INTO S_COOPR_BIZ_LINE ( seq,coopr_cde,service_line,maxloanamout)values (LINE_SEQ.Nextval,'" + map.get("coopr_cde") + "','" + o + "', '"+map1.get(o)+"');";
                        FileOutputStream fosNN = null;
                       try {
                           fosNN = new FileOutputStream("F:\\2022jiaoben\\s_biz_line.sql",true);
                           byte[] bytesNN = biz.getBytes();
                           fosNN.write(bytesNN);
                           // 换行
                           fosNN.write("\r\n".getBytes());
                       }catch (Exception e) {
                         e.printStackTrace();
                       }finally {
                           try {
                               fosNN.close();
                           } catch (IOException e) {
                               throw new RuntimeException(e);
                           }
                       }
                    }
                }

                //生成审批端脚本
                String content = "INSERT INTO S_COOPR(COOPR_SEQ, COOPR_CDE, COOPR_NAME, COOPR_KIND, COOPR_LVL, " +
                        "DIRECT_BCH, COOPR_STR_DT, COOPR_END_DT, COOPR_STS, COOPR_PROVINCE, COOPR_CITY, COOPR_AREA," +
                        " COOPR_ADDR, COOPR_ZIP_CDE, COOPR_CONTACT_EMAIL, CONT_TEL, FAX_ZONE, CORPORATE_NAME, " +
                        "ACCT_CCY,ACCT_NAME, BANK_CDE, BANK_NAM, BCH_CDE, BCH_NAM, LAST_CHG_DT, INSTU_CDE, " +
                        "IS_DIRECT_PAY, IS_OWN_ACCT, COOPR_TYP, RISK_FLAG, CONT_NO, GURT_AMT, CARD_NO, LEVEL_TREE, " +
                        "CRED_COM, SALES_REP, MAR_COE, BEF_AMOUNT, METH_RED, WHOL_BUS, RECO_PERIOD, WITN_DESC, PAY_NUMBER, ZIP_CODE, " +
                        "SUB_REGI, REG_MANA, SUB_AREA, AREA_MANA, " +
                        " STOR_ARCH, COOPR_TYP_CLASS, PER_PHA_GUA_AGRE, ORG_PHA_GUA_AGRE, AUTH_FACE_AGRE," +
                        " FAX_NO,FILE_RE_ADD_TYP, COOPR_RE_PROVINCE, COOPR_RE_CITY, COOPR_RE_AREA, COOPR_RE_ADDR, AC_ALIPAY_NO, " +
                        "RISK_FLAG_SEC, BUSI_SCOPE, SPECIAL_COM, ROLE_RANGE, AC_PROVINCE, BANK_NUMBER,SETTLE_PROVE_TYPE, TAXPAYER_ID_NUMBER," +
                        " RECIPIENT, RECIPIENT_TEL, BUSINESS_LEGAL, BUSINESS_LEGAL_TEL, BUSINESS_START_DT, BUSINESS_CAPITAL_AMT," +
                        " BUSINESS_PROVINCE, BUSINESS_CITY, BUSINESS_AREA, BUSINESS_ADDRESS, BUSINESS_ZIP_CODE, GROUP_CODE, GROUP_DESC," +
                        " INTER_BANK_PAY)" +
                        "VALUES('"+getCooprSeq(sum)+"','" + map.get("coopr_cde") + "','" + map.get("coopr_name") + "','07','02','" + findSbch(map.get("direct_bch"),generateSqlService) + "','"
                        + map.get("str_dt") + "','" + map.get("end_dt") + "','" + findSComCde(map.get("coopr_sts"), generateSqlService, "COOPR_STS") + "','" + findPace(map.get("coopr_province"),generateSqlService,"province" )+ "','"
                        +findPace(map.get("coopr_city"),generateSqlService,"city") +
                        "','" +findPace( map.get("coopr_area"),generateSqlService,"area") + "','" + map.get("coopr_addr") + "','" + map.get("coopr_zip_cde") + "','" + map.get("coopr_contact_email") + "','" + map.get("cont_tel") +
                        "','" + map.get("fax_zone") + "','" + map.get("corporate_name") + "','CNY','" + map.get("acct_name") + "','" + findAccName(map.get("bank_nam"),generateSqlService) + "','" + map.get("bank_nam") +
                        "','" + findAccName(map.get("bank_nam") ,generateSqlService)+ "','" + map.get("bch_nam") + "',to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'),'900000000','','Y','" +
                        findSComCde(map.get("coopr_typ"), generateSqlService, "COOPR_TYP") + "','" + map.get("risk_flag") + "','" + map.get("cont_no") + "','" + map.get("gurt_amt") + "','" + map.get("card_no") +
                        "','" + map.get("coopr_cde") + "','" + map.get("cred_com") + "','" + map.get("sales_rep") + "','" + map.get("mar_coe") + "','" + map.get("bef_amount") +
                        "','" + findSComCde(map.get("meth_red"), generateSqlService, "METH_RED") + "','" + findSComCde(map.get("whol_bus"), generateSqlService, "SIG_STATUS") + "','" + map.get("reco_period") + "','" + map.get("witn_desc") + "','" + map.get("pay_number") +
                        "','" + map.get("zip_code") + "','" + findSbch(map.get("direct_bch"),generateSqlService) + "','" + findSUsr( map.get("reg_mana"),generateSqlService) + "','" +findSbch(map.get("sub_area"),generateSqlService) + "','" + findSUsrN( map.get("area_mana"),generateSqlService) +
                        "','01','" + findSComCde(map.get("coopr_typ_class"), generateSqlService, "DEAL_TYP") + "','" + findSComCde(map.get("per_pha_gua_agre"), generateSqlService, "SIG_STATUS") + "','" +
                        findSComCde(map.get("org_pha_gua_agre"), generateSqlService, "SIG_STATUS") + "','" + findSComCde(map.get("auth_face_agre"), generateSqlService, "SIG_STATUS") +
                        "','" + map.get("fax_no") + "','" + findSComCde(map.get("file_re_add_typ"), generateSqlService, "FILE_RE_ADD_TYP") + "','" + findPace(map.get("coopr_re_province"),generateSqlService,"province") + "','" + findPace(map.get("coopr_re_city"),generateSqlService,"city") + "','" + findPace(map.get("coopr_re_area"),generateSqlService,"area") +
                        "','" + map.get("coopr_re_addr") + "','" + map.get("ac_alipay_no") + "','" + map.get("risk_flag_sec") + "','" + bussinessScope + "','" + map.get("special_com") +
                        "','" + changeRoleRange(map.get("role_range")) + "','" + findPace(map.get("ac_province"),generateSqlService,"province") + "','"+map.get("bank_number")+"','" + findSComCde(map.get("settle_prove_type"), generateSqlService, "SETTLE_PROVE_TYPE") + "','" + map.get("taxpayer_id_number") + "','" + map.get("recipient") +
                        "','" + map.get("recipient_tel") + "','" + map.get("business_legal") + "','" + map.get("business_legal_tel") + "','" + map.get("business_start_dt") + "','" + map.get("business_capital_amt") +
                        "','" + findPace(map.get("business_province"),generateSqlService,"province") + "','" + findPace(map.get("business_city"),generateSqlService,"city") + "','" + findPace(map.get("business_area"),generateSqlService,"area") + "','" + map.get("business_address") + "','" + map.get("business_zip_code") +
                        "','" + findCooprGroup(map.get("group_desc"),generateSqlService) + "','" + map.get("group_desc") + "','" + isYesOrNo(map.get("inter_bank_pay")) + "');";
                //生成贷后脚本
                String contentDh = "INSERT INTO S_COOPR(COOPR_SEQ, COOPR_CDE, COOPR_NAME, COOPR_KIND, COOPR_LVL, " +
                        "DIRECT_BCH, COOPR_STR_DT, COOPR_END_DT, COOPR_STS, COOPR_PROVINCE, COOPR_CITY, COOPR_AREA," +
                        " COOPR_ADDR, COOPR_ZIP_CDE, COOPR_CONTACT_EMAIL, CONT_TEL, FAX_ZONE, CORPORATE_NAME, " +
                        "ACCT_CCY,ACCT_NAME, BANK_CDE, BANK_NAM, BCH_CDE, BCH_NAM, LAST_CHG_DT, INSTU_CDE, " +
                        "IS_DIRECT_PAY, IS_OWN_ACCT, COOPR_TYP, RISK_FLAG, CONT_NO, GURT_AMT, CARD_NO, LEVEL_TREE, " +
                        "CRED_COM, SALES_REP, MAR_COE, BEF_AMOUNT, METH_RED, WHOL_BUS, RECO_PERIOD, WITN_DESC, PAY_NUMBER, ZIP_CODE, " +
                        "SUB_REGI, REG_MANA, SUB_AREA, AREA_MANA, " +
                        " STOR_ARCH, COOPR_TYP_CLASS, PER_PHA_GUA_AGRE, ORG_PHA_GUA_AGRE, AUTH_FACE_AGRE," +
                        " FAX_NO,FILE_RE_ADD_TYP, COOPR_RE_PROVINCE, COOPR_RE_CITY, COOPR_RE_AREA, COOPR_RE_ADDR, AC_ALIPAY_NO, " +
                        "RISK_FLAG_SEC, BUSI_SCOPE, SETTLE_PROVE_TYPE, TAXPAYER_ID_NUMBER)" +
                        "VALUES('"+getCooprSeq(sum)+"','" + map.get("coopr_cde") + "','" + map.get("coopr_name") + "','07','02','" + findSbch(map.get("direct_bch"),generateSqlService) + "','"
                        + map.get("str_dt") + "','" + map.get("end_dt") + "','" + findSComCde(map.get("coopr_sts"), generateSqlService, "COOPR_STS") + "','" + findPace(map.get("coopr_province"),generateSqlService,"province" )+ "','"
                        +findPace(map.get("coopr_city"),generateSqlService,"city") +
                        "','" +findPace( map.get("coopr_area"),generateSqlService,"area") + "','" + map.get("coopr_addr") + "','" + map.get("coopr_zip_cde") + "','" + map.get("coopr_contact_email") + "','" + map.get("cont_tel") +
                        "','" + map.get("fax_zone") + "','" + map.get("corporate_name") + "','CNY','" + map.get("acct_name") + "','" + findAccName(map.get("bank_nam"),generateSqlService) + "','" + map.get("bank_nam") +
                        "','" + findAccName(map.get("bank_nam") ,generateSqlService)+ "','" + map.get("bch_nam") + "',to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'),'900000000','','Y','" +
                        findSComCde(map.get("coopr_typ"), generateSqlService, "COOPR_TYP") + "','" + map.get("risk_flag") + "','" + map.get("cont_no") + "','" + map.get("gurt_amt") + "','" + map.get("card_no") +
                        "','" + map.get("coopr_cde") + "','" + map.get("cred_com") + "','" + map.get("sales_rep") + "','" + map.get("mar_coe") + "','" + map.get("bef_amount") +
                        "','" + findSComCde(map.get("meth_red"), generateSqlService, "METH_RED") + "','" + findSComCde(map.get("whol_bus"), generateSqlService, "SIG_STATUS") + "','" + map.get("reco_period") + "','" + map.get("witn_desc") + "','" + map.get("pay_number") +
                        "','" + map.get("zip_code") + "','" + findSbch(map.get("direct_bch"),generateSqlService) + "','" + findSUsr( map.get("reg_mana"),generateSqlService) + "','" +findSbch(map.get("sub_area"),generateSqlService) + "','" + findSUsrN( map.get("area_mana"),generateSqlService) +
                        "','01','" + findSComCde(map.get("coopr_typ_class"), generateSqlService, "DEAL_TYP") + "','" + findSComCde(map.get("per_pha_gua_agre"), generateSqlService, "SIG_STATUS") + "','" +
                        findSComCde(map.get("org_pha_gua_agre"), generateSqlService, "SIG_STATUS") + "','" + findSComCde(map.get("auth_face_agre"), generateSqlService, "SIG_STATUS") +
                        "','" + map.get("fax_no") + "','" + findSComCde(map.get("file_re_add_typ"), generateSqlService, "FILE_RE_ADD_TYP") + "','" + findPace(map.get("coopr_re_province"),generateSqlService,"province") + "','" + findPace(map.get("coopr_re_city"),generateSqlService,"city") + "','" + findPace(map.get("coopr_re_area"),generateSqlService,"area") +
                        "','" + map.get("coopr_re_addr") + "','" + map.get("ac_alipay_no") + "','" + map.get("risk_flag_sec") + "','" + bussinessScope + "','" + findSComCde(map.get("settle_prove_type"), generateSqlService, "SETTLE_PROVE_TYPE") + "','" + map.get("taxpayer_id_number") + "');";



                //生成渠道端脚本
                String contentN = "INSERT INTO CF_COOPR" +
                        "(CO_INSTUCDE, CO_COOPRSEQ, CO_COOPRCDE, CO_COOPRNAME, CO_COOPRKIND, CO_COOPRLVL, CO_DIRECTBCH," +
                        " CO_COOPRSTRDT, CO_COOPRENDDT, CO_COOPRSTS, CO_CAPITALAMT, CO_COOPRPROVINCE, CO_COOPRCITY, " +
                        "CO_COOPRAREA, CO_COOPRADDR, CO_COOPRZIPCDE, CO_COOPRCONTACTEMAIL, CO_CONTTEL, CO_FAXZONE,CO_BUSI_SCOPE, CO_COOPRTYP, CO_RISKFLAG,CO_RISKFLAGSEC)" +
                        "VALUES('900000000','"+getCooprSeq(sum)+"','"+ map.get("coopr_cde") +"','"+ map.get("coopr_name") +"','07','02','"+findSbch(map.get("direct_bch"),generateSqlService)+
                        "','"+map.get("str_dt") + "','" + map.get("end_dt") +"','"+ findSComCde(map.get("coopr_sts"), generateSqlService, "COOPR_STS") +
                        "','"+map.get("business_capital_amt")+"','"+ findPace(map.get("coopr_province"),generateSqlService,"province" )+ "','"
                        +findPace(map.get("coopr_city"),generateSqlService,"city") + "','" +findPace( map.get("coopr_area"),generateSqlService,"area") + "','" + map.get("coopr_addr") +
                        "','"+map.get("coopr_zip_cde")+ "','"+map.get("coopr_contact_email") + "','" + map.get("cont_tel") +"','" + map.get("fax_zone") +"','"+ bussinessScope +
                        "','"+findSComCde(map.get("coopr_typ"), generateSqlService, "COOPR_TYP") +"','"+map.get("risk_flag")+"','"+map.get("risk_flag_sec")+"');";

                //车型审批端脚本
                String cooprCde = generateSqlService.findCooprBrand(map.get("code"));//经销商品牌信息
                String cooprBrand = "insert into S_COOPR_BRAND s (s.BRAND_SEQ,s.COOPR_CDE,s.BRAND_CDE,s.LAST_CHG_DT,s.LAST_CHG_USR) values " +
                        "('"+getCooprSeq(sum)+"','"+map.get("coopr_cde")+"','"+cooprCde+"',to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'),'admin');";
                //车型渠道端脚本
                String cooprBrandQd = "insert into CF_COOPR_BRAND s (s.CO_BRANDSEQ,s.CO_COOPRCDE,s.CO_BRANDCDE,s.CO_LASTCHGDT,s.CO_LASTCHGUSR) values " +
                        "('"+getCooprSeq(sum)+"','"+map.get("coopr_cde")+"','"+cooprCde+"',to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'),'admin');";


                //生成sql脚本
                FileOutputStream fos = null; //审批端脚本
                FileOutputStream fosN = null; //渠道端脚本
                FileOutputStream fosDH = null; //贷后审批端脚本


                FileOutputStream fosCooprS = null; //审批端脚本
                FileOutputStream fosNCooprQ = null; //渠道端脚本

                try {
                    // true代表叠加写入，没有则代表清空一次写一次
                    fos = new FileOutputStream("F:\\2022jiaoben\\s_coopr.sql", true);
                    fosN = new FileOutputStream("F:\\2022jiaoben\\cf_coopr.sql", true);
                    fosDH = new FileOutputStream("F:\\2022jiaoben\\s_coopr_dh.sql", true);

                    fosCooprS = new FileOutputStream("F:\\2022jiaoben\\s_coopr_brand.sql", true);
                    fosNCooprQ = new FileOutputStream("F:\\2022jiaoben\\cf_coopr_brand.sql", true);

                    byte[] bytes = content.getBytes();
                    fos.write(bytes);
                    // 换行
                    fos.write("\r\n".getBytes());

                    byte[] bytesN = contentN.getBytes();
                    fosN.write(bytesN);
                    // 换行
                    fosN.write("\r\n".getBytes());


                    byte[] contentDhB = contentDh.getBytes();
                    fosDH.write(contentDhB);
                    // 换行
                    fosDH.write("\r\n".getBytes());


                    byte[] bytesNN = cooprBrand.getBytes();
                    fosCooprS.write(bytesNN);
                    // 换行
                    fosCooprS.write("\r\n".getBytes());

                    byte[] bytesNNN = cooprBrandQd.getBytes();
                    fosNCooprQ.write(bytesNNN);
                    // 换行
                    fosNCooprQ.write("\r\n".getBytes());


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                        fosN.close();
                        fosDH.close();
                        fosCooprS.close();
                        fosNCooprQ.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            //生成sql脚本
        }
    }


    //读取excel
    public static Workbook readExcel(String filePath) {
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

    public static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue()).trim();
                    break;
                }
                case Cell.CELL_TYPE_FORMULA: {
                    //判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue()).trim();
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING: {
                    cellValue = cell.getRichStringCellValue().getString().trim();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }


    public static void parseExcelUser(String filePath, GenerateSqlService generateSqlService) {
        Workbook wb;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        wb = readExcel(filePath);
        String columns[] = {"usr_cde", "usr_name", "usr_id_typ", "id_is_temporary", "usr_id_no", "usr_tel", "usr_email", "usr_bch", "usr_sts", "role_cate", "usr_rmk"};

        if (wb != null) {
            //用来存放表中数据
            list = new ArrayList<Map<String, String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(1);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行:wq
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 1; i < rownum; i++) {
                Map<String, String> map = new LinkedHashMap<String, String>();
                row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < colnum; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                } else {
                    break;
                }
                //生成审批端脚本
                String content = "INSERT INTO S_USR_EXT" +
                        " (INSTU_CDE, USR_CDE, USR_NAME, USR_ID_TYP, ID_IS_TEMPORARY,USR_ID_NO," +
                        " USR_TEL, USR_EMAIL, USR_BCH, USR_STS, USR_RMK, LAST_CHG_DT, LAST_CHG_USR, " +
                        " ROLE_CATE, STOR_COMP, CRT_USR, CRT_DT, DISTRIBUTOR,  LAST_SIGN_TIME, IS_UPLOAD," +
                        " ID_CARD_TEMP_PATH, CANCEL_DT)" +
                        " VALUES('900000000','" + map.get("usr_cde") + "','" + map.get("usr_name") + "','20','" + isYesOrNo(map.get("id_is_temporary")) + "','" + map.get("usr_id_no")
                        + "','" + map.get("usr_tel") + "','" + map.get("usr_email") + "','" + queryScooprCde(map.get("usr_bch"), generateSqlService) + "','A','" + map.get("usr_rmk") +
                        "',to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'),'admin','01','01','admin','" +
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "','','','N','','');";

                String contentN = "INSERT INTO CF_SALERINF" +
                        "(CSI_SALERNO, CSI_PASSWORD, CSI_INSTU_CDE, CSI_STORENO, CSI_NAME, CSI_SEX, CSI_IDNO, CSI_TEL, CSI_ADDRESS, CSI_TYPE, CSI_ROLE, CSI_STT," +
                        "  CSI_LASTCHGDT, CSI_LASTCHGUSR, " +
                        " IS_TEMPORARY_ID, IS_UPLOAD_IMAGE,CSI_LASTPWDMODDATE)" +
                        "VALUES('" + map.get("usr_cde") + "','a172ffc990129fe6f68b50f6037c54a1894ee3fd','900000000','" + queryScooprCde(map.get("usr_bch"), generateSqlService) +
                        "','" + map.get("usr_name") + "','','" + map.get("usr_id_no") + "','" + map.get("usr_tel")
                        + "','','01','01','A',to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'),'admin','" + isYesOrNo(map.get("id_is_temporary")) + "','N',to_char(sysdate,'yyyy-MM-dd'));";

                //生成sql脚本
                FileOutputStream fos = null;
                FileOutputStream fosN = null;
                try {
                    // true代表叠加写入，没有则代表清空一次写一次
                    fos = new FileOutputStream("F:\\2022jiaoben\\userExt.sql", true);
                    fosN = new FileOutputStream("F:\\2022jiaoben\\cfSalerInf.sql", true);
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

    public static String testCode(String str) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("本品牌新车-Fleet", "flbppxc");
        hashMap.put("全品牌新车-Fleet", "flqppxc");
        hashMap.put("全品牌新车-small lot", "qpplot");
        hashMap.put("全品牌LCV-small lot", "qlcvlot");
        hashMap.put("二手车", "rsc");
        hashMap.put("全品牌准新车", "qppzxc");
        hashMap.put("LCV准新车", "lcvzxc");
        hashMap.put("全品牌新车", "qppxc");
        hashMap.put("LCV", "lcvqpp");
        hashMap.put("本品牌新车", "bppxc");
        hashMap.put("本品牌新车Small lot", "sl");

        return hashMap.get(str.trim());
    }

    private static String isYesOrNo(String str) {
        String ss = "";
        if ("是".equals(str)) {
            ss = "Y";
        } else {
            ss = "N";
        }
        return ss;
    }


    private static String queryScooprCde(String str, GenerateSqlService generateSqlService) {
        String ss = generateSqlService.findScoopr(str);
        return ss;
    }

    private static String findSComCde(String str, GenerateSqlService generateSqlService, String type) {
        String ss = String.valueOf(generateSqlService.findSComCde(str, type));
        return ss;
    }
    private static String findPace(String str, GenerateSqlService generateSqlService, String type) {
        String ss = generateSqlService.findSAreByName(str, type);
        return ss;
    }
    private static String findSbch(String str, GenerateSqlService generateSqlService) {
        String ss = generateSqlService.findSbch(str);
        return ss;
    }
    private static String findSUsr(String str, GenerateSqlService generateSqlService) {
        String ss = generateSqlService.findSUsr(str.trim());
        return ss;
    }
    private static String findSUsrN(String str, GenerateSqlService generateSqlService) {
        String ss = generateSqlService.findSUsrN(str);
        return ss;
    }
    private static String findCooprGroup(String str, GenerateSqlService generateSqlService) {
        String ss = generateSqlService.findCooprGroup(str);
        return ss;
    }

    private static String findAccName(String str, GenerateSqlService generateSqlService) {
        String ss = generateSqlService.findAccName(str);
        return ss;
    }
    private static String changeRoleRange(String roleRange) {
        if (!StringUtils.hasText(roleRange)) {
            return "";
        }
        if (!roleRange.contains(",")) {
            String str = "";
            if ("高级信贷专员".equals(roleRange)) {
                str = "02";
            } else if ("信贷专员".equals(roleRange)) {
                str = "03";
            } else {
                str = "04";
            }
            return str;
        }

        String roles[] = roleRange.split(",");
        for (int i = 0; i < roles.length; i++) {
            if ("高级信贷专员".equals(roles[i])) {
                roles[i] = "02";
            } else if ("信贷专员".equals(roles[i])) {
                roles[i] = "03";
            } else {
                roles[i] = "04";
            }
        }
        return String.join(",", roles);
    }

    private static Map getMap(String str) {
        if (!StringUtils.hasText(str)) {
            return new HashMap();
        }
        String temps[] = str.split("，");
        int size = temps.length / 2;
        Map map = new HashMap();
        for (int i = 0; i < size; i++) {
            map.put(testCode(temps[2 * i]), temps[2 * i + 1]);
        }
        return map;
    }

    public static BigDecimal getCooprSeq(BigDecimal cooprSeq) {
        try{
            //增加渠道编号和年月的处理
            Date date=new Date();
            SimpleDateFormat sfm =new SimpleDateFormat("yyyyMMdd");
            String time=sfm.format(date);
            //seq长度不足12位使用0补位
            String seq= String.valueOf(cooprSeq);//
            if(seq.length()<10){
                int size=10-seq.length();
                for(int i=0;i<size;i++){
                    seq="0"+seq;
                }
            }
            seq="10"+time+seq;
            cooprSeq=new BigDecimal(seq);//
        }catch (Exception e) {
        }
        return cooprSeq;
    }
    private static String getString(Map map,GenerateSqlService generateSqlService) {

       List<String> list = new ArrayList<String>();
       Set set = map.keySet();
       for (Object o : set){
           list.add(String.valueOf(o));
       }
        String str = generateSqlService.findServiceLine(list);
        return str;
    }
}
