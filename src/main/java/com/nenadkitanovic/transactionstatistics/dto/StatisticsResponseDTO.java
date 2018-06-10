package com.nenadkitanovic.transactionstatistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Nenad Kitanovic on 9.6.18..
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsResponseDTO {
    private Double sum = 0.0;
    private Double avg = 0.0;
    private Double max;
    private Double min;
    private Long count = 0L;
}
