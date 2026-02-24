package com.ontariotechu.sofe3980U;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getDefault() throws Exception {
        mvc.perform(get("/hello"))
           .andExpect(status().isOk())
           .andExpect(view().name("hello"))
           .andExpect(model().attribute("name", "World"));
    }

    @Test
    void helloWithName() throws Exception {
        mvc.perform(get("/hello").param("name", "Doe"))
           .andExpect(status().isOk())
           .andExpect(view().name("hello"))
           .andExpect(model().attribute("name", "Doe"));
    }
}