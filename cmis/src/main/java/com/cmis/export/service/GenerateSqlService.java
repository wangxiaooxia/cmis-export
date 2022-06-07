package com.cmis.export.service;

import com.cmis.export.entity.*;
import com.cmis.export.mapper.GenerateSqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GenerateSqlService {

    @Autowired
    private GenerateSqlMapper generateSqlMapper;

    public List<STreeDic> findAll(String aa){
    List<STreeDic> sTreeDicList = generateSqlMapper.findAll(aa);
        sTreeDicList.forEach(sTreeDic -> {
            System.out.println("insert into pub_apppar (APR_NAME, APR_CODE, APR_VALUE, APR_LANGUAGE, APR_SHOWMSG, APR_CLASS, APR_SEQ, APR_FLAG, APP_PRENT_CODE)" +
                    "values ('单位所属行业', 'EMP_INDUSTRY', '"+sTreeDic.getComCde()+"', 'ZH_CN', '"+sTreeDic.getComDesc()+"', 'CFLN', '0', '1', null);");
        });
    return sTreeDicList;
    }

    /**
     * 翻译地区字典项
     * @param name
     * @param type
     * @return
     */
    public String findSAreByName(String name,String type){
        List<SArea> sTreeDicList = generateSqlMapper.findSAreByName(name,type);
        String code = "";
        if (sTreeDicList.size() > 0){
            code = sTreeDicList.get(0).getAreaCode();
        }
        return code;
    }

    /**
     * 翻译通用字典项
     * @param name
     * @return
     */
    public String findSComCde(String name,String type){
        List<SComCde> sTreeDicList = generateSqlMapper.findSComCde(name,type);
        String code = "";
        if (sTreeDicList.size() > 0){
            code = sTreeDicList.get(0).getComCde();
        }
        return code;
    }
    public String findScoopr(String name){
        List<Scoopr> sTreeDicList = generateSqlMapper.findScoopr(name);
        String str = sTreeDicList.get(0).getCooprCde();
        return str;
    }

    public String findSbch(String name){
        List<SBch> sTreeDicList = generateSqlMapper.findSbch(name);
        String code = "";
        if (sTreeDicList.size() > 0){
            code = sTreeDicList.get(0).getBchCde();
        }
        return code;
    }
    public String findSUsr(String name){
        List<SUsr> sTreeDicList = generateSqlMapper.findSUsr(name);
        String code = "";
        if (sTreeDicList.size() > 0){
            code = sTreeDicList.get(0).getUsrCde();
        }
        return code;
    }
    public String findSUsrN(String name){
        List<SUsr> sTreeDicList = generateSqlMapper.findSUsrN(name);
        String code = "";
        if (sTreeDicList.size() > 0){
            code = sTreeDicList.get(0).getUsrCde();
        }
        return code;
    }

    public String findCooprGroup(String name){
        Map sTreeDicList = generateSqlMapper.findCooprGroup(name);
        String code = "";
        if (sTreeDicList.size() > 0){
            code = (String) sTreeDicList.get("GROUP_CODE");
        }
        return code;
    }

    public String  findServiceLine(List<String> list){
        List<String> sTreeDicList = generateSqlMapper.findServiceLine(list);
        return listToString(sTreeDicList,",");
    }
    public String findAccName(String name){
        Map sTreeDicList = generateSqlMapper.findAccName(name);
        String code = "";
        if (sTreeDicList.size() > 0){
            code = (String) sTreeDicList.get("ACC_BANK_CDE");
        }
        return code;
    }
    public String findCooprBrand(String name){
        Map sTreeDicList = generateSqlMapper.findCooprBrand(name);
        String code = "";
        if (sTreeDicList.size() > 0){
            code = (String) sTreeDicList.get("BRAND_CDE");
        }
        return code;
    }
    public static String listToString(List<String> list,String separator){
        StringBuffer listString = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                listString.append(list.get(i));
                if (i != list.size()-1) {
                    listString.append(separator);
                }
            }
        }
        return listString.toString();
    }
}
