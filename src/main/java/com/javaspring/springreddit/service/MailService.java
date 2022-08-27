package com.javaspring.springreddit.service;

import com.javaspring.springreddit.exceptions.*;
import com.javaspring.springreddit.model.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.mail.*;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    void sendMail(NotificationEmail notificationEmail){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("rijju473@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try{
            mailSender.send(messagePreparator);
            log.info("Activation Email Sent!!");
        } catch(MailException e){
            throw new SpringRedditException("Exception occured when sending mail to user");
        }
    }
}
