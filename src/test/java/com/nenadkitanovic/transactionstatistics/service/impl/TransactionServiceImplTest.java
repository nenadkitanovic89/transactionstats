package com.nenadkitanovic.transactionstatistics.service.impl;

import com.nenadkitanovic.transactionstatistics.dto.TransactionRequestDTO;
import com.nenadkitanovic.transactionstatistics.repository.StatisticsRepository;
import com.nenadkitanovic.transactionstatistics.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by Nenad Kitanovic on 10.6.18..
 */
@RunWith(SpringRunner.class)
public class TransactionServiceImplTest {

    @Mock
    private StatisticsRepository statisticsRepository;

    private TransactionService transactionService;

    @Before
    public void init() {
        transactionService = new TransactionServiceImpl(statisticsRepository);
    }

    @Test
    public void createTransaction() {
        TransactionRequestDTO requestDTO = TransactionRequestDTO.builder()
                .amount(100.0)
                .timestamp(System.currentTimeMillis() - 10000)
                .build();

        transactionService.createTransaction(requestDTO);

        verify(statisticsRepository).addTransaction(eq(requestDTO.getTimestamp()), eq(requestDTO.getAmount()));
    }

    @Test
    public void getStatistics() {
        Duration duration = Duration.of(60, ChronoUnit.SECONDS);

        transactionService.getStatistics(duration);

        verify(statisticsRepository).getStatistics(eq(duration));
    }
}