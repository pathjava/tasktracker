//package ru.progwards.tasktracker.repository.deprecated.jsonhandler;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//import org.springframework.stereotype.Repository;
//import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
//import ru.progwards.tasktracker.repository.deprecated.entity.TaskTypeEntity;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.Writer;
//import java.lang.reflect.Type;
//import java.net.URISyntaxException;
//import java.nio.file.Files;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;
//
///**
// * Методы работы сущности с JSON базой данных
// *
// * @author Oleg Kiselev
// */
//@Repository
//@Deprecated
//public class TaskTypeEntityJsonHandler implements JsonHandler<Long, TaskTypeEntity> {
//
//    public final Map<Long, TaskTypeEntity> types = new ConcurrentHashMap<>();
//    private static File TYPES_PATH;
//
//    static {
//        try {
//            TYPES_PATH = new File(Objects.requireNonNull(
//                    Thread.currentThread().getContextClassLoader()
//                            .getResource("data/task_type.json")).toURI());
//        } catch (NullPointerException | URISyntaxException e) {
//            //e.printStackTrace();
//            TYPES_PATH = new File("src/main/resources/data/task_type.json");
//        }
//    }
//
//    public TaskTypeEntityJsonHandler() {
//        try {
//            read();
//        } catch (Exception e) {
//            try {
//                write();
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public Map<Long, TaskTypeEntity> getMap() {
//        return types;
//    }
//
//    /**
//     * записываем объекты в Json файл из Map
//     */
//    @Override
//    public void write() {
//        synchronized (this) {
//            try (Writer writer = new FileWriter(TYPES_PATH)) {
//                Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                gson.toJson(types.values().stream().collect(Collectors.toUnmodifiableList()), writer);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * читаем объекты из Json файла в Map
//     */
//    @Override
//    public void read() {
//        synchronized (this) {
//            try {
//                types.clear();
//                String json = Files.readString(TYPES_PATH.toPath());
//                Type type = new TypeToken<List<TaskTypeEntity>>() {
//                }.getType();
//                ArrayList<TaskTypeEntity> list = new Gson().fromJson(json, type);
//                list.forEach(e -> types.put(e.getId(), e));
//            } catch (IOException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//    }
//}
