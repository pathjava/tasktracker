package ru.progwards.tasktracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.exception.NotExistException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import java.util.Collection;

@RestController
@RequestMapping("/rest/project/")
public class ProjectController {

    private final ProjectEntityRepository repository;
    private final ProjectEntityRepositoryUpdateField projectEntityRepositoryUpdateField;

    public ProjectController(ProjectEntityRepository repository, ProjectEntityRepositoryUpdateField projectEntityRepositoryUpdateField) {
        this.repository = repository;
        this.projectEntityRepositoryUpdateField = projectEntityRepositoryUpdateField;
    }

    /**
     * по запросу получаем список проектов
     * @return возвращается список проектов
     */
    @GetMapping("list")
    public ResponseEntity<Collection<ProjectEntity>> get() {
        return new ResponseEntity<>(repository.get(), HttpStatus.OK);
    }

    /**
     * по запросу получаем нужный проект; если такового нет, то бросаем исключение NotFoundProjectException
     * @param id идентификатор проекта
     * @return Project
     */
    @GetMapping("{id}")
    public ResponseEntity<ProjectEntity> get(@PathVariable("id") Long id) {
        ProjectEntity entity = repository.get(id);
        if (entity == null)
            throw new NotFoundException("Not found a project with id=" + id);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    /**
     * по запросу создаём проект
     * @param entity передаем наполненный проект
     * @return возвращаем созданный проект
     */
    @PostMapping("create")
    public ResponseEntity<ProjectEntity> create(@RequestBody ProjectEntity entity) {
        if (entity == null)
            throw new NotExistException("Project is null");

        repository.create(entity);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    /**
     * по запросу обновляем существующий проект
     * @param id идентификатор изменяемого проекта
     * @param entity измененный проект
     */
    @PostMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody ProjectEntity entity) {
        if (entity == null)
            throw new NotExistException("Project is null");

        entity.setId(id);
        repository.update(entity);
    }

    /**
     * по запросу удаляем нужный проект; если такого проекта не существует, то бросаем исключение NotFoundProjectException
     * @param id идентификатор удаляемого проекта
     */
    @PostMapping("{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        ProjectEntity entity = repository.get(id);
        if (entity == null)
            throw new NotFoundException("Not found a project with id=" + id);

        repository.delete(id);
    }

    /**
     * по запросу обновляем значение поля проекта
     * @param id идентификатор проекта, в котором нужно обновить поле
     * @param updateOneValue объект, содержащий информацию о поле, которое необходимо изменить и нововое значение
     *                       данного поля
     */
    @PostMapping("{id}/update1field")
    @ResponseStatus(HttpStatus.OK)
    public void updateOneField(@PathVariable("id") Long id, @RequestBody UpdateOneValue updateOneValue) {
        if (updateOneValue == null)
            throw new NotExistException("UpdateOneValue is null");

        updateOneValue.setId(id);
        projectEntityRepositoryUpdateField.updateField(updateOneValue);
    }
}