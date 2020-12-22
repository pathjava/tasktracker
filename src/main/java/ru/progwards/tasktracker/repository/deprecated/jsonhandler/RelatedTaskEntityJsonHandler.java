package ru.progwards.tasktracker.repository.deprecated.jsonhandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;
import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
import ru.progwards.tasktracker.repository.deprecated.entity.RelatedTaskEntity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Методы работы сущности с JSON базой данных
 *
 * @author Oleg Kiselev
 */
@Repository
public class RelatedTaskEntityJsonHandler implements JsonHandler<Long, RelatedTaskEntity> {

    public final Map<Long, RelatedTaskEntity> relatedTasks = new ConcurrentHashMap<>();
    private static File RELATED_TASKS_PATH;

    static {
        try {
            RELATED_TASKS_PATH = new File(Objects.requireNonNull(
                    Thread.currentThread().getContextClassLoader()
                            .getResource("data/related_task.json")).toURI());
        } catch (NullPointerException | URISyntaxException e) {
            //e.printStackTrace();
            RELATED_TASKS_PATH = new File("src/main/resources/data/related_task.json");
        }
    }

    public RelatedTaskEntityJsonHandler() {
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
    public Map<Long, RelatedTaskEntity> getMap() {
        return relatedTasks;
    }

    /**
     * записываем объекты в Json файл из Map
     */
    @Override
    public void write() {
        synchronized (this) {
            try (Writer writer = new FileWriter(RELATED_TASKS_PATH)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(relatedTasks.values().stream().collect(Collectors.toUnmodifiableList()), writer);
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
                relatedTasks.clear();
                String json = Files.readString(RELATED_TASKS_PATH.toPath());
                Type type = new TypeToken<List<RelatedTaskEntity>>() {
                }.getType();
                ArrayList<RelatedTaskEntity> list = new Gson().fromJson(json, type);
                list.forEach(e -> relatedTasks.put(e.getId(), e));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
