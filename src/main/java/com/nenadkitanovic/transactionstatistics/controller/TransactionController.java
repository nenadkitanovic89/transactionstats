package com.nenadkitanovic.transactionstatistics.controller;

import com.nenadkitanovic.transactionstatistics.dto.StatisticsResponseDTO;
import com.nenadkitanovic.transactionstatistics.dto.TransactionRequestDTO;
import com.nenadkitanovic.transactionstatistics.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Duration;

/**
 * This controller can be split to separate controllers (classes) so we have controller for transactions and controller for stats
 * so the class has single responsibility
 * Created by Nenad Kitanovic on 9.6.18..
 */
@RestController
public class TransactionController {

    @Value("#{ T(java.time.Duration).parse('${settings.statistics.history-range:PT60S}')}")
    private Duration statsHistoryRange;

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Creates the transaction if timestamp is not older than 60seconds
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "Create new transaction if the timestamp is not more than 60seconds in the past")
    @PostMapping(path = "/transactions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createTransaction(@RequestBody @Valid TransactionRequestDTO requestDTO){
        //if requestTimestamp is older than 60seconds this request should be discarded for stats api since it doesn't affect the stats
        if(requestDTO.getTimestamp() < System.currentTimeMillis() - statsHistoryRange.getSeconds() * 1000){
            return ResponseEntity.noContent().build();
        }
        transactionService.createTransaction(requestDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * Gets the transaction statistics for last 60 seconds
     * @return
     */
    @ApiOperation(value = "Get transactions statistics for the last 60 seconds from now")
    @GetMapping(path = "/statistics", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StatisticsResponseDTO> getStatistics(){
        return ResponseEntity.ok(transactionService.getStatistics(statsHistoryRange));
    }
}
