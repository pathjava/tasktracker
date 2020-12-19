package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.TaskAttachmentContent;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Тестирование бизнес-сущности и репозитория
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class TaskAttachmentContentServiceTest {

    @Autowired
    CreateService<TaskAttachmentContent> createService;
    @Autowired
    RemoveService<TaskAttachmentContent> removeService;
    @Autowired
    GetService<Long, TaskAttachmentContent> getService;

    // инициализация тестового экземпляра бизнес-объекта
    byte[] dataBytes = {1, 2, 3};
    TaskAttachmentContent content;

    {
        try {
            //InputStream targetStream = new ByteArrayInputStream(dataBytes);
            Blob targetStream = new SerialBlob(dataBytes);
            content = new TaskAttachmentContent(-23123L, targetStream, null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Безошибочное удаление бизнес-объекта
     */
    public void removeTestEntity() {
        try {
            removeService.remove(content);
        } catch (NoSuchElementException e) {
        }
    }


    /**
     * Ошибка при получении несуществующего объекта
     */
    @Test
    public void getNotExistsException() {
        removeTestEntity();
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            getService.get(content.getId());
        });
    }


    /**
     * Создаем, читаем тот же объект, сравниваем
     */
    @Test
    public void create() {
        removeTestEntity();
        createService.create(content);
        TaskAttachmentContent got = getService.get(content.getId());
        Assertions.assertNotNull(got, "Сохранено в репо, но прочесть не смогли");
        Assertions.assertEquals(content.getId(), got.getId(), "Идентификатор сохраненного объекта не совпал");

        try {
            //String expectedData = Arrays.toString(content.getData().getBinaryStream().readAllBytes());
            String expectedData = Arrays.toString(dataBytes);
            String gotData = Arrays.toString(got.getData().getBinaryStream().readAllBytes());
            Assertions.assertEquals(expectedData, gotData, "Данные файлов не совпадают");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        removeTestEntity();
    }


    /**
     * Проверка на удаляемость
     */
    @Test
    public void remove() {
        removeTestEntity();
        createService.create(content);
        removeService.remove(content);
        boolean cantFind = false;
        try {
            getService.get(content.getId());
        } catch (NoSuchElementException ex) {
            cantFind = true;
        }
        Assertions.assertTrue(cantFind, "Удаление элемента из репозитория не удалось");
    }


    /**
     * Проверка на удаление несуществующего
     */
    @Test
    public void removeNotExistsException() {
        removeTestEntity();
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            removeService.remove(content);
        });
    }

}