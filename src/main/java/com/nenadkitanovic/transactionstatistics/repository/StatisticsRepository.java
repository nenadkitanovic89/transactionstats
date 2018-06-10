package com.nenadkitanovic.transactionstatistics.repository;

import com.nenadkitanovic.transactionstatistics.dto.StatisticsResponseDTO;

import java.time.Duration;

/**
 * Created by Nenad Kitanovic on 10.6.18..
 */
public interface StatisticsRepository {

    void addTransaction(Long timestamp, Double amount);

    StatisticsResponseDTO getStatistics(Duration statsHistoryRange);
}
