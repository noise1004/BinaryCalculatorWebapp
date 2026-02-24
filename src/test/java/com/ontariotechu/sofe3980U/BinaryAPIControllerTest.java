package com.ontariotechu.sofe3980U;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BinaryAPIController.class)
class BinaryAPIControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void add() throws Exception {
        mvc.perform(get("/add")
                .param("operand1", "111")
                .param("operand2", "1010"))
           .andExpect(status().isOk())
           .andExpect(content().string("10001"));
    }

    @Test
    void add_json() throws Exception {
        mvc.perform(get("/add_json")
                .param("operand1", "111")
                .param("operand2", "1010"))
           .andExpect(status().isOk())
           // Most APIs return these as strings since they are binary digits
           .andExpect(jsonPath("$.operand1").value("111"))
           .andExpect(jsonPath("$.operand2").value("1010"))
           .andExpect(jsonPath("$.result").value("10001"))
           .andExpect(jsonPath("$.operator").value("add"));
    }

    @ParameterizedTest
    @CsvSource({
        "0,1010,0",
        "1,1010,1010",
        "10,11,110"
    })
    void mulTextCases(String a, String b, String expected) throws Exception {
        mvc.perform(get("/mul").param("operand1", a).param("operand2", b))
            .andExpect(status().isOk())
            .andExpect(content().string(expected));
    }

    @ParameterizedTest
    @CsvSource({
        "1111,1010,1010",
        "101,11,1",
        "0,111,0"
    })
    void andTextCases(String a, String b, String expected) throws Exception {
        mvc.perform(get("/and").param("operand1", a).param("operand2", b))
            .andExpect(status().isOk())
            .andExpect(content().string(expected));
    }

    @ParameterizedTest
    @CsvSource({
        "0,1010,1010",
        "1,1010,1011",
        "101,11,111"
    })
    void orTextCases(String a, String b, String expected) throws Exception {
        mvc.perform(get("/or").param("operand1", a).param("operand2", b))
            .andExpect(status().isOk())
            .andExpect(content().string(expected));
    }
}