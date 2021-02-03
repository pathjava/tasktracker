package ru.progwards.tasktracker.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Тестирование методов контроллера TaskController
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("dev")
class TaskControllerTest {

    @Test
    void create_Task() {
    }

    @Test
    void create_Task_Validation_If_Id_is_NotNull() {
    }

    @Test
    void create_Task_Validation_If_Code_is_NotNull() {
    }

    @Test
    void create_Task_Validation_If_Name_is_Blank() {
    }

    @Test
    void create_Task_Validation_If_TaskType_is_Null() {
    }

    @Test
    void create_Task_Validation_If_Project_is_Null() {
    }

    @Test
    void create_Task_Validation_If_Author_is_Null() {
    }

    @Test
    void create_Task_Validation_If_Created_is_NotNull() {
    }

    @Test
    void create_Task_Validation_If_Updated_is_NotNull() {
    }

    @Test
    void get_Task() {
    }

    @Test
    void get_Task_when_NotFound() {
    }

    @Test
    void get_Task_Validation_when_Id_is_negative() {
    }

    @Test
    void getByCode_Task() {
    }

    @Test
    void getByCode_Task_when_NotFound() {
    }

    @Test
    void getByCode_Task_Validation_when_Id_is_negative() {
    }

    @Test
    void getListByProject_Task() {
    }

    @Test
    void getListByProject_Task_Validation_when_Id_is_negative() {
    }

    @Test
    void getListByProject_Task_when_return_Empty_List() {
    }

    @Test
    void getList_Task() {
    }

    @Test
    void getList_Task_when_return_Empty_List() {
    }

    @Test
    void update_Task() {
    }

    @Test
    void update_Task_when_Request_Id_is_different_Dto_Id() {
    }

    @Test
    void update_Task_when_Code_is_already_used_another_TaskType() {
    }

    @Test
    void update_Task_when_NotFound() {
    }

    @Test
    void delete_Task() {
    }

    @Test
    void delete_Task_Validation_when_Id_is_negative() {
    }

    @Test
    void updateOneField_Task() {
    }

    @Test
    void updateOneField_Task_when_Request_Id_is_different_Dto_Id() {
    }
}