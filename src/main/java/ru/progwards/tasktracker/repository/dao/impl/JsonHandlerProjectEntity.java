package ru.progwards.tasktracker.repository.dao.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class JsonHandlerProjectEntity implements JsonHandler {
    private final static File PROJECT_PATH = new File(JsonHandlerProjectEntity.class.getClassLoader().
            getResource("data/projects.json").getFile());
    public final Map<Long, ProjectEntity> map = new ConcurrentHashMap<>();

    public JsonHandlerProjectEntity() {
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
            }
        }
    }
}