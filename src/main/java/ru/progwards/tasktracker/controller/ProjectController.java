package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.ProjectDto;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллеры Project
 * @author Pavel Khovaylo
 */
@RestController
@RequestMapping("/rest/project/")
public class ProjectController {

    @Autowired
    private Converter<Project, ProjectDto> converter;

    @Autowired
    private GetService<Long, Project> projectGetService;

    @Autowired
    private GetListService<Project> projectGetListService;

    @Autowired
    private CreateService<Project> projectCreateService;

    @Autowired
    private RefreshService<Project> projectRefreshService;

    @Autowired
    private RemoveService<Project> projectRemoveService;

    @Autowired
    private RepositoryUpdateField<ProjectEntity> projectEntityRepositoryUpdateField;

    /**
     * по запросу получаем список проектов
     * @return список проектов
     */
    @GetMapping("list")
    public ResponseEntity<Collection<ProjectDto>> get() {

        Collection<ProjectDto> projectDtos =
                projectGetListService.getList().stream().
                        map(e -> converter.toDto(e)).collect(Collectors.toList());

        return new ResponseEntity<>(projectDtos, HttpStatus.OK);
    }

    /**
     * по запросу получаем нужный проект; если такового нет, то бросаем исключение NotFoundProjectException
     * @param id идентификатор проекта
     * @return ProjectDto
     */
    @GetMapping("{id}")
    public ResponseEntity<ProjectDto> get(@PathVariable("id") Long id) {
        Project project = projectGetService.get(id);
        if (project == null)
            throw new NotFoundException("Not found a project with id=" + id);

        return new ResponseEntity<>(converter.toDto(project), HttpStatus.OK);
    }

    /**
     * по запросу создаём проект
     * @param projectDto передаем наполненный проект
     * @return созданный проект
     */
    @PostMapping("create")
    public ResponseEntity<ProjectDto> create(@RequestBody ProjectDto projectDto) {
        if (projectDto == null)
            throw new BadRequestException("Project is null");

        projectCreateService.create(converter.toModel(projectDto));

        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    /**
     * по запросу обновляем существующий проект
     * @param id идентификатор изменяемого проекта
     * @param projectDto измененный проект
     */
    @PostMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody ProjectDto projectDto) {
        if (id == null)
            throw new BadRequestException("Id is null");

        if (projectDto == null)
            throw new BadRequestException("Project is null");

        projectDto.setId(id);

        projectRefreshService.refresh(converter.toModel(projectDto));
    }

    /**
     * по запросу удаляем нужный проект; если такого проекта не существует, то бросаем исключение NotFoundProjectException
     * @param id идентификатор удаляемого проекта
     */
    @PostMapping("{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        Project project = projectGetService.get(id);
        if (project == null)
            throw new NotFoundException("Not found a project with id=" + id);

        projectRemoveService.remove(project);
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
            throw new BadRequestException("UpdateOneValue is null");

        updateOneValue.setId(id);
        projectEntityRepositoryUpdateField.updateField(updateOneValue);
    }
}