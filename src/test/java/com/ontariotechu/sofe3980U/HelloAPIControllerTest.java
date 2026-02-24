package com.ontariotechu.sofe3980U;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloAPIController.class)
class HelloAPIControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void helloAPINoParameter() throws Exception {
        mvc.perform(get("/helloAPI"))
           .andExpect(status().isOk())
           .andExpect(content().string("Hello World!"));
    }

    @Test
    void helloAPIWithName() throws Exception {
        mvc.perform(get("/helloAPI").param("name", "John"))
           .andExpect(status().isOk())
           .andExpect(content().string("Hello John!"));
    }

    @Test
    void emailAPINoParameters() throws Exception {
        mvc.perform(get("/emailAPI"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("John Doe"))
           .andExpect(jsonPath("$.suggestedEmail").value("John.Doe@OntarioTechU.net"));
    }

    @Test
    void emailAPIWithFirstName() throws Exception {
        mvc.perform(get("/emailAPI").param("fname", "Jack"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("Jack Doe"))
           .andExpect(jsonPath("$.suggestedEmail").value("Jack.Doe@OntarioTechU.net"));
    }

    @Test
    void emailAPIWithLastName() throws Exception {
        mvc.perform(get("/emailAPI").param("lname", "Sparrow"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("John Sparrow"))
           .andExpect(jsonPath("$.suggestedEmail").value("John.Sparrow@OntarioTechU.net"));
    }

    @Test
    void emailAPIWithFullName() throws Exception {
        mvc.perform(get("/emailAPI").param("fname", "Jack").param("lname", "Sparrow"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("Jack Sparrow"))
           .andExpect(jsonPath("$.suggestedEmail").value("Jack.Sparrow@OntarioTechU.net"));
    }
}