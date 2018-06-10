package com.nenadkitanovic.transactionstatistics.repository.impl;

import com.nenadkitanovic.transactionstatistics.dto.StatisticsResponseDTO;
import com.nenadkitanovic.transactionstatistics.entity.StatisticsEntity;
import com.nenadkitanovic.transactionstatistics.repository.StatisticsRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;

/**
 * Created by Nenad Kitanovic on 10.6.18..
 */
@Component
@Setter
public class StatisticsRepositoryImpl implements StatisticsRepository {

    @Value("${settings.statistics.history-entries:60}")
    private Integer statsHistoryEntries;

    private Map<Long, StatisticsEntity> statisticsCache;

    @PostConstruct
    public void init(){
        this.statisticsCache = Collections.synchronizedMap(new Cache<>(statsHistoryEntries));
    }

    /**
     * Adds the transaction to stats where the seconds of the transaction is the cache key
     * @param timestamp
     * @param amount
     */
    @Override
    public void addTransaction(Long timestamp, Double amount) {
        //cache key is the value in seconds (every second is a new key)
        Long timestampSeconds = timestamp / 1000;
        statisticsCache.compute(timestampSeconds, (key, value) -> {
            //if value doesn't exist for this second then create a new entry
            if (value == null) {
                return StatisticsEntity.builder().count(1L).sum(amount).min(amount).max(amount).build();
            }
            //otherwise update stats for this second (transactions performed in this seconds)
            value.setCount(value.getCount() + 1);
            value.setSum(value.getSum() + amount);
            if (amount < value.getMin()) {
                value.setMin(amount);
            }
            if (amount > value.getMax()) {
                value.setMax(amount);
            }
            return value;
        });
    }

    /**
     * Calculates the stats for transactions in last 60sec, where cache contains only 60 entries (for the last 60 seconds)
     * @param statsHistoryRange
     * @return
     */
    @Override
    public StatisticsResponseDTO getStatistics(Duration statsHistoryRange) {
        long startTime = System.currentTimeMillis() / 1000;
        long endTime = startTime - statsHistoryRange.getSeconds();
        final StatisticsResponseDTO statisticsResponseDTO = new StatisticsResponseDTO();
        //go through the collection and calculate the ones with date in range, cache size is 60, so it's O(constant) complexity
        statisticsCache.forEach((key, value) -> {
            //there might be "holes" in time, when there were no transactions, so we have to check the key if it should be used or not
            if (key >= endTime && key <= startTime) {
                statisticsResponseDTO.setSum(statisticsResponseDTO.getSum() + value.getSum());
                statisticsResponseDTO.setCount(statisticsResponseDTO.getCount() + value.getCount());
                if(statisticsResponseDTO.getMax() == null || value.getMax() > statisticsResponseDTO.getMax()) {
                    statisticsResponseDTO.setMax(value.getMax());
                }
                if(statisticsResponseDTO.getMin() == null || value.getMin() < statisticsResponseDTO.getMin()) {
                    statisticsResponseDTO.setMin(value.getMin());
                }
            }
        });
        long transactionCount = statisticsResponseDTO.getCount() > 0 ? statisticsResponseDTO.getCount() : 1;    //division by 0
        statisticsResponseDTO.setAvg(statisticsResponseDTO.getSum() / transactionCount);
        return statisticsResponseDTO;
    }
}

