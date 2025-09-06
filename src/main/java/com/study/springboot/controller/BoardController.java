package com.study.springboot.controller;

import com.study.springboot.domain.Board;
import com.study.springboot.domain.CustomUser;
import com.study.springboot.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService service;

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping
    public ResponseEntity<Board> register(@Validated @RequestBody Board board,
                                          @AuthenticationPrincipal CustomUser customUser) throws Exception {

        log.debug("BoardController.register");

        String userId = customUser.getUserId();
        board.setWriter(userId);
        service.register(board);
        Board createdBoard = service.read(board.getBoardNo());
        return new ResponseEntity<>(createdBoard, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Board>> list() throws Exception {

        log.debug("BoardController.list");

        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

    @GetMapping("/{boardNo}")
    public ResponseEntity<Board> read(@PathVariable("boardNo") Long boardNo) throws Exception {

        log.debug("BoardController.read");

        return new ResponseEntity<>(service.read(boardNo), HttpStatus.OK);
    }

    @PreAuthorize("(hasRole('MEMBER') and principal.username == #writer) or hasRole('ADMIN')")
    @DeleteMapping("/{boardNo}")
    public ResponseEntity<Void> remove(@PathVariable("boardNo") Long boardNo,
                                       @RequestParam("writer") String writer) throws Exception {

        log.debug("BoardController.remove");

        service.remove(boardNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MEMBER') and principal.username == #board.writer")
    @PutMapping("/{boardNo}")
    public ResponseEntity<Void> modify(@PathVariable("boardNo") Long boardNo,
                                       @Validated @RequestBody Board board) throws Exception {

        log.debug("BoardController.modify");

        board.setBoardNo(boardNo);
        service.modify(board);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
