package service.api;

import repository.entity.Project;
import service.vo.ProjectModel;
import service.vo.UserModel;
import util.types.Rules;

public interface ChangeProjectService {
    void createProject(ProjectModel project);
    void refreshProject(ProjectModel project);
    void changeRules(ProjectModel project, UserModel user, Rules[] rules);
}
