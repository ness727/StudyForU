package com.megamaker.studyforu.common.email;

import com.megamaker.studyforu.category.domain.dto.CategoryAddRequest;
import com.megamaker.studyforu.category.domain.event.CategoryAddRequestedEvent;

public interface MailSendService {
    void send(CategoryAddRequestedEvent addRequestedEvent);
}
