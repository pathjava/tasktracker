package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.repository.dao.impl.UserEntityRepository;
import ru.progwards.tasktracker.repository.entity.UserEntity;
import ru.progwards.tasktracker.service.vo.UserRole;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private UserController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserEntityRepository repository;

    @Test
    public void loadedController() {
        Assertions.assertNotNull(controller);
    }

    @Test
    public void getUsersTest() throws Exception {
        Collection<UserEntity> entities = repository.get();
        String json = new ObjectMapper().writeValueAsString(entities);

        mockMvc.perform(get("/rest/user/list")).
                andExpect(status().isOk()).
                andExpect(content().json(json));
    }

    @Test
    public void getUser() throws Exception {
        UserEntity entity = new UserEntity(7L, "USER-7", "sidnet@mail.ru", "777", new ArrayList<UserRole>(),false);

        repository.create(entity);

        String json = new ObjectMapper().writeValueAsString(entity);

        mockMvc.perform(get("/rest/user/7")).
                andExpect(status().isOk()).
                andExpect(content().json(json));
    }

    @Test
    public void createTest() throws Exception {
        UserEntity entity = new UserEntity(5L, "USER-5", "sidnet@mail.ru", "555", new ArrayList<UserRole>(), false);

        String json = new ObjectMapper().writeValueAsString(entity);

        mockMvc.perform(post("/rest/user/create").
                content("{\n" +
                        "    \"id\": 5,\n" +
                        "    \"name\": \"USER-5\",\n" +
                        "    \"email\": \"sidnet@mail.ru\",\n" +
                        "    \"password\": \"555\",\n" +
                        "    \"roles\": []\n" +
                        "}").
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().is2xxSuccessful()).andExpect(content().json(json));

        mockMvc.perform(get("/rest/user/5")).
                andExpect(jsonPath("$.name").value("USER-5")).
                andExpect(jsonPath("$.password").value("555"));
    }

    @Test
    public void updateTest() throws Exception {
        UserEntity entity = new UserEntity(6L, "USER-6", "sidnet@mail.ru", "666", new ArrayList<UserRole>(), false);
        repository.create(entity);

        String json = new ObjectMapper().writeValueAsString(entity);

        mockMvc.perform(put("/rest/user/6/update").
                content("{\n" +
                        "    \"id\": 6,\n" +
                        "    \"name\": \"USER-61\",\n" +
                        "    \"email\": \"sidnet@mail.ru\",\n" +
                        "    \"password\": \"999\",\n" +
                        "    \"roles\": []\n" +
                        "}").
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andDo(print()).andExpect(status().isOk());

        mockMvc.perform(get("/rest/user/6")).
                andExpect(jsonPath("$.name").value("USER-61")).
                andExpect(jsonPath("$.password").value("999"));
    }

    @Test
    public void deleteTest() throws Exception {
        Collection<UserEntity> entities = repository.get();
        String json = new ObjectMapper().writeValueAsString(entities);

        UserEntity entity = new UserEntity(8L, "USER-8", "sidnet@mail.ru", "888", new ArrayList<UserRole>(), false);
        repository.create(entity);

        mockMvc.perform(delete("/rest/user/8/delete")).andExpect(status().isOk());

//        mockMvc.perform(get("/rest/user/list")).andExpect(content().json(json));
    }

//    @Test
//    public void updateOneFieldTest() throws Exception {
//        UserEntity entity = new UserEntity(8L, "USER-8", "sidnet@mail.ru", "888", new ArrayList<UserRole>(), false);
//        repository.create(entity);
//
//        mockMvc.perform(post("/rest/user/8/update1field").
//                content("{\n" +
//                "    \"newValue\": \"name user 8\",\n" +
//                "    \"fieldName\": \"name\"\n" +
//                "}").
//                contentType(MediaType.APPLICATION_JSON).
//                accept(MediaType.APPLICATION_JSON)).
//                andExpect(status().isOk());
//
//        mockMvc.perform(get("/rest/user/8")).
//                andExpect(jsonPath("$.name").value("name user 8"));
//    }

//    @Test
//    public void NotFoundUserExceptionTest() throws Exception {
//        mockMvc.perform(get("/rest/user/100")).andExpect(content().
//                string("Not found a user with id=100"));
//
//        mockMvc.perform(delete("/rest/user/100/delete")).andExpect(content().
//                string("Not found a user with id=100"));
//    }
}
