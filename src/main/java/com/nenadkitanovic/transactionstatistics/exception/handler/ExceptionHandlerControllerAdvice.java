package com.nenadkitanovic.transactionstatistics.exception.handler;

import com.nenadkitanovic.transactionstatistics.exception.BadRequestWithMessageException;
import com.nenadkitanovic.transactionstatistics.exception.UnprocessableRequestException;
import com.nenadkitanovic.transactionstatistics.exception.message.BeanFieldError;
import com.nenadkitanovic.transactionstatistics.exception.message.ErrorMessage;
import com.nenadkitanovic.transactionstatistics.exception.message.GeneralError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nenad Kitanovic on 10.6.18..
 */
@Component
@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * 400. Is triggered if no other handler is triggered
     */
    @ExceptionHandler
    public ResponseEntity<Object> badRequestHandler(final Exception exception) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorMessage errorMessage;
        if (exception instanceof BadRequestWithMessageException) {
            final BadRequestWithMessageException badRequestWithMessageException = (BadRequestWithMessageException) exception;
            errorMessage = createGeneralErrorMessages(badRequestWithMessageException.getMessage(),
                    badRequestWithMessageException.getMessageText());
        } else {
            errorMessage = createGeneralErrorMessages(exception.getMessage());
        }
        return new ResponseEntity<>(errorMessage, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {
        return createValidationErrorMessage(ex.getBindingResult());
    }


    /**
     * 422 for unprocessable requests
     */
    @ExceptionHandler({ UnprocessableRequestException.class })
    public ResponseEntity<Object> unprocessableRequestHandler(final Exception exception) {
        final HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        final ErrorMessage errorMessage = createGeneralErrorMessages(exception.getMessage());
        return new ResponseEntity<>(errorMessage, status);
    }

    /**
     * Creates validationErrorMessage from the BindingResult
     */
    private ResponseEntity<Object> createValidationErrorMessage(final BindingResult result) {
        final HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        final ErrorMessage errorMessage = new ErrorMessage();
        final List<BeanFieldError> allErrorMessages = new ArrayList<>();
        result.getGlobalErrors().forEach(error -> {
            final BeanFieldError globalErrorMessage = new BeanFieldError();
            globalErrorMessage.setErrorType(StringUtils.isEmpty(error.getDefaultMessage()) ? error.getCode()
                    : error.getDefaultMessage());
            allErrorMessages.add(globalErrorMessage);
        });
        result.getFieldErrors().forEach(error -> {
            final BeanFieldError filedErrorMessage = new BeanFieldError();
            filedErrorMessage.setFieldName(error.getField());
            filedErrorMessage.setErrorType(StringUtils.isEmpty(error.getDefaultMessage()) ? error.getCode()
                    : error.getDefaultMessage());
            allErrorMessages.add(filedErrorMessage);
        });
        errorMessage.setFieldErrors(allErrorMessages);
        return new ResponseEntity<>(errorMessage, status);
    }

    private ErrorMessage createGeneralErrorMessages(final String exceptionMessageKey) {
        final GeneralError generalErrorMessage = new GeneralError(exceptionMessageKey);
        final ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setGeneralError(generalErrorMessage);
        return errorMessage;
    }

    private ErrorMessage createGeneralErrorMessages(final String exceptionMessageKey, final String exceptionMessageText) {
        final GeneralError generalErrorMessage = new GeneralError(exceptionMessageKey, exceptionMessageText);
        final ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setGeneralError(generalErrorMessage);
        return errorMessage;
    }

}
