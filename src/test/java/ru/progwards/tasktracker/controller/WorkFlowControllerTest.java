package ru.progwards.tasktracker.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

@SpringBootTest
public class WorkFlowControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void giveControllers() {
        ServletContext servletContext = wac.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        //Assertions.assertNotNull(wac.getBean("WorkFlowController"));
        //Assertions.assertNotNull(wac.getBean("restTaskController"));
    }

    @Test
    public void getTask() throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/rest/workflow/list")
        )
                .andDo(MockMvcResultHandlers.print());
                //.andExpect(MockMvcResultMatchers.view().name(""));
    }

}