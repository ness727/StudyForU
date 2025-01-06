package com.megamaker.studyforu.common.email.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Email {
    private String to;
    private String subject;
    private String text;
}
