package com.cmis.export.service;

import com.cmis.export.entity.SArea;
import com.cmis.export.entity.SComCde;
import com.cmis.export.entity.STreeDic;
import com.cmis.export.entity.Scoopr;
import com.cmis.export.mapper.GenerateSqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<SArea> findSAreByName(String name,String type){
        List<SArea> sTreeDicList = generateSqlMapper.findSAreByName(name,type);
        return sTreeDicList;
    }

    /**
     * 翻译通用字典项
     * @param name
     * @return
     */
    public List<SComCde> findSComCde(String name){
        List<SComCde> sTreeDicList = generateSqlMapper.findSComCde(name);
        return sTreeDicList;
    }
    public String findScoopr(String name){
        List<Scoopr> sTreeDicList = generateSqlMapper.findScoopr(name);
        String str = sTreeDicList.get(0).getCooprCde();
        return str;
    }
}
