package ru.progwards.tasktracker.dto.converter.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.ProjectDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.service.GetService;

/**
 * Конвертер Project <-> ProjectDtoPreview
 * @author Pavel Khovaylo
 */
@Component
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectDtoPreviewConverter implements Converter<Project, ProjectDtoPreview> {
    /**
     * сервис для получения бизнес модели Project
     */
    GetService<Long, Project> projectGetService;

    @Override
    public Project toModel(ProjectDtoPreview dto) {
        if (dto == null) return null;

        Project model = projectGetService.get(dto.getId());

        if (model != null)
            model.setName(dto.getName());

        return model;
    }

    @Override
    public ProjectDtoPreview toDto(Project model) {
        return new ProjectDtoPreview(model.getId(), model.getName());
    }
}