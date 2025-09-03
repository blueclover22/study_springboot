package com.study.springboot.service;

import com.study.springboot.domain.AccessLog;

import java.util.List;

public interface AccessLogService {
    public void register(AccessLog accessLog) throws Exception;

    public List<AccessLog> list() throws Exception;
}
