package ru.progwards.tasktracker.service.impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RemoveService;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Тестирование бизнес-сущности и репозитория
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskAttachmentServiceTest {

    @Autowired
    CreateService<TaskAttachment> createService;
    @Autowired
    RemoveService<TaskAttachment> removeService;
    @Autowired
    GetService<Long, TaskAttachment> getService;
    @Autowired
    RemoveService<TaskAttachmentContent> removeAttachmentContentService;
    @Autowired
    CreateService<Task> createTaskService;
    @Autowired
    RemoveService<Task> removeTaskService;
    @Autowired
    CreateService<Project> createProjectService;
    @Autowired
    RemoveService<Project> removeProjectService;
    @Autowired
    CreateService<User> createUserService;
    @Autowired
    RemoveService<User> removeUserService;

    // инициализация тестового экземпляра бизнес-объекта
    Project project;
    Task task;
    User user;
    byte[] data = {65, 67, 66};
    TaskAttachmentContent content;
    TaskAttachment attachment;

    {
        user = new User();
        user.setName("TESTUSER-01");
        user.setEmail("test@test.ru");
        user.setPassword("");
        project = new Project();
        project.setName("TESTPROJ-01");
        project.setPrefix("TP123");
        project.setCreated(ZonedDateTime.now());
        project.setLastTaskCode(0L);
        task = new Task();
        task.setName("TESTTASK-01");
        task.setName("Test task 1");
        task.setProject(project);
        task.setAuthor(user);
        content = new TaskAttachmentContent(null, data, Collections.emptyList());
        attachment = new TaskAttachment(null, task, "test1", "txt", (long)(data.length), null, content);
    }

    @Test
    @Order(0)
    public void beforeAll() {
        createUserService.create(user);
        createProjectService.create(project);
        createTaskService.create(task);
    }

    @Test()
    @Order(99)
    public void afterAll() {
        removeTestEntity();
        removeTaskService.remove(task);
        removeProjectService.remove(project);
        removeUserService.remove(user);
    }

    /**
     * Безошибочное удаление бизнес-объекта
     */
    public void removeTestEntity() {
        try {
            removeService.remove(attachment);
        } catch (NoSuchElementException e) {
        }
        attachment.setId(null);
        try {
            removeAttachmentContentService.remove(content);
        } catch (NoSuchElementException e) {
        }
        content.setId(null);
    }


    /**
     * Ошибка при получении несуществующего объекта
     */
    @Test
    @Order(2)
    public void getNotExistsException() {
        removeTestEntity();
        Assertions.assertThrows(NotFoundException.class, () -> {
            getService.get(-1234L);
        });
    }


    /**
     * Создаем, читаем тот же объект, сравниваем
     */
    @Test
    @Order(3)
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
    @Order(4)
    public void remove() {
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
    @Order(5)
    public void silentNotExistsRemove() {
        removeTestEntity();
        attachment.setId(-23451L);
        removeService.remove(attachment);
    }


    /**
     * Получение списка вложений по задаче
     */
    @Test()
    @Order(6)
    public void getListByTaskId() {
        removeTestEntity();
        createService.create(attachment);
        List<TaskAttachment> list = task.getAttachments();
        Assertions.assertTrue(list.size() > 0, "Вложений для задачи " + task.getId() + " не найдено");
        TaskAttachment gotAttachment = (TaskAttachment) list.toArray()[0];
        Assertions.assertEquals(gotAttachment.getId(), attachment.getId(), "Идентификатор связки различается");
        Assertions.assertNotNull(gotAttachment.getContent(), "Вложение не определено");
        Assertions.assertNotNull(gotAttachment.getContent().getId(), "Идентификатор вложения не задан");
        Assertions.assertEquals(gotAttachment.getContent().getId(), content.getId(), "Идентификатор вложения в связке сохранен не верно");
        TaskAttachmentContent gotContent = gotAttachment.getContent();
        Assertions.assertNotNull(gotContent, "Вложение не определено");
        Assertions.assertEquals(gotContent.getId(), content.getId(), "Идентификатор вложения различается");
    }

}