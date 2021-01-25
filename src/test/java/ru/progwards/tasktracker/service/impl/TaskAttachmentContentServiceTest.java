package ru.progwards.tasktracker.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.TaskAttachmentContent;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RemoveService;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * Тестирование бизнес-сущности и репозитория
 */
@SpringBootTest
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
        content = new TaskAttachmentContent(null, dataBytes, Collections.emptyList());
    }

    /**
     * Безошибочное удаление бизнес-объекта
     */
    public void removeTestEntity() {
        try {
            removeService.remove(content);
            content.setId(null);
        } catch (NoSuchElementException e) {
        }
    }


    /**
     * Ошибка при получении несуществующего объекта
     */
    @Test
    public void getNotExistsException() {
        removeTestEntity();
        content.setId(-12342L);
        Assertions.assertThrows(NotFoundException.class, () -> {
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
        System.out.println(content.getId());
        TaskAttachmentContent got = getService.get(content.getId());
        Assertions.assertNotNull(got, "Сохранено в репо, но прочесть не смогли");
        Assertions.assertEquals(content.getId(), got.getId(), "Идентификатор сохраненного объекта не совпал");

        String expectedData = Arrays.toString(content.getData());
        String gotData = Arrays.toString(content.getData());
        Assertions.assertEquals(expectedData, gotData, "Данные файлов не совпадают");
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
        } catch (NotFoundException ex) {
            cantFind = true;
        }
        Assertions.assertTrue(cantFind, "Удаление элемента из репозитория не удалось");
    }


    /**
     * Проверка на удаление несуществующего
     */
    @Test
    public void silentNotExistsRemove() {
        removeTestEntity();
        content.setId(-1L);
        removeService.remove(content);
    }

}