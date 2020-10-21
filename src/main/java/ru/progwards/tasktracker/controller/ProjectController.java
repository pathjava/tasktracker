package ru.progwards.tasktracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.exceptions.NotFoundProjectException;
import ru.progwards.tasktracker.controller.exceptions.NullObjectException;
import ru.progwards.tasktracker.service.facade.impl.*;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import java.util.Collection;

@RestController
public class ProjectController {

    private final ProjectGetListService projectGetListService;

    private final ProjectGetService projectGetService;

    private final ProjectCreateService projectCreateService;

    private final ProjectRefreshService projectRefreshService;

    private final ProjectOneFieldSetService projectOneFieldSetService;

    private final ProjectRemoveService projectRemoveService;

    public ProjectController(ProjectGetListService projectGetListService, ProjectGetService projectGetService, ProjectCreateService projectCreateService, ProjectRefreshService projectRefreshService, ProjectOneFieldSetService projectOneFieldSetService, ProjectRemoveService projectRemoveService) {
        this.projectGetListService = projectGetListService;
        this.projectGetService = projectGetService;
        this.projectCreateService = projectCreateService;
        this.projectRefreshService = projectRefreshService;
        this.projectOneFieldSetService = projectOneFieldSetService;
        this.projectRemoveService = projectRemoveService;
    }

    /**
     * по запросу получаем список проектов
     * @return Collection<Project>
     */
    @GetMapping("/rest/project/list")
    public ResponseEntity<Collection<Project>> get() {
        return new ResponseEntity<>(projectGetListService.getList(), HttpStatus.OK);
    }

    /**
     * по запросу получаем нужый проект; если такового нет, то бросаем исключение NotFoundProjectException
     * @param id идентификатор проекта
     * @return Project
     */
    @GetMapping("/rest/project/{id}")
    public ResponseEntity<Project> get(@PathVariable("id") Long id) {
        Project project = projectGetService.get(id);
        if (project == null)
            throw new NotFoundProjectException("Not found a project with id=" + id);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    /**
     * по запросу создаём проект
     * @param project передаем наполненный объект типа Project
     */
    @PostMapping("/rest/project/create")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody Project project) {
        if (project == null)
            throw new NullObjectException("Project is null");

        projectCreateService.create(project);
    }

    @PostMapping("/rest/project/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id) {
//        if (id == null)
//            throw new IdNullException("Id is null");
//
//        Project project = projectGetService.get(id);
//        if (project == null)
//            throw new NotFoundProjectException("Not found a project with id=" + id);
    }

    /**
     * по запросу удаляем нужный проект; если такого проекта не существует, то бросаем исключение NotFoundProjectException
     * @param id идентификатор удаляемого проекта
     */
    @PostMapping("/rest/project/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        Project project = projectGetService.get(id);
        if (project == null)
            throw new NotFoundProjectException("Not found a project with id=" + id);

        projectRemoveService.remove(project);
    }

    /**
     * по запросу обновляем значение поля проекта
     * @param id идентификатор проекта, в котором нужно обновить поле
     * @param updateOneValue объект, содержащий информацию о поле, которое необходимо изменить и нововое значение
     *                       данного поля
     */
    @PostMapping("/rest/project/{id}/update1field")
    @ResponseStatus(HttpStatus.OK)
    public void updateOneField(@PathVariable("id") Long id, @RequestBody UpdateOneValue updateOneValue) {
        if (updateOneValue == null)
            throw new NullObjectException("UpdateOneValue is null");

        updateOneValue.setId(id);
        projectOneFieldSetService.setOneField(updateOneValue);
    }

//    @PostMapping("/rest/project/{id}/update1field?name={fieldName}")
//    @ResponseStatus(HttpStatus.OK)
//    public void updateOneField(@PathVariable("id") Long id, @PathVariable("fieldName") String fieldName, @RequestBody UpdateOneValue updateOneValue) {
//        updateOneValue.setId(id);
//        updateOneValue.setFieldName(fieldName);
//        projectOneFieldSetService.setOneField(updateOneValue);
//    }
}
