package com.nenadkitanovic.transactionstatistics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Nenad Kitanovic on 9.6.18..
 *
 * Request sent with invalid parameters
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableRequestException extends RuntimeException {

    private static final long serialVersionUID = 461516410536174685L;
    private static final String DEFAULT_MESSAGE = "unprocessableRequest";

    public UnprocessableRequestException() {
        super(DEFAULT_MESSAGE);
    }

    public UnprocessableRequestException(final String message) {
        super(message);
    }

}
