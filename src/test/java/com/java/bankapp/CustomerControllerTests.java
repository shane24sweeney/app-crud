package com.java.bankapp;

import com.bank.AppMainApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AppMainApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerControllerTests {

    private static final String CUSTOMERS = "/api/customers";
    private static final String JOHN =
            "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"phoneNumber\":\"1234567890\"}";

    @Autowired
    private MockMvc mockMvc;

    private void createJohn() throws Exception {
        mockMvc.perform(post(CUSTOMERS).contentType(MediaType.APPLICATION_JSON).content(JOHN))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void createsAndReadsCustomer() throws Exception {
        createJohn();

        mockMvc.perform(get(CUSTOMERS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        mockMvc.perform(get(CUSTOMERS + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void updatesCustomer() throws Exception {
        createJohn();

        mockMvc.perform(put(CUSTOMERS + "/1").contentType(MediaType.APPLICATION_JSON)
                        .content(JOHN.replace("1234567890", "5555555555")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value("5555555555"));
    }

    @Test
    void deletesCustomer() throws Exception {
        createJohn();

        mockMvc.perform(delete(CUSTOMERS + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer deleted: 1"));

        mockMvc.perform(get(CUSTOMERS + "/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void returnsNotFoundForUnknownCustomer() throws Exception {
        mockMvc.perform(get(CUSTOMERS + "/99")).andExpect(status().isNotFound());
        mockMvc.perform(put(CUSTOMERS + "/99").contentType(MediaType.APPLICATION_JSON).content(JOHN))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete(CUSTOMERS + "/99")).andExpect(status().isNotFound());
    }
}
