package com.megamaker.studyforu.common.email.application;

import com.megamaker.studyforu.category.domain.dto.CategoryAddRequest;
import com.megamaker.studyforu.category.domain.event.CategoryAddedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendServiceImpl implements MailSendService {
    private final Environment environment;
    private final JavaMailSender javaMailSender;

    @Override
    public void send(CategoryAddRequest request) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        try {
            messageHelper.setTo(environment.getProperty("admin.email"));  // 수신자
            messageHelper.setSubject("테스트 이메일 제목");  // 제목
            messageHelper.setText(request.getName());  // 내용

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
