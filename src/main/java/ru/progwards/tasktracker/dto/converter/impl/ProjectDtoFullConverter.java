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
        if (dto.getId() == null)
            return new Project(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrefix(),
                    userDtoPreviewConverter.toModel(dto.getOwner()), ZonedDateTime.now(),
                    new ArrayList<>(), new ArrayList<>(), 0L, false);

        Project model = projectGetService.get(dto.getId());

        if (model != null) {
            model.setName(dto.getName());
            model.setDescription(dto.getDescription());
            model.setPrefix(dto.getPrefix());
            model.setOwner(userDtoPreviewConverter.toModel(dto.getOwner()));
            //думаю, менять время создания проекта НЕ нужно
//            model.setCreated(dto.getCreated());
        }

        return model;
    }

    /**
     * метод конвертирует model Project в объект ProjectDtoFull
     * @param model бизнес-модель проекта, которая конвертируется в ProjectDto
     * @return объект ProjectDto
     */
    @Override
    public ProjectDtoFull toDto(Project model) {
        return new ProjectDtoFull(model.getId(), model.getName(), model.getDescription(), model.getPrefix(),
                userDtoPreviewConverter.toDto(model.getOwner()), model.getCreated());
    }
}