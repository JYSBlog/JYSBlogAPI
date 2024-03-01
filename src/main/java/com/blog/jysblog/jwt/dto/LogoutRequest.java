package com.blog.jysblog.jwt.dto;

import lombok.Data;

@Data
public class LogoutRequest {

    private String userId;
    private String token;
}
