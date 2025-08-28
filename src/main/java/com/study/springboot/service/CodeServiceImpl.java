package com.study.springboot.service;

import com.study.springboot.domain.CodeDetail;
import com.study.springboot.domain.CodeGroup;
import com.study.springboot.dto.CodeLabelValue;
import com.study.springboot.repository.CodeDetailRepository;
import com.study.springboot.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CodeServiceImpl implements CodeService {

    private final CodeGroupRepository codeGroupRepository;

    private final CodeDetailRepository codeDetailRepository;

    @Override
    public List<CodeLabelValue> getCodeGroupList() throws Exception {

        List<CodeGroup> codeGroups = codeGroupRepository.findAll(Sort.by(Sort.Direction.ASC, "groupCode"));

        List<CodeLabelValue> codeGroupList = new ArrayList<CodeLabelValue>();

        for (CodeGroup codeGroup : codeGroups) {
            codeGroupList.add(new CodeLabelValue(codeGroup.getGroupCode(), codeGroup.getGroupName()));
        }

        return codeGroupList;
    }

    @Override
    public List<CodeLabelValue> getCodeList(String groupCode) throws Exception {

        List<CodeDetail> codeDetails = codeDetailRepository.getCodeList(groupCode);

        List<CodeLabelValue> codeList = new ArrayList<CodeLabelValue>();

        for (CodeDetail codeDetail : codeDetails) {
            codeList.add(new CodeLabelValue(codeDetail.getCodeValue(), codeDetail.getCodeName()));
        }

        return codeList;
    }


}
