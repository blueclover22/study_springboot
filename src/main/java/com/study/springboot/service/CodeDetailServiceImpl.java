package com.study.springboot.service;

import com.study.springboot.domain.CodeDetail;
import com.study.springboot.repository.CodeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CodeDetailServiceImpl implements CodeDetailService{

    private final CodeDetailRepository repository;

    @Override
    public void register(CodeDetail codeDetail) throws Exception {
        String groupCode = codeDetail.getGroupCode();

        List<Object[]> rsList = repository.getMaxSortSeq(groupCode);

        Integer max = 0;
        if (rsList.size() > 0) {

            Object[] obj = rsList.get(0);
            System.out.println(obj);

            if(obj != null && obj.length > 0){
                max = (Integer) obj[0];
            }

        }

        codeDetail.setSortSeq(max + 1);

        repository.save(codeDetail);
    }

    @Override
    public List<CodeDetail> list() throws Exception {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "groupCode", "codeValue"));
    }
}
