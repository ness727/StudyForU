package com.megamaker.studyforu.common.email;

import com.megamaker.studyforu.category.domain.event.CategoryAddRequestedEvent;
import com.megamaker.studyforu.common.email.MailSendService;
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
    @EventListener(CategoryAddRequestedEvent.class)
    public void handle(CategoryAddRequestedEvent event) {
        log.info("이벤트 수신 성공");
        mailSendService.send(event);
    }
}
