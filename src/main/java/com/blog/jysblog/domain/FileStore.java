package com.blog.jysblog.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.jdbc.Size;
import org.springdoc.webmvc.core.fn.SpringdocRouteBuilder;
import org.springframework.http.MediaType;

import java.security.PrivateKey;

@RequiredArgsConstructor
@Getter
@Entity
public class FileStore extends AuditingEntity {
    @Id
    @Column(name = "filestore_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalName;
    private String fileName;
    private String path;
    private String contentType;
    private Long size;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
