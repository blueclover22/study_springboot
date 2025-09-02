package com.study.springboot.service.impl;

import com.study.springboot.domain.Notice;
import com.study.springboot.repository.NoticeRepository;
import com.study.springboot.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository repository;

    @Override
    public void register(Notice notice) throws Exception {
        repository.save(notice);
    }

    @Override
    public Notice read(Long noticeNo) throws Exception {
        return repository.getReferenceById(noticeNo);
    }

    @Override
    public void modify(Notice notice) throws Exception {
        Notice noticeEntity = repository.getReferenceById(notice.getNoticeNo());

        noticeEntity.setTitle(notice.getTitle());
        noticeEntity.setContent(notice.getContent());

        repository.save(noticeEntity);
    }

    @Override
    public void remove(Long noticeNo) throws Exception {
        repository.deleteById(noticeNo);
    }

    @Override
    public List<Notice> list() throws Exception {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "noticeNo"));
    }
}
