package com.study.springboot.service.impl;

import com.study.springboot.domain.CodeGroup;
import com.study.springboot.repository.CodeGroupRepository;
import com.study.springboot.service.CodeGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CodeGroupServiceImpl implements CodeGroupService {

    private final CodeGroupRepository repository;

    @Override
    public void register(CodeGroup codeGroup) throws Exception {
        repository.save(codeGroup);
    }

    @Override
    public List<CodeGroup> list() throws Exception {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "groupCode"));
    }

    @Override
    public CodeGroup read(String groupCode) throws Exception {
        return repository.getReferenceById(groupCode);
    }

    @Override
    public void modify(CodeGroup codeGroup) throws Exception {
        CodeGroup codeGroupEntity = repository.getReferenceById(codeGroup.getGroupCode());
        codeGroupEntity.setGroupName(codeGroup.getGroupName());
        repository.save(codeGroupEntity);
    }

    @Override
    public void remove(String groupCode) throws Exception {
        repository.deleteById(groupCode);
    }
}
