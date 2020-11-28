package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.ProjectDtoFull;
import ru.progwards.tasktracker.controller.dto.ProjectDtoPreview;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
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
    /**
     * конвертер Project <-> ProjectDtoFull
     */
    @Autowired
    private Converter<Project, ProjectDtoFull> converterFull;
    /**
     * конвертер Project <-> ProjectDtoPreview
     */
    @Autowired
    private Converter<Project, ProjectDtoPreview> converterPreview;
    /**
     * сервисный класс для получение списка проекта
     */
    @Autowired
    private GetService<Long, Project> projectGetService;
    /**
     * сервисный класс для получение проекта
     */
    @Autowired
    private GetListService<Project> projectGetListService;
    /**
     * сервисный класс для создания проекта
     */
    @Autowired
    private CreateService<Project> projectCreateService;
    /**
     * сервисный класс для обновления проекта
     */
    @Autowired
    private RefreshService<Project> projectRefreshService;
    /**
     * сервисный класс для удаления проекта
     */
    @Autowired
    private RemoveService<Project> projectRemoveService;
    /**
     * сервисный класс для обновления одного поля проекта
     */
    @Autowired
    private RepositoryUpdateField<ProjectEntity> projectEntityRepositoryUpdateField;

    /**
     * по запросу получаем список проектов
     * @return список проектов
     */
    @GetMapping("list")
    public ResponseEntity<Collection<ProjectDtoPreview>> get() {

        Collection<ProjectDtoPreview> projectDtos =
                projectGetListService.getList().stream().
                        map(e -> converterPreview.toDto(e)).collect(Collectors.toList());

        return new ResponseEntity<>(projectDtos, HttpStatus.OK);
    }

    /**
     * по запросу получаем нужный проект; если такового нет, то бросаем исключение NotFoundException
     * @param id идентификатор проекта
     * @return ProjectDto
     */
    @GetMapping("{id}")
    public ResponseEntity<ProjectDtoFull> get(@PathVariable("id") Long id) {
        Project project = projectGetService.get(id);
        if (project == null)
            throw new NotFoundException("Not found a project with id=" + id);

        return new ResponseEntity<>(converterFull.toDto(project), HttpStatus.OK);
    }

    /**
     * по запросу создаём проект
     * @param projectDto передаем наполненный проект
     * @return созданный проект
     */
    @PostMapping("create")
    public ResponseEntity<ProjectDtoFull> create(@RequestBody ProjectDtoFull projectDto) {
        if (projectDto == null)
            throw new BadRequestException("Project is null");

        projectCreateService.create(converterFull.toModel(projectDto));

        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    /**
     * по запросу обновляем существующий проект
     * @param id идентификатор изменяемого проекта
     * @param projectDto измененный проект
     */
    @PostMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody ProjectDtoFull projectDto) {
        if (id == null)
            throw new BadRequestException("Id is null");

        if (projectDto == null)
            throw new BadRequestException("Project is null");

        projectDto.setId(id);
        projectRefreshService.refresh(converterFull.toModel(projectDto));
    }

    /**
     * по запросу удаляем нужный проект; если такого проекта не существует, то бросаем исключение NotFoundException
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