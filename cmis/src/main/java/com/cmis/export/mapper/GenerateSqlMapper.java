package com.cmis.export.mapper;

import com.cmis.export.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GenerateSqlMapper {
    List<STreeDic> findAll(String ss);
    List<SArea> findSAreByName(@Param("name") String name,@Param("type") String type);
    List<SComCde> findSComCde(@Param("name") String name, @Param("type")String type);
    List<Scoopr> findScoopr(String name);
    List<SBch> findSbch(@Param("name") String name);
    List<SUsr> findSUsr(@Param("name") String name);
    List<SUsr> findSUsrN(@Param("name") String name);
    Map findCooprGroup(@Param("name") String name);
    List<String> findServiceLine( List<String> list);
    Map findAccName(@Param("name") String name);
    Map findCooprBrand(@Param("name") String name);


}
