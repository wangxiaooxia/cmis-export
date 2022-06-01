package com.cmis.export.mapper;

import com.cmis.export.entity.SArea;
import com.cmis.export.entity.SComCde;
import com.cmis.export.entity.STreeDic;
import com.cmis.export.entity.Scoopr;

import java.util.List;

public interface GenerateSqlMapper {
    List<STreeDic> findAll(String ss);
    List<SArea> findSAreByName(String name,String type);
    List<SComCde> findSComCde(String name);
    List<Scoopr> findScoopr(String name);

}
