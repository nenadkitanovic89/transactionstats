package com.nenadkitanovic.transactionstatistics.validator;

import com.nenadkitanovic.transactionstatistics.annotation.TransactionTimestamp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Nenad Kitanovic on 9.6.18..
 */
public class TransactionTimestampValidator implements ConstraintValidator<TransactionTimestamp, Long>{
    @Override
    public void initialize(TransactionTimestamp timestamp) {
        //It should do nothing
    }

    @Override
    public boolean isValid(final Long timestamp, final ConstraintValidatorContext constraintValidatorContext) {
        //timestamp = null or timestamp negative number
        if(timestamp == null || timestamp <= 0){
            return false;
        }
        //current timestamp should be after or at the same time as the timestamp
        //we could also add range of valid dates here
        return System.currentTimeMillis() - timestamp >= 0;
    }
}
