package com.blog.jysblog.controller;

import com.blog.jysblog.request.BoardRequest;
import com.blog.jysblog.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid BoardRequest request) {

        Long boardId = boardService.createBoard(request);
        return ResponseEntity.ok("created board id is " + boardId);
    }
}


