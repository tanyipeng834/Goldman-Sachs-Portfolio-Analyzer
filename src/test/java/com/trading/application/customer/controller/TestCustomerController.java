package com.trading.application.customer.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestCustomerController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnCustomer() throws Exception {

        String userId = "5cJ0NI3WpPLi9hCQKZG0";

        this.mockMvc.perform(get("/customer/" + userId)).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("dooshik"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("kim"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.dateJoined").value("14/9/2023"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalCapitalAvailable").value(999999))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("moonsan@gmail.com"));

    }


}
