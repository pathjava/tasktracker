package ru.progwards.tasktracker.controller;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.ProjectDtoFull;
import ru.progwards.tasktracker.dto.ProjectDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер Project
 * @author Pavel Khovaylo
 */
@Validated
@RestController
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/rest/project/",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {
    /**
     * конвертер Project <-> ProjectDtoFull
     */
    Converter<Project, ProjectDtoFull> converterFull;
    /**
     * конвертер Project <-> ProjectDtoPreview
     */
    Converter<Project, ProjectDtoPreview> converterPreview;
    /**
     * сервисный класс для получение списка проекта
     */
    GetService<Long, Project> projectGetService;
    /**
     * сервисный класс для получение проекта
     */
    GetListService<Project> projectGetListService;
    /**
     * сервисный класс для создания проекта
     */
    CreateService<Project> projectCreateService;
    /**
     * сервисный класс для обновления проекта
     */
    RefreshService<Project> projectRefreshService;
    /**
     * сервисный класс для удаления проекта
     */
    RemoveService<Project> projectRemoveService;

    /**
     * по запросу получаем список проектов
     * @return список проектов
     */
    @GetMapping("list")
    public ResponseEntity<Collection<ProjectDtoPreview>> get() {

        Collection<ProjectDtoPreview> projectDtos =
                projectGetListService.getList().stream().
                        map(converterPreview::toDto).collect(Collectors.toList());

        return new ResponseEntity<>(projectDtos, HttpStatus.OK);
    }

    /**
     * по запросу получаем нужный проект; если такового нет, то бросаем исключение NotFoundException
     * id должен находится в диапазоне от 0 до 9_223_372_036_854_775_807 и не равен null
     * @param id идентификатор проекта
     * @return ProjectDto
     */
    @GetMapping("{id}")
    public ResponseEntity<ProjectDtoFull> get(@NotNull @Min(0) @Max(Long.MAX_VALUE) @PathVariable("id") Long id) {
        Project project = projectGetService.get(id);

        if (project == null)
            throw new NotFoundException("Not found the project with id=" + id);

        return new ResponseEntity<>(converterFull.toDto(project), HttpStatus.OK);
    }

    /**
     * по запросу создаём проект
     * формируем объект ProjectDtoFull, исходя из ограничений, относящихся к group = Create.class
     * @param projectDto передаем наполненный проект
     * @return созданный проект
     */
    @Transactional
    @PostMapping("create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProjectDtoFull> create(@Validated(Create.class) @NotNull @RequestBody ProjectDtoFull projectDto) {
        Project project = converterFull.toModel(projectDto);
        projectCreateService.create(project);
        ProjectDtoFull createdProject = converterFull.toDto(project);

        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    /**
     * по запросу обновляем существующий проект
     * id должен находится в диапазоне от 0 до 9_223_372_036_854_775_807 и не равен null
     * формируем объект ProjectDtoFull, исходя из ограничений, относящихся к group = Update.class
     * @param id идентификатор изменяемого проекта
     * @param projectDto измененный проект
     */
    @PostMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@NotNull @Min(0) @Max(Long.MAX_VALUE) @PathVariable ("id") Long id,
                       @Validated(Update.class) @NotNull @RequestBody ProjectDtoFull projectDto) {

        if (!id.equals(projectDto.getId()))
            throw new OperationIsNotPossibleException("Impossible to create the project");

        projectDto.setId(id);
        projectRefreshService.refresh(converterFull.toModel(projectDto));
    }

    /**
     * по запросу удаляем нужный проект; если такого проекта не существует, то бросаем исключение NotFoundException
     * id должен находится в диапазоне от 0 до 9_223_372_036_854_775_807 и не равен null
     * @param id идентификатор удаляемого проекта
     */
    @PostMapping("{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@NotNull @Min(0) @Max(Long.MAX_VALUE) @PathVariable("id") Long id) {
        Project project = projectGetService.get(id);
        if (project == null)
            throw new NotFoundException("Not found the project with id=" + id);

        projectRemoveService.remove(project);
    }
}