package com.nenadkitanovic.transactionstatistics.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Nenad Kitanovic on 10.6.18..
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class StatisticsEntity {
    private double sum;
    private double max;
    private double min;
    private long count;
}
