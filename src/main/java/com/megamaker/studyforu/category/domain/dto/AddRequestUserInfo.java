package com.megamaker.studyforu.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddRequestUserInfo {
    private String username;
    private Long userId;
}
