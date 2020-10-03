package ru.progwards.service.api;

import ru.progwards.service.vo.Project;
import ru.progwards.service.vo.User;
import ru.progwards.util.types.Rules;

public interface ChangeProjectService {
    void createProject(Project project);
    void refreshProject(Project project);
    void changeRules(Project project, User user, Rules[] rules);
}
