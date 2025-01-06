package com.megamaker.studyforu.common.email;

import com.megamaker.studyforu.category.domain.event.CategoryAddRequestedEvent;
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
    public void send(CategoryAddRequestedEvent event) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        try {
            messageHelper.setTo(environment.getProperty("admin.email"));  // 수신자
            messageHelper.setSubject("카테고리 추가 요청 by " + event.getUserInfo());  // 제목
            messageHelper.setText(event.getDescription()
                    + "\n" + event.getParentLevel() + "번 레벨의 하위 카테고리로 ["
                    + event.getCategoryName() + "] 추가 요청합니다.");  // 내용

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
