package ru.progwards.tasktracker.dto.converter.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.ProjectDtoFull;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.User;

import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Конвертер Project <-> ProjectDtoFull
 * @author Pavel Khovaylo
 */
@Component
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectDtoFullConverter implements Converter<Project, ProjectDtoFull> {
    /**
     * конвертер User <-> UserDtoPreview
     */
    Converter<User, UserDtoPreview> userDtoPreviewConverter;
    /**
     * сервис для получения бизнес модели
     */
    GetService<Long, Project> projectGetService;

    /**
     * метод конвертирует объект ProjectDtoFull в model Project
     * @param dto объект ProjectDto, который конвертируется в модель
     * @return бизнес-модель проекта
     */
    @Override
    public Project toModel(ProjectDtoFull dto) {
        if (dto == null)
            return null;

        Project model = projectGetService.get(dto.getId());

        //проверка на наличие этого проекта в базе данных
        if (model == null)
            return new Project(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrefix(),
                    userDtoPreviewConverter.toModel(dto.getOwner()), ZonedDateTime.now(),
                    new ArrayList<>(), new ArrayList<>(), null, false);

        return new Project(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrefix(),
                userDtoPreviewConverter.toModel(dto.getOwner()), dto.getCreated(), model.getTasks(),
                model.getTaskTypes(), model.getLastTaskCode(), model.isDeleted());
    }

    /**
     * метод конвертирует model Project в объект ProjectDtoFull
     * @param model бизнес-модель проекта, которая конвертируется в ProjectDto
     * @return объект ProjectDto
     */
    @Override
    public ProjectDtoFull toDto(Project model) {
        if (model == null)
            return null;

        return new ProjectDtoFull(model.getId(), model.getName(), model.getDescription(), model.getPrefix(),
                userDtoPreviewConverter.toDto(model.getOwner()), model.getCreated());
    }
}