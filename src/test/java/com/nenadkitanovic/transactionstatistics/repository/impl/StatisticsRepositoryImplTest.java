package com.nenadkitanovic.transactionstatistics.repository.impl;

import com.nenadkitanovic.transactionstatistics.dto.StatisticsResponseDTO;
import com.nenadkitanovic.transactionstatistics.repository.StatisticsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Nenad Kitanovic on 10.6.18..
 */
@RunWith(SpringRunner.class)
public class StatisticsRepositoryImplTest {

    private static final double DELTA = 0.000001;

    private StatisticsRepository statisticsRepository;

    @Before
    public void init() {
        statisticsRepository = new StatisticsRepositoryImpl();
        ((StatisticsRepositoryImpl) statisticsRepository).setStatisticsCache(Collections.synchronizedMap(new Cache<>(60)));
    }

    @Test
    public void addTransactionThreeTransactions() {
        long currentTimeMillis = System.currentTimeMillis();
        /*
        now() - 5 seconds: 5.0, 10.0
        now()- 10 seconds: 15.0
         */
        statisticsRepository.addTransaction(currentTimeMillis - 5000, 5.0);
        statisticsRepository.addTransaction(currentTimeMillis - 5000, 10.0);
        statisticsRepository.addTransaction(currentTimeMillis - 10000, 15.0);
        StatisticsResponseDTO statistics = statisticsRepository.getStatistics(Duration.ofSeconds(60));

        Assert.assertEquals(30.0, statistics.getSum(), DELTA);
        Assert.assertEquals(5.0, statistics.getMin(), DELTA);
        Assert.assertEquals(15.0, statistics.getMax(), DELTA);
        Assert.assertEquals(10.0, statistics.getAvg(), DELTA);
        Assert.assertEquals(3, statistics.getCount(), DELTA);
    }

    @Test
    public void addTransactionThreeTransactionsOneOld() {
        long currentTimeMillis = System.currentTimeMillis();
        /*
        now() - 65 seconds: 5.0 (SHOULD NOT BE INCLUDED IN THE STATS)
        now() - 5 seconds: 10.0
        now()- 10 seconds: 15.0
         */
        statisticsRepository.addTransaction(currentTimeMillis - 65000, 5.0);
        statisticsRepository.addTransaction(currentTimeMillis - 5000, 10.0);
        statisticsRepository.addTransaction(currentTimeMillis - 10000, 15.0);
        StatisticsResponseDTO statistics = statisticsRepository.getStatistics(Duration.ofSeconds(60));

        Assert.assertEquals(25.0, statistics.getSum(), DELTA);
        Assert.assertEquals(10.0, statistics.getMin(), DELTA);
        Assert.assertEquals(15.0, statistics.getMax(), DELTA);
        Assert.assertEquals(12.5, statistics.getAvg(), DELTA);
        Assert.assertEquals(2, statistics.getCount(), DELTA);
    }

    @Test
    public void testTransactionsConcurrency() {
        Random rand = new Random();
        List<Thread> threads = new ArrayList<>();
        //prepare threads
        final int threadCount = 30000;
        final double amount = 10.0;
        for(int i = 0; i< threadCount; i++){
            threads.add(new Thread(() -> {
                long currentTimeMillis = System.currentTimeMillis();
                statisticsRepository.addTransaction(currentTimeMillis - rand.nextInt(50000), amount);
            }));
        }

        //start all the treads
        threads.forEach(Thread::start);

        StatisticsResponseDTO statistics = statisticsRepository.getStatistics(Duration.ofSeconds(60));

        Assert.assertEquals(threadCount * amount, statistics.getSum(), DELTA);
        Assert.assertEquals(amount, statistics.getMin(), DELTA);
        Assert.assertEquals(amount, statistics.getMax(), DELTA);
        Assert.assertEquals(amount, statistics.getAvg(), DELTA);
        Assert.assertEquals(threadCount, statistics.getCount(), DELTA);
    }
}