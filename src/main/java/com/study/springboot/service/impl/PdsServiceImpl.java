package com.study.springboot.service.impl;

import com.study.springboot.domain.Pds;
import com.study.springboot.domain.PdsFile;
import com.study.springboot.repository.PdsFileRepository;
import com.study.springboot.repository.PdsRepository;
import com.study.springboot.service.PdsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PdsServiceImpl implements PdsService {

    private final PdsRepository repository;
    private final PdsFileRepository fileRepository;

    @Override
    public void register(Pds pds) throws Exception {

        Pds pdsEntity = new Pds();

        pdsEntity.setItemName(pds.getItemName());
        pdsEntity.setDescription(pds.getDescription());

        String[] files = pds.getFiles();

        if (files == null) {
            return;
        }

        for (String fileName : files) {

            PdsFile pdsFile = new PdsFile();
            pdsFile.setFileName(fileName);

            pdsEntity.addItemFile(pdsFile);
        }

        repository.save(pdsEntity);
        pds.setItemId(pdsEntity.getItemId());

    }

    @Override
    public Pds read(Long itemId) throws Exception {

        Pds pdsEntity = repository.getReferenceById(itemId);
        Integer viewCnt = pdsEntity.getViewCnt();

        if (viewCnt == null) {
            viewCnt = 0;
        }

        pdsEntity.setViewCnt(viewCnt + 1);

        repository.save(pdsEntity);

        return repository.getReferenceById(itemId);
    }

    @Override
    public void modify(Pds item) throws Exception {

        Pds pdsEntity = repository.getReferenceById(item.getItemId());

        pdsEntity.setItemName(item.getItemName());
        pdsEntity.setDescription(item.getDescription());

        String[] files = item.getFiles();

        if (files != null) {
            pdsEntity.clearItemFile();

            for (String fileName : files) {
                PdsFile pdsFile = new PdsFile();
                pdsFile.setFileName(fileName);

                pdsEntity.addItemFile(pdsFile);

            }
            repository.save(pdsEntity);
        }
    }

    @Override
    public void remove(Long itemId) throws Exception {
        repository.deleteById(itemId);

    }

    @Override
    public List<Pds> list() throws Exception {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "itemId"));
    }

    @Override
    public List<String> getAttach(Long itemId) throws Exception {

        Pds pdsEntity = repository.getReferenceById(itemId);

        List<PdsFile> pdsFiles = pdsEntity.getPdsFiles();

        List<String> attachList = new ArrayList<>();
        for (PdsFile pdsFile : pdsFiles) {
            attachList.add(pdsFile.getFileName());
        }

        return attachList;
    }

    @Override
    public void updateAttachDownCnt(String fullName) throws Exception {

        List<PdsFile> pdsFileList = fileRepository.findByFullName(fullName);

        if (pdsFileList.size() > 0) {

            PdsFile pdsFileEntity = pdsFileList.get(0);

            Integer downCnt = pdsFileEntity.getDownCnt();

            if (downCnt == null) {
                downCnt = 0;
            }

            pdsFileEntity.setDownCnt(downCnt + 1);
            fileRepository.save(pdsFileEntity);
        }
    }
}
