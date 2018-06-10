package com.nenadkitanovic.transactionstatistics.service.impl;

import com.nenadkitanovic.transactionstatistics.dto.StatisticsResponseDTO;
import com.nenadkitanovic.transactionstatistics.dto.TransactionRequestDTO;
import com.nenadkitanovic.transactionstatistics.repository.StatisticsRepository;
import com.nenadkitanovic.transactionstatistics.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Created by Nenad Kitanovic on 9.6.18..
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private final StatisticsRepository statisticsRepository;

    @Autowired
    public TransactionServiceImpl(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override public void createTransaction(TransactionRequestDTO transactionRequestDTO) {
        statisticsRepository.addTransaction(transactionRequestDTO.getTimestamp(), transactionRequestDTO.getAmount());
    }

    @Override public StatisticsResponseDTO getStatistics(Duration statsHistoryRange) {
        return statisticsRepository.getStatistics(statsHistoryRange);
    }
}
