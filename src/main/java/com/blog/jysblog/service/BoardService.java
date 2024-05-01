package com.blog.jysblog.service;

import com.blog.jysblog.domain.Board;
import com.blog.jysblog.repository.BoardRepository;
import com.blog.jysblog.request.BoardRequest;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    public Long createBoard(BoardRequest request) {

        Board board = Board.builder()
            .title(request.title())
            .description(request.description())
            .isChecked(request.isChecked())
            .build();

        if (boardRepository.existsByTitle(request.title())) {
            new IllegalAccessException("아이디가 중복이에요");
        }

        return boardRepository.save(board).getId();


    }


}
