package com.cmis.export.service;

import com.cmis.export.entity.STreeDic;
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

}
