package com.study.springboot.service.impl;

import com.study.springboot.domain.PerformanceLog;
import com.study.springboot.repository.PerformanceLogRepository;
import com.study.springboot.service.PerformanceLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PerformanceLogServiceImpl implements PerformanceLogService {

    private final PerformanceLogRepository repository;

    @Override
    public void register(PerformanceLog performanceLog) throws Exception {
        repository.save(performanceLog);
    }

    @Override
    public List<PerformanceLog> list() throws Exception {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "logNo"));
    }
}
