package com.javaspring.springreddit.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;


public class SpringRedditException extends RuntimeException {

    public SpringRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringRedditException(String exMessage) {
        super(exMessage);
    }
}
