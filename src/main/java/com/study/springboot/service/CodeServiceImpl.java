package com.study.springboot.service;

import com.study.springboot.dto.CodeLabelValue;
import com.study.springboot.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CodeServiceImpl implements CodeService{

    private final CodeGroupRepository repository;

    @Override
    public List<CodeLabelValue> getCodeGroupList() throws Exception {
        return List.of();
    }
}
