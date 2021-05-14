package com.cmis.export.service;

import com.cmis.export.entity.BizLine;
import com.cmis.export.mapper.ExportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportService {

    @Autowired
    private ExportMapper mapper;

    public List<BizLine> queryAll() {
        return mapper.queryAll();
    }
}
