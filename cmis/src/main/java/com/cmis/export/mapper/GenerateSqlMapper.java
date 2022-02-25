package com.cmis.export.mapper;

import com.cmis.export.entity.STreeDic;

import java.util.List;

public interface GenerateSqlMapper {
    List<STreeDic> findAll(String ss);
}
