package com.trading.application.customer.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    String token = "";
    // add ur token


    @Test
    public void shouldReturnCustomer() throws Exception {

//        String userId = "5cJ0NI3WpPLi9hCQKZG0";
//
//        this.mockMvc.perform(get("/customer/" + userId)).andDo(print()).andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("dooshik"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("kim"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.dateJoined").value("14/9/2023"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalCapitalAvailable").value(999999))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("moonsan@gmail.com"));
//
    }

    @Test
    public void shouldCreateCustomer() throws Exception {
        String email = "test@gmail.com";
        String id = "testid";
        String name = "test.user.2021";
        String picture = "https://s.gravatar.com/avatar/f46f055e3618f5939cfc4a8e6572279c?s=480&r=pg&d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fxu.png";
        String updatedAt = "2023-10-01T08:19:57.640Z";
        //to confirm data type
        float totalCapitalAvailable = 10000;

        String customerJson = String.format(
                "{\"email\":\"%s\",\"id\":\"%s\",\"name\":\"%s\",\"picture\":\"%s\",\"totalCapitalAvailable\":%f,\"updatedAt\":\"%s\"}",
                email, id, name, picture, totalCapitalAvailable, updatedAt);

        mockMvc.perform(post("/customer/").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(customerJson)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldGetCustomerById() throws Exception {
        String id = "testid";
        mockMvc.perform(get("/customer/" + id).header("authorization", "Bearer " + token)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerData.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerData.email").value("test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerData.name").value("test.user.2021"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerData.totalCapitalAvailable").value(10000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerData.updatedAt").value("2023-10-01T08:19:57.640Z"));}

    @Test
    void shouldUpdateCustomerName() throws Exception {

        String id = "testid";
        String name = "new_name";
        String email = "test@gmail.com";
        String picture = "https://s.gravatar.com/avatar/f46f055e3618f5939cfc4a8e6572279c?s=480&r=pg&d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fxu.png";
        String updatedAt = "2023-10-01T08:19:57.640Z";
        //to confirm data type
        float totalCapitalAvailable = 10000;

        String body = String.format(
                "{\"email\":\"%s\",\"id\":\"%s\",\"name\":\"%s\",\"picture\":\"%s\",\"totalCapitalAvailable\":%f,\"updatedAt\":\"%s\"}",
                email, id, name, picture, totalCapitalAvailable, updatedAt);

        mockMvc.perform(put("/customer/updatename").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(body)).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void shouldUpdateCustomerEmail() throws Exception {

        String id = "testid";
        String name = "new_name";
        String email = "newtest@gmail.com";
        String picture = "https://s.gravatar.com/avatar/f46f055e3618f5939cfc4a8e6572279c?s=480&r=pg&d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fxu.png";
        String updatedAt = "2023-10-01T08:19:57.640Z";
        //to confirm data type
        float totalCapitalAvailable = 10000;

        String body = String.format(
                "{\"email\":\"%s\",\"id\":\"%s\",\"name\":\"%s\",\"picture\":\"%s\",\"totalCapitalAvailable\":%f,\"updatedAt\":\"%s\"}",
                email, id, name, picture, totalCapitalAvailable, updatedAt);

        mockMvc.perform(put("/customer/updateemail").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(body)).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void shouldUpdateCustomerCapital() throws Exception {
        String id = "testid";
        String name = "new_name";
        String email = "test@gmail.com";
        String picture = "https://s.gravatar.com/avatar/f46f055e3618f5939cfc4a8e6572279c?s=480&r=pg&d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fxu.png";
        String updatedAt = "2023-10-01T08:19:57.640Z";
        //to confirm data type
        float totalCapitalAvailable = 12000;

        String body = String.format(
                "{\"email\":\"%s\",\"id\":\"%s\",\"name\":\"%s\",\"picture\":\"%s\",\"totalCapitalAvailable\":%f,\"updatedAt\":\"%s\"}",
                email, id, name, picture, totalCapitalAvailable, updatedAt);

        mockMvc.perform(put("/customer/updatecapital").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(body)).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void shouldDeleteCustomerAccount() throws Exception {

        String id = "testid";
        mockMvc.perform(delete("/customer/deletecustomer/" + id).header("authorization", "Bearer " + token)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    void shouldGetCapital() throws Exception {
        String id = "testid";
        float expectedCapital = 12000;

        mockMvc.perform(get("/customer/getcapital/" + id)
                        .header("authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedCapital)));

}

}