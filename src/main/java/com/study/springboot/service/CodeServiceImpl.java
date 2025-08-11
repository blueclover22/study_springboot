package com.study.springboot.service;

import com.study.springboot.domain.CodeGroup;
import com.study.springboot.dto.CodeLabelValue;
import com.study.springboot.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CodeServiceImpl implements CodeService {

    private final CodeGroupRepository repository;

    @Override
    public List<CodeLabelValue> getCodeGroupList() throws Exception {

        List<CodeGroup> codeGroups = repository.findAll(Sort.by(Sort.Direction.ASC, "groupCode"));

        List<CodeLabelValue> codeGroupList = new ArrayList<CodeLabelValue>();

        for (CodeGroup codeGroup : codeGroups) {
            codeGroupList.add(new CodeLabelValue(codeGroup.getGroupCode(), codeGroup.getGroupName()));
        }

        return codeGroupList;
    }
}
