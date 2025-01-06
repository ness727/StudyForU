package com.megamaker.studyforu.common.email.event;

import com.megamaker.studyforu.category.domain.dto.CategoryAddRequest;
import com.megamaker.studyforu.category.domain.event.CategoryAddedEvent;
import com.megamaker.studyforu.common.email.application.MailSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryAddedEventHandler {
    private final MailSendService mailSendService;

    @Async
    @EventListener(CategoryAddedEvent.class)
    public void handle(CategoryAddedEvent event) {
        log.info("이벤트 수신 성공");
        CategoryAddRequest request = new CategoryAddRequest(event.getParentId(), event.getLevel(), event.getName());
        mailSendService.send(request);
    }
}
