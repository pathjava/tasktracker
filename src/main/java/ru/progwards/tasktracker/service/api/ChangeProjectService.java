package ru.progwards.tasktracker.service.api;

import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.types.Rules;

public interface ChangeProjectService {

    void changeRules(Project project, User user, Rules[] rules);

}
