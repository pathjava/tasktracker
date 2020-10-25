package ru.progwards.tasktracker.service.api.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.AttachmentContent;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Тестирование бизнес-сущности и репозитория
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class AttachmentContentServiceTest {

    @Autowired
    CreateService<AttachmentContent> createService;
    @Autowired
    RemoveService<AttachmentContent> removeService;
    @Autowired
    GetService<Long, AttachmentContent> getService;

    // инициализация тестового экземпляра бизнес-объекта
    byte[] dataBytes = {1, 2, 3};
    AttachmentContent content;

    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(dataBytes.length);
        baos.write(dataBytes, 0, dataBytes.length);
        content = new AttachmentContent(-23123L, baos);
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
        AttachmentContent got = getService.get(content.getId());
        Assertions.assertNotNull(got, "Сохранено в репо, но прочесть не смогли");
        Assertions.assertEquals(content.getId(), got.getId(), "Идентификатор сохраненного объекта не совпал");

        String expectedData = Arrays.toString(content.getData().toByteArray());
        String gotData = Arrays.toString(content.getData().toByteArray());
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