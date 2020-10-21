package ru.progwards.tasktracker.repository.dao.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskPriority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkFlowStatus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.time.Duration;
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
                1L, new TaskEntity(1L, "TT1-1", "Test task 1", "Description task 1",
                        TaskType.BUG, TaskPriority.MAJOR, 2L, new User(1L), new User(1L),
                        ZonedDateTime.now().toEpochSecond(), null,
                        new WorkFlowStatus(1L),
                        null, null, null,
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        );
        tempTasks.put(
                2L, new TaskEntity(2L, "TT2-2", "Test task 2", "Description task 2",
                        TaskType.BUG, TaskPriority.MAJOR, 1L, new User(1L), new User(1L),
                        ZonedDateTime.now().plusDays(1).toEpochSecond(), null,
                        new WorkFlowStatus(1L),
                        Duration.ofDays(3).toSeconds(), null, null,
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        );
        tempTasks.put(
                3L, new TaskEntity(3L, "TT3-3", "Test task 3", "Description task 3",
                        TaskType.BUG, TaskPriority.MAJOR, 2L, new User(1L), new User(1L),
                        ZonedDateTime.now().plusDays(2).toEpochSecond(), ZonedDateTime.now().plusDays(3).toEpochSecond(),
                        new WorkFlowStatus(1L),
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        );
        tempTasks.put(
                4L, new TaskEntity(4L, "TT4-4", "Test task 4", "Description task 4",
                        TaskType.BUG, TaskPriority.MAJOR, 1L, new User(1L), new User(1L),
                        ZonedDateTime.now().plusDays(3).toEpochSecond(), null,
                        new WorkFlowStatus(1L),
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        );
        tempTasks.put(
                5L, new TaskEntity(5L, "TT5-5", "Test task 5", "Description task 5",
                        TaskType.BUG, TaskPriority.MAJOR, 1L, new User(1L), new User(1L),
                        ZonedDateTime.now().plusDays(4).toEpochSecond(), ZonedDateTime.now().plusDays(5).toEpochSecond(),
                        new WorkFlowStatus(1L),
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
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
