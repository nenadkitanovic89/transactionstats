package com.nenadkitanovic.transactionstatistics.exception.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Nenad Kitanovic on 10.6.18..
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralError {

    @JsonProperty
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String messageText;

    public GeneralError() {
    }

    public GeneralError(final String message) {
        this.message = message;
    }

    public GeneralError(final String message, final String messageText) {
        this.message = message;
        this.messageText = messageText;
    }
}
