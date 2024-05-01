package com.blog.jysblog.repository;

import com.blog.jysblog.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Boolean existsByTitle(String title);
}
