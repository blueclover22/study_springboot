package com.study.springboot.service;

import com.study.springboot.domain.CodeGroup;

import java.util.List;

public interface CodeGroupService {
    void register(CodeGroup codeGroup) throws Exception;

    List<CodeGroup> list() throws Exception;

    CodeGroup read(String groupCode) throws Exception;

    void modify(CodeGroup codeGroup) throws Exception;

    void remove(String groupCode) throws Exception;
}
