package com.study.springboot.service.impl;

import com.study.springboot.domain.Board;
import com.study.springboot.repository.BoardRepository;
import com.study.springboot.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository;

    @Override
    public void register(Board board) { 

        log.debug("BoardServiceImpl.register");

        repository.save(board);
    }

    @Override
    public Board read(Long boardNo) {

        log.debug("BoardServiceImpl.read");

        return repository.getReferenceById(boardNo);
    }

    @Override
    public void modify(Board board) {

        log.debug("BoardServiceImpl.modify");

        Board boardEntity = repository.getReferenceById(board.getBoardNo());

        boardEntity.setTitle(board.getTitle());
        boardEntity.setContent(board.getContent());

        repository.save(boardEntity);

    }

    @Override
    public void remove(Long boardNo) {

        log.debug("BoardServiceImpl.remove");

        repository.deleteById(boardNo);

    }

    @Override
    public List<Board> list() throws Exception {

        log.debug("BoardServiceImpl.list");

        return repository.findAll(Sort.by(Sort.Direction.DESC, "boardNo"));
    }
}
