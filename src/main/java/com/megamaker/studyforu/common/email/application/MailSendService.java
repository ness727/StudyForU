package com.megamaker.studyforu.common.email.application;

import com.megamaker.studyforu.category.domain.dto.CategoryAddRequest;

public interface MailSendService {
    void send(CategoryAddRequest categoryAddRequest);
}
