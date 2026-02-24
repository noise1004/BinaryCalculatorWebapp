package com.ontariotechu.sofe3980U;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BinaryController.class)
class BinaryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getDefault() throws Exception {
        mvc.perform(get("/"))
           .andExpect(status().isOk())
           .andExpect(view().name("calculator"))
           .andExpect(model().attribute("operand1", ""))
           .andExpect(model().attribute("operand1Focused", false));
    }

    @Test
    void getParameter() throws Exception {
        mvc.perform(get("/").param("operand1", "111"))
           .andExpect(status().isOk())
           .andExpect(view().name("calculator"))
           .andExpect(model().attribute("operand1", "111"))
           .andExpect(model().attribute("operand1Focused", true));
    }

    @Test
    void postParameter() throws Exception {
        mvc.perform(post("/")
                .param("operand1", "111")
                .param("operator", "+")
                .param("operand2", "111"))
           .andExpect(status().isOk())
           .andExpect(view().name("result"))
           .andExpect(model().attribute("result", "1110"))
           .andExpect(model().attribute("operand1", "111"));
    }
    @ParameterizedTest
    @CsvSource({
        "0,1010,0",
        "1,1010,1010",
        "111,10,1110"
    })
    void postMultiplyCases(String a, String b, String expected) throws Exception {
        mvc.perform(post("/")
                .param("operand1", a)
                .param("operator", "*")
                .param("operand2", b))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", expected));
    }

    @ParameterizedTest
    @CsvSource({
        "1111,1010,1010",
        "101,11,1",
        "0,111,0"
    })
    void postAndCases(String a, String b, String expected) throws Exception {
        mvc.perform(post("/")
                .param("operand1", a)
                .param("operator", "&")
                .param("operand2", b))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", expected));
    }

    @ParameterizedTest
    @CsvSource({
        "0,1010,1010",
        "1,1010,1011",
        "101,11,111"
    })
    void postInclusiveOrCases(String a, String b, String expected) throws Exception {
        mvc.perform(post("/")
                .param("operand1", a)
                .param("operator", "I")
                .param("operand2", b))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", expected));
    }
}