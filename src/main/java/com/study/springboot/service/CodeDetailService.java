package com.study.springboot.service;

import com.study.springboot.domain.CodeDetail;

import java.util.List;

public interface CodeDetailService{

    void register(CodeDetail codeDetail) throws Exception;

    List<CodeDetail> list() throws Exception;
}
