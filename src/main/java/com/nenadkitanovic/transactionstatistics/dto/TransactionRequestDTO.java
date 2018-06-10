package com.nenadkitanovic.transactionstatistics.dto;

import com.nenadkitanovic.transactionstatistics.annotation.TransactionTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Created by Nenad Kitanovic on 9.6.18..
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequestDTO {
    @NotNull(message = "amountCannotBeNull")
    @Positive(message = "amountMustBePositive")
    private Double amount;
    @TransactionTimestamp(message = "timestampMustBeValidDateInPast")  //could set some date range validation / custom annotation and validator if needed
    private Long timestamp;
}
