package ru.progwards.tasktracker.repository.deprecated.jsonhandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskEntity;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskNoteEntity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Методы работы сущности с JSON базой данных
 *
 * @author Konstantin Kishkin
 */
@Component
public class TaskNotesEntityJsonHandler implements JsonHandler<Long, TaskNoteEntity> {

    public final Map<Long, TaskNoteEntity> tasks = new ConcurrentHashMap<>();
    private static File TASKS_PATH;

    static {
        try {
            TASKS_PATH = new File(Objects.requireNonNull(
                    Thread.currentThread().getContextClassLoader()
                            .getResource("data/tasks.json")).toURI());
        } catch (NullPointerException | URISyntaxException e) {
            TASKS_PATH = new File("src/main/resources/data/tasks.json");
        }
    }

    public TaskNotesEntityJsonHandler() {
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

    @Override
    public Map<Long, TaskNoteEntity> getMap() {
        return tasks;
    }

    /**
     * записываем объекты в Json файл из Map
     */
    @Override
    public void write() {
        synchronized (this) {
            try (Writer writer = new FileWriter(TASKS_PATH)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(tasks.values().stream().collect(Collectors.toList()), writer);
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
                String json = Files.readAllBytes(TASKS_PATH.toPath()).toString();
                Type type = new TypeToken<List<TaskEntity>>() {
                }.getType();
                ArrayList<TaskNoteEntity> list = new Gson().fromJson(json, type);
                list.forEach(e -> tasks.put(e.getId(), e));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
