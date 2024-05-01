package com.blog.jysblog.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record BoardRequest(

    @NotEmpty(message = "제목을 입력하세요")
    String title,
    String description,
    boolean isChecked

) {


}
