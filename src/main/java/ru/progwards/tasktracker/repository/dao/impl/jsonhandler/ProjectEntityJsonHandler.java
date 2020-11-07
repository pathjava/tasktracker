package ru.progwards.tasktracker.repository.dao.impl.jsonhandler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ProjectEntityJsonHandler implements JsonHandler<Long, ProjectEntity> {
    private static File PROJECT_PATH;
    private final Map<Long, ProjectEntity> map = new ConcurrentHashMap<>();

    static {
        try {
            PROJECT_PATH = new File(Objects.requireNonNull(
                    Thread.currentThread().getContextClassLoader()
                            .getResource("data/projects.json")).toURI());
        } catch (NullPointerException | URISyntaxException e) {
            //e.printStackTrace();
            PROJECT_PATH = new File("src/main/resources/data/projects.json");
        }
    }
    public ProjectEntityJsonHandler() {
        try {
            read();
        } catch (Exception ex1) {
            try {
                write();
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
    }

    public Map<Long, ProjectEntity> getMap() {
        return map;
    }

    public void addMap(Long id, ProjectEntity entity) {
        map.put(id, entity);
    }

    @Override
    public void write() {
        synchronized (this) {
            List<ProjectEntity> list = map.values().stream().collect(Collectors.toUnmodifiableList());
            String json = new Gson().toJson(list);
            try {
                Files.writeString(PROJECT_PATH.toPath(), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void read() {
        synchronized (this) {
            try {
                map.clear();
                String json = Files.readString(PROJECT_PATH.toPath());
                Type type = new TypeToken<ArrayList<ProjectEntity>>(){}.getType();
                List<ProjectEntity> list = new Gson().fromJson(json, type);
                list.forEach(e -> map.put(e.getId(), e));
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex.getMessage());
            }
        }
    }
}