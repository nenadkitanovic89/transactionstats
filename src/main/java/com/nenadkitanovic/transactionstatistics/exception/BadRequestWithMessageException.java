package com.nenadkitanovic.transactionstatistics.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Nenad Kitanovic on 9.6.18..
 *
 * Request sent with bad parameters
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class BadRequestWithMessageException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "badRequest";

    private String message;
    private String messageText;

    public BadRequestWithMessageException() {
        super(DEFAULT_MESSAGE);
    }

    public BadRequestWithMessageException(final String message) {
        this.message = message;
    }

    public BadRequestWithMessageException(final String message, final String messageText) {
        this.message = message;
        this.messageText = messageText;
    }

}
