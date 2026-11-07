
package com.tejas.studentapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.tejas.studentapi.controllers.StdController;

@WebMvcTest(StdController.class)
public class StdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHello() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                    "Hello Welcome Tejas Saraf.. to Learn Jenkins..."));
    }

    @Test
    void testAddition() throws Exception {
        mockMvc.perform(get("/api/add/10/20"))
                .andExpect(status().isOk())
                .andExpect(content().string("30"));
    }

    @Test
    void testDifference() throws Exception {
        mockMvc.perform(get("/api/diff")
                .param("a", "10")
                .param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void testMultiplication() throws Exception {
        mockMvc.perform(get("/api/mul")
                .param("a", "10")
                .param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("50"));
    }
}
