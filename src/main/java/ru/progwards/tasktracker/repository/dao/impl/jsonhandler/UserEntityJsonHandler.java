package ru.progwards.tasktracker.repository.dao.impl.jsonhandler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.UserEntity;

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
public class UserEntityJsonHandler implements JsonHandler<Long, UserEntity> {
    private final static File PROJECT_PATH = new File(UserEntityJsonHandler.class.getClassLoader().
            getResource("data/users.json").getFile());
    private final Map<Long, UserEntity> map = new ConcurrentHashMap<>();

    public UserEntityJsonHandler() {
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

    public Map<Long, UserEntity> getMap() {
        return map;
    }

    public void addMap(Long id, UserEntity entity) {
        map.put(id, entity);
    }

    public void clearRepository() {
        try {
            Files.writeString(PROJECT_PATH.toPath(), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write() {
        synchronized (this) {
            List<UserEntity> list = map.values().stream().collect(Collectors.toUnmodifiableList());
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
                Type type = new TypeToken<ArrayList<UserEntity>>(){}.getType();
                List<UserEntity> list = new Gson().fromJson(json, type);
                list.forEach(e -> map.put(e.getId(), e));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}