package com.nenadkitanovic.transactionstatistics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Nenad Kitanovic on 10.6.18..
 */
public abstract class AbstractControllerTest {

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
