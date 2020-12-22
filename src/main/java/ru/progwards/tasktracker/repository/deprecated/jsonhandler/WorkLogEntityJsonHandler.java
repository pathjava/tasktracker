package ru.progwards.tasktracker.repository.deprecated.jsonhandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;
import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
import ru.progwards.tasktracker.repository.deprecated.entity.WorkLogEntity;

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
public class WorkLogEntityJsonHandler implements JsonHandler<Long, WorkLogEntity> {

    public final Map<Long, WorkLogEntity> logs = new ConcurrentHashMap<>();
    private static File LOGS_PATH;

    static {
        try {
            LOGS_PATH = new File(Objects.requireNonNull(
                    Thread.currentThread().getContextClassLoader()
                            .getResource("data/work_logs.json")).toURI());
        } catch (NullPointerException | URISyntaxException e) {
            //e.printStackTrace();
            LOGS_PATH = new File("src/main/resources/data/work_logs.json");
        }
    }

    public WorkLogEntityJsonHandler() {
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
    public Map<Long, WorkLogEntity> getMap() {
        return logs;
    }

    /**
     * записываем объекты в Json файл из Map
     */
    @Override
    public void write() {
        synchronized (this) {
            try (Writer writer = new FileWriter(LOGS_PATH)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(logs.values().stream().collect(Collectors.toUnmodifiableList()), writer);
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
                logs.clear();
                String json = Files.readString(LOGS_PATH.toPath());
                Type type = new TypeToken<List<WorkLogEntity>>() {
                }.getType();
                ArrayList<WorkLogEntity> list = new Gson().fromJson(json, type);
                list.forEach(e -> logs.put(e.getId(), e));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
