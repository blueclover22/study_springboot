package com.study.springboot.service.impl;

import com.study.springboot.domain.CodeDetail;
import com.study.springboot.domain.CodeDetailId;
import com.study.springboot.repository.CodeDetailRepository;
import com.study.springboot.service.CodeDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CodeDetailServiceImpl implements CodeDetailService {

    private final CodeDetailRepository repository;

    @Override
    public void register(CodeDetail codeDetail) throws Exception {

        log.debug("CodeDetailServiceImpl.register");

        String groupCode = codeDetail.getGroupCode();

        Integer maxValue = repository.getMaxSortSeq(groupCode);
        int max = (maxValue == null) ? 0 : maxValue;

        codeDetail.setSortSeq(max + 1);

        repository.save(codeDetail);
    }

    @Override
    public List<CodeDetail> list() throws Exception {

        log.debug("CodeDetailServiceImpl.list");

        return repository.findAll(Sort.by(Sort.Direction.ASC, "groupCode", "codeValue"));
    }

    @Override
    public CodeDetail read(CodeDetail codeDetail) throws Exception {

        log.debug("CodeDetailServiceImpl.read");

        CodeDetailId codeDetailId = new CodeDetailId(codeDetail.getGroupCode(), codeDetail.getCodeValue());
        return repository.getReferenceById(codeDetailId);
    }

    @Override
    public void modify(CodeDetail codeDetail) throws Exception {

        log.debug("CodeDetailServiceImpl.modify");

        CodeDetailId codeDetailId = new CodeDetailId(codeDetail.getGroupCode(), codeDetail.getCodeValue());
        CodeDetail codeDetailEntity = repository.getReferenceById(codeDetailId);
        codeDetailEntity.setCodeValue(codeDetail.getCodeValue());
        codeDetailEntity.setCodeName(codeDetail.getCodeName());
        repository.save(codeDetailEntity);
    }

    @Override
    public void remove(CodeDetail codeDetail) throws Exception {

        log.debug("CodeDetailServiceImpl.remove");

        CodeDetailId codeDetailId = new CodeDetailId(codeDetail.getGroupCode(), codeDetail.getCodeValue());
        repository.deleteById(codeDetailId);
    }


}
