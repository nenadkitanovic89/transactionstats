package com.nenadkitanovic.transactionstatistics.config;

import com.nenadkitanovic.transactionstatistics.exception.handler.ExceptionHandlerControllerAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Nenad Kitanovic on 9.6.18..
 */
@Configuration
public class TransactionStatisticsConfig {

    @Bean(name = "exceptionHandlerControllerAdvice")
    public ExceptionHandlerControllerAdvice controllerAdvice() {
        return new ExceptionHandlerControllerAdvice();
    }
}
