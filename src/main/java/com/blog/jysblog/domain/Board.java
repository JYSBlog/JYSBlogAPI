package com.blog.jysblog.domain;

import jakarta.persistence.*;
import jdk.jfr.Registered;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@Registered
@Entity
public class Board {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean isChecked;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<FileStore> fileStores;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments;


    public void setFile(FileStore fileStore){
        fileStores.add(fileStore);
        fileStore.setBoard(this);
    }
    public void setComment(Comment comment){
        comments.add(comment);
        comment.setBoard(this);
    }
}
