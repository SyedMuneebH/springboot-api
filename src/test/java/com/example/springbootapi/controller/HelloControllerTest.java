package com.example.springbootapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHello() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello from Spring Boot API!"));
    }

    @Test
    public void testHelloWithName() throws Exception {
        mockMvc.perform(get("/api/hello/John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, John!"));
    }

    @Test
    public void testHelloWithDifferentName() throws Exception {
        mockMvc.perform(get("/api/hello/Alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Alice!"));
    }

    @Test
    public void testEcho() throws Exception {
        String jsonPayload = "{\"key\": \"value\", \"number\": 42}";
        
        mockMvc.perform(post("/api/echo")
                .contentType("application/json")
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.echoed.key").value("value"))
                .andExpect(jsonPath("$.echoed.number").value(42))
                .andExpect(jsonPath("$.timestamp").isNumber());
    }

}
