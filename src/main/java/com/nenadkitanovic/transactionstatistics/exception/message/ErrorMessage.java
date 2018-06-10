package com.nenadkitanovic.transactionstatistics.exception.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * Created by Nenad Kitanovic on 10.6.18..
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ErrorMessage {

    @JsonProperty
    private List<BeanFieldError> fieldErrors;

    @JsonProperty
    private GeneralError generalError;
}
