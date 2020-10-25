package ru.progwards.tasktracker.repository.dao.impl.jsonhandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;

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

@Component
public class RelatedTaskEntityJsonHandler implements JsonHandler {

    public final Map<Long, RelatedTaskEntity> relatedTasks = new ConcurrentHashMap<>();
    private static File RELATED_TASKS_PATH;

    /* for testing */
    private void initializerMap() {
        Map<Long, RelatedTaskEntity> tempRelatedTasks = new HashMap<>();

        tempRelatedTasks.put(
                1L, new RelatedTaskEntity(
                        1L, new RelationTypeEntity(1L, "блокирующая", new RelationTypeEntity(
                        2L, "блокируемая", null)),
                        1L, null)
        );
        tempRelatedTasks.put(
                2L, new RelatedTaskEntity(
                        2L, new RelationTypeEntity(2L, "блокируемая", new RelationTypeEntity(
                                1L, "блокирующая", null)),
                        2L, null)
        );
        tempRelatedTasks.put(
                3L, new RelatedTaskEntity(
                        3L, new RelationTypeEntity(3L, "связываемая", new RelationTypeEntity(
                                3L, "ссылается", null)),
                        1L, null)
        );

        tempRelatedTasks.values().forEach(value -> relatedTasks.put(value.getId(), value));
        write();
    }

    static {
        try {
            RELATED_TASKS_PATH = new File(Objects.requireNonNull(
                    Thread.currentThread().getContextClassLoader()
                            .getResource("data/related_task.json")).toURI());
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public RelatedTaskEntityJsonHandler() {
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
            }
        }
    }
}
