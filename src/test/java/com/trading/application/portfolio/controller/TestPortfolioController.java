package com.trading.application.portfolio.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPortfolioController {

    @Autowired
    private MockMvc mockMvc;

    String token = "";
   // add your token

    @Test
    public void shouldCreatePortfolio() throws Exception {

        String body = "{\"portfolioName\":\"portfolio_name\",\"portfolioDescription\":\"portfolio_desc\"," +
                "\"portfolioValue\":0," + "\"unrealisedPnL\":0,\"dateCreated\":\"14/10/2023\",\"userId\":\"fad\"," +
                "\"capital\":5000," + "\"isPublic\":true,\"portStock\":{\"IBM\":[{\"stockBoughtPrice\":5," +
                "\"quantity\":5," + "\"dateBought\":\"23/9/2023\"}],\"TSLA\":[{\"stockBoughtPrice\":6,\"quantity\":2,"
                + "\"dateBought\":\"23/9/2023\"}]}}";

        mockMvc.perform(post("/portfolio/create").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(body)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void shouldReturnPortfolio() throws Exception {

        String portfolioId = "BjHkZggaxiFlhKdSJXcy";

        mockMvc.perform(get("/portfolio/" + portfolioId).header("authorization", "Bearer " + token)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.portfolioId").value(portfolioId)).andExpect(MockMvcResultMatchers.jsonPath("$.portfolioName").value("portfolio_name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.portfolioDescription").value("portfolio_desc")).andExpect(MockMvcResultMatchers.jsonPath("$.portfolioValue").value(37))
                .andExpect(MockMvcResultMatchers.jsonPath("$.unrealisedPnL").value(0)).andExpect(MockMvcResultMatchers.jsonPath("$.dateCreated").value("14/10/2023"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("fad")).andExpect(MockMvcResultMatchers.jsonPath("$.capital").value(5000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.public").value(true)).andExpect(MockMvcResultMatchers.jsonPath("$.countryExposure", nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.portStock.IBM[0].stockBoughtPrice").value(5)).andExpect(MockMvcResultMatchers.jsonPath("$.portStock.IBM[0].quantity").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.portStock.IBM[0].dateBought").value("23/9/2023")).andExpect(MockMvcResultMatchers.jsonPath("$.portStock.TSLA[0].stockBoughtPrice").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.portStock.TSLA[0].quantity").value(2)).andExpect(MockMvcResultMatchers.jsonPath("$.portStock.TSLA[0].dateBought").value("23/9/2023"));
    }

    @Test
    public void shouldDeletePortfolio() throws Exception {

        String portfolioId = "Rpl4b9wLJZxOtZ5oL84u";

        mockMvc.perform(post("/portfolio/delete/" + portfolioId).header("authorization", "Bearer " + token)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void shouldUpdatePortfolio() throws Exception {

        String portfolioId = "x7WENlGE2kV6dtt0mjCB";

        String body = "{\"portfolioName\":\"new_portfolio_name\",\"portfolioDescription\":\"new_portfolio_desc\"," +
                "\"capital\":6000,\"isPublic\":false,\"delete\":{\"TSLA\":[0]},\"add\":{\"TSLA\":[{\"quantity\":7," + "\"dateBought\":\"23/9/2023\",\"stockBoughtPrice\":450},{\"quantity\":7,\"dateBought\":\"23/9/2023\"," + "\"stockBoughtPrice\":450}]},\"update\":{\"IBM\":{\"1\":{\"stockBoughtPrice\":5,\"quantity\":10," + "\"dateBought\":\"23/9/2023\"}}},\"portfolioId\":\"x7WENlGE2kV6dtt0mjCB\",\"userId\":\"fad\"}";

        mockMvc.perform(post("/portfolio/updateportfolio/").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(body)).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnAllPortfoliosOfAUser() throws Exception {

        String userId = "fad";

        mockMvc.perform(get("/portfolio/getportfolios/" + userId).header("authorization", "Bearer " + token)).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnTotalPortfolioValue() throws Exception {

        String portfolioId = "BjHkZggaxiFlhKdSJXcy";

        mockMvc.perform(get("/portfolio/gettotalportfoliovalue/" + portfolioId).header("authorization", "Bearer " + token)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAllPublicPortfolios() throws Exception {

        mockMvc.perform(get("/portfolio/getpublicportfolios").header("authorization", "Bearer " + token)).andDo(print())
                .andExpect(status().isOk());
    }



}



//package com.trading.application.customer.controller;
//
//        import com.trading.application.customer.repository.CustomerRepository;
//        import com.trading.application.customer.service.CustomerService;
//        import org.junit.jupiter.api.Test;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//        import org.springframework.boot.test.context.SpringBootTest;
//        import org.springframework.test.web.servlet.MockMvc;
//        import org.junit.jupiter.api.Test;
//        import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//        import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class TestCustomerController {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void shouldReturnCustomer() throws Exception {
//
////        String userId = "5cJ0NI3WpPLi9hCQKZG0";
////
////        this.mockMvc.perform(get("/customer/" + userId)).andDo(print()).andExpect(status().isOk())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
////                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("dooshik"))
////                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("kim"))
////                .andExpect(MockMvcResultMatchers.jsonPath("$.dateJoined").value("14/9/2023"))
////                .andExpect(MockMvcResultMatchers.jsonPath("$.totalCapitalAvailable").value(999999))
////                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("moonsan@gmail.com"));
////
//    }
//
//    @Test
//    public void shouldCreateCustomer() throws Exception{
//
//    }
//
//
//}
