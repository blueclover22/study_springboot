package com.study.springboot.service.impl;

import com.study.springboot.domain.CodeGroup;
import com.study.springboot.repository.CodeGroupRepository;
import com.study.springboot.service.CodeGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CodeGroupServiceImpl implements CodeGroupService {

    private final CodeGroupRepository repository;

    @Override
    public void register(CodeGroup codeGroup) throws Exception {

        log.debug("CodeGroupServiceImpl.register");

        repository.save(codeGroup);
    }

    @Override
    public List<CodeGroup> list() throws Exception {

        log.debug("CodeGroupServiceImpl.list");

        return repository.findAll(Sort.by(Sort.Direction.DESC, "groupCode"));
    }

    @Override
    public CodeGroup read(String groupCode) throws Exception {

        log.debug("CodeGroupServiceImpl.read");

        return repository.getReferenceById(groupCode);
    }

    @Override
    public void modify(CodeGroup codeGroup) throws Exception {

        log.debug("CodeGroupServiceImpl.modify");

        CodeGroup codeGroupEntity = repository.getReferenceById(codeGroup.getGroupCode());
        codeGroupEntity.setGroupName(codeGroup.getGroupName());
        repository.save(codeGroupEntity);
    }

    @Override
    public void remove(String groupCode) throws Exception {

        log.debug("CodeGroupServiceImpl.remove");

        repository.deleteById(groupCode);
    }
}
