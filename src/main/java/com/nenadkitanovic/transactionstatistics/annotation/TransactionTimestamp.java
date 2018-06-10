package com.nenadkitanovic.transactionstatistics.annotation;

import com.nenadkitanovic.transactionstatistics.validator.TransactionTimestampValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Nenad Kitanovic on 9.6.18..
 */
@Documented
@Constraint(validatedBy = TransactionTimestampValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionTimestamp {

    String message() default "{transactionTimestampNotValid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}