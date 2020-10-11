package ru.progwards.tasktracker.repository.dao.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class JsonHandlerTaskEntity implements JsonHandler {

    public final Map<Long, TaskEntity> tasks = new ConcurrentHashMap<>();
    private static File TASKS_PATH = null;

    /* for testing */
    private void initializerMap() {
        Map<Long, TaskEntity> tempTasks = new HashMap<>();
        tempTasks.put(
                1L, new TaskEntity(1L, "task1_test", "description1", TaskType.BUG, Priority.MAJOR,
                        001L, 003L,
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                        123456L, 123456L, 123456L)
        );
        tempTasks.put(
                2L, new TaskEntity(2L, "task2_test", "description2", TaskType.EPIC, Priority.BLOCKER,
                        003L, 004L,
                        ZonedDateTime.now().plusDays(1).toEpochSecond(), ZonedDateTime.now().plusDays(2).toEpochSecond(),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.REVIEW, "new_version",
                        123456L, 123456L, 123456L)
        );
        tempTasks.put(
                3L, new TaskEntity(3L, "task3_test", "description3", TaskType.TASK, Priority.CRITICAL,
                        005L, 006L,
                        ZonedDateTime.now().plusDays(2).toEpochSecond(), ZonedDateTime.now().plusDays(3).toEpochSecond(),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.IN_PROGRESS, "new_version",
                        123456L, 123456L, 123456L)
        );
        tempTasks.put(
                4L, new TaskEntity(4L, "task4_test", "description4", TaskType.BUG, Priority.MINOR,
                        007L, 002L,
                        ZonedDateTime.now().plusDays(3).toEpochSecond(), ZonedDateTime.now().plusDays(4).toEpochSecond(),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                        123456L, 123456L, 123456L)
        );
        tempTasks.put(
                5L, new TaskEntity(5L, "task5_test", "description5", TaskType.EPIC, Priority.MAJOR,
                        001L, 003L,
                        ZonedDateTime.now().plusDays(4).toEpochSecond(), ZonedDateTime.now().plusDays(5).toEpochSecond(),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.REVIEW, "new_version",
                        123456L, 123456L, 123456L)
        );
        tempTasks.values().forEach(value -> tasks.put(value.getId(), value));
        write();
    }

    static {
        try {
            TASKS_PATH = new File(Objects.requireNonNull(
                    Thread.currentThread().getContextClassLoader()
                            .getResource("data/tasks.json")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public JsonHandlerTaskEntity() {
        initializerMap(); /* for testing */
        try {
            read();
        } catch (Exception e) {
            try {
                write();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * записываем объекты в Json файл из Map
     */
    @Override
    public void write() {
        synchronized (this) {
            try (Writer writer = new FileWriter(TASKS_PATH)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(tasks.values().stream().collect(Collectors.toUnmodifiableList()), writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * читаем объекты из Json файла в Map
     */
    @Override
    public void read() {
        synchronized (this) {
            try {
                tasks.clear();
                String json = Files.readString(TASKS_PATH.toPath());
                Type type = new TypeToken<List<TaskEntity>>() {
                }.getType();
                ArrayList<TaskEntity> list = new Gson().fromJson(json, type);
                list.forEach(e -> tasks.put(e.getId(), e));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
