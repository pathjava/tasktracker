package ru.progwards.tasktracker.repository.dao.impl.jsonhandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
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
public class RelationTypeEntityJsonHandler implements JsonHandler<Long, RelationTypeEntity> {

    public final Map<Long, RelationTypeEntity> relationType = new ConcurrentHashMap<>();
    private static File RELATION_TYPE_PATH;

    /* for testing */
    private void initializerMap() {
        Map<Long, RelationTypeEntity> tempRelationType = new HashMap<>();

        tempRelationType.put(
                1L, new RelationTypeEntity(1L, "блокирующая", new RelationTypeEntity(2L, "блокируемая", null))
        );
        tempRelationType.put(
                2L, new RelationTypeEntity(2L, "блокируемая", new RelationTypeEntity(1L, "блокирующая", null))
        );
        tempRelationType.put(
                3L, new RelationTypeEntity(3L, "ссылается", new RelationTypeEntity(3L, "ссылается", null))
        );

        tempRelationType.values().forEach(value -> relationType.put(value.getId(), value));
        write();
    }

    static {
        try {
            RELATION_TYPE_PATH = new File(Objects.requireNonNull(
                    Thread.currentThread().getContextClassLoader()
                            .getResource("data/relation_type.json")).toURI());
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public RelationTypeEntityJsonHandler() {
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

    @Override
    public Map<Long, RelationTypeEntity> getMap() {
        return relationType;
    }

    /**
     * записываем объекты в Json файл из Map
     */
    @Override
    public void write() {
        synchronized (this) {
            try (Writer writer = new FileWriter(RELATION_TYPE_PATH)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(relationType.values().stream().collect(Collectors.toUnmodifiableList()), writer);
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
                relationType.clear();
                String json = Files.readString(RELATION_TYPE_PATH.toPath());
                Type type = new TypeToken<List<RelationTypeEntity>>() {
                }.getType();
                ArrayList<RelationTypeEntity> list = new Gson().fromJson(json, type);
                list.forEach(e -> relationType.put(e.getId(), e));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
