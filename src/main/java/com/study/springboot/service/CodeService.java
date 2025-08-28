package com.study.springboot.service;

import com.study.springboot.dto.CodeLabelValue;

import java.util.List;

public interface CodeService {

    // 코드 그룹 목록 조회
    public List<CodeLabelValue> getCodeGroupList() throws Exception;

    // 상세 코드 목록 조회
    public List<CodeLabelValue> getCodeList(String groupCode) throws Exception;
}
