package com.study.springboot.service;

import com.study.springboot.domain.Board;
import com.study.springboot.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository;

    @Override
    public void register(Board board) {
        repository.save(board);
    }

    @Override
    public Board read(Long boardNo) {
        return repository.getReferenceById(boardNo);
    }

    @Override
    public void modify(Board board) {

        Board boardEntity = repository.getReferenceById(board.getBoardNo());

        boardEntity.setTitle(board.getTitle());
        boardEntity.setContent(board.getContent());

        repository.save(boardEntity);

    }

    @Override
    public void remove(Long boardNo) {
        repository.deleteById(boardNo);

    }

    @Override
    public List<Board> list() throws Exception {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "boardNo"));
    }
}
