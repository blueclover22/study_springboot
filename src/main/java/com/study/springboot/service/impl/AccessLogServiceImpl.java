package com.study.springboot.service.impl;

import com.study.springboot.domain.AccessLog;
import com.study.springboot.repository.AccessLogRepository;
import com.study.springboot.service.AccessLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository repository;

    @Override
    public void register(AccessLog accessLog) throws Exception {
        repository.save(accessLog);
    }

    @Override
    public List<AccessLog> list() throws Exception {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "logNo"));
    }
}
