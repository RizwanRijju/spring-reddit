package com.javaspring.springreddit.service;

import lombok.*;
import org.springframework.stereotype.*;
import org.thymeleaf.*;
import org.thymeleaf.context.*;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    String build(String message){
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }
}
