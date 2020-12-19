package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.TaskAttachmentContent;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.TaskAttachment;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Тестирование бизнес-сущности и репозитория
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class TaskAttachmentServiceTest {

    @Autowired
    CreateService<TaskAttachment> createService;
    @Autowired
    RemoveService<TaskAttachment> removeService;
    @Autowired
    GetService<Long, TaskAttachment> getService;
    @Autowired
    GetListByTaskService<Long, TaskAttachment> getListByTaskService;
    @Autowired
    RemoveService<TaskAttachmentContent> removeAttachmentContentService;
    @Autowired
    CreateService<Task> createTaskService;
    @Autowired
    RemoveService<Task> removeTaskService;

    // инициализация тестового экземпляра бизнес-объекта
    Task task;
    byte[] dataBytes = {65, 67, 66};
    TaskAttachmentContent content;
    TaskAttachment attachment;

    {
        try {
            task = new Task(-12343L, "TESTTASK-01", "Test task 1", "",
                    null, null, null, null, null,
                    null, null, null,
                    null, null, null,
                    null, null, null, null, null, null);
            //InputStream targetStream = new ByteArrayInputStream(dataBytes);
            Blob targetStream = new SerialBlob(dataBytes);
            content = new TaskAttachmentContent(-12341L, targetStream, null);
            attachment = new TaskAttachment(-23124L, task.getId(), "test1.txt", "txt", 3L, ZonedDateTime.now(), content);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //@BeforeAll
    public void prepareTests() {
        removeTestEntity();
        try {
            removeTaskService.remove(task);
        } catch (NoSuchElementException e) {
        }
        createTaskService.create(task);
    }

    //@AfterAll
    public void finishTests() {
        removeTestEntity();
        try {
            removeTaskService.remove(task);
        } catch (NoSuchElementException e) {
        }
    }

    /**
     * Безошибочное удаление бизнес-объекта
     */
    public void removeTestEntity() {
        try {
            removeService.remove(attachment);
        } catch (NoSuchElementException e) {
        }
        try {
            removeAttachmentContentService.remove(content);
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
            getService.get(attachment.getId());
        });
    }


    /**
     * Создаем, читаем тот же объект, сравниваем
     */
    @Test
    public void create() {
        createService.create(attachment);
        TaskAttachment got = getService.get(attachment.getId());
        Assertions.assertNotNull(got, "Сохранено в репо, но прочесть не смогли");
        Assertions.assertEquals(attachment.getId(), got.getId(), "Идентификатор созраненного объекта не совпал");
    }


    /**
     * Проверка на удаляемость
     */
    @Test
    public void remove() {
        createService.create(attachment);
        removeService.remove(attachment);
        boolean cantFind = false;
        try {
            getService.get(attachment.getId());
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
            removeService.remove(attachment);
        });
    }


    /**
     * Получение списка вложений по задаче
     */
    @Test()
    public void getListByTaskId() {
        removeTestEntity();
        createService.create(attachment);
        Collection<TaskAttachment> list = getListByTaskService.getListByTaskId(task.getId());
        Assertions.assertTrue(list.size() > 0, "Вложений для задачи " + task.getId() + " не найдено");
        TaskAttachment gotAttachment = (TaskAttachment) list.toArray()[0];
        Assertions.assertEquals(gotAttachment.getId(), attachment.getId(), "Идентификатор связки различается");
        Assertions.assertNotNull(gotAttachment.getContent(), "Идентификатор вложения не задан");
        Assertions.assertEquals(gotAttachment.getContent().getId(), content.getId(), "Идентификатор вложения в связке сохранен не верно");
        TaskAttachmentContent gotContent = gotAttachment.getContent();
        Assertions.assertNotNull(gotContent, "Вложение не определено");
        Assertions.assertEquals(gotContent.getId(), content.getId(), "Идентификатор вложения различается");
    }

}