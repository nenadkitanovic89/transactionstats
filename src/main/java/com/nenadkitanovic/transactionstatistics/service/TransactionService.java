package com.nenadkitanovic.transactionstatistics.service;

import com.nenadkitanovic.transactionstatistics.dto.StatisticsResponseDTO;
import com.nenadkitanovic.transactionstatistics.dto.TransactionRequestDTO;

import java.time.Duration;

/**
 * Created by Nenad Kitanovic on 9.6.18..
 */
public interface TransactionService {
    void createTransaction(TransactionRequestDTO transactionRequestDTO);
    StatisticsResponseDTO getStatistics(Duration statsHistoryRange);
}
