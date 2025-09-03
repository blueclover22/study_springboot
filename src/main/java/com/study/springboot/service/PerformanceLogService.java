package com.study.springboot.service;

import com.study.springboot.domain.PerformanceLog;

import java.util.List;

public interface PerformanceLogService {

    public void register(PerformanceLog performanceLog) throws Exception;

    public List<PerformanceLog> list() throws Exception;
}
