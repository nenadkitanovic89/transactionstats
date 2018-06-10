package com.nenadkitanovic.transactionstatistics.controller;

import com.nenadkitanovic.transactionstatistics.dto.TransactionRequestDTO;
import com.nenadkitanovic.transactionstatistics.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Created by Nenad Kitanovic on 10.6.18..
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest extends AbstractControllerTest{

    private static final double AMOUNT = 10.5;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void createTransactionValidRequest() throws Exception {
        TransactionRequestDTO requestDTO = TransactionRequestDTO.builder()
                .amount(AMOUNT)
                .timestamp(System.currentTimeMillis() - 5000)
                .build();

        mvc.perform(post("/transactions").content(asJsonString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated());

        verify(transactionService).createTransaction(any(TransactionRequestDTO.class));
    }

    @Test
    public void createTransactionOldTimestampValidRequest() throws Exception {
        TransactionRequestDTO requestDTO = TransactionRequestDTO.builder()
                .amount(AMOUNT)
                .timestamp(System.currentTimeMillis() - 100000)
                .build();

        mvc.perform(post("/transactions").content(asJsonString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNoContent());

        verify(transactionService, times(0)).createTransaction(any(TransactionRequestDTO.class));
    }

    @Test
    public void createTransactionInvalidRequest() throws Exception {
        TransactionRequestDTO requestDTO = TransactionRequestDTO.builder().build();

        mvc.perform(post("/transactions").content(asJsonString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.fieldErrors[*].fieldName").isArray());

        verify(transactionService, times(0)).createTransaction(any(TransactionRequestDTO.class));
    }


    @Test
    public void getStatistics() {
        //TODO add tests
    }
}