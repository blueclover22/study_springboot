package com.study.springboot.service;

import com.study.springboot.domain.Board;

import java.util.List;

public interface BoardService {

    public void register(Board board);

    public Board read(Long boardNo);

    public void modify(Board board);

    public void remove(Long boardNo);

    public List<Board> list() throws Exception;

}
