package ru.progwards.tasktracker.service.vo;

import java.time.LocalDateTime;
import java.util.List;

public class Project {
    List<Task> taskModels;
    private String id;
    private LocalDateTime time;
}
