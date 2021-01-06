package ru.progwards.tasktracker.dto.converter.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.ProjectDtoPreview;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.Project;

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
        if (dto == null)
            return null;

        if (dto.getId() == null)
            return null;

        Project model = projectGetService.get(dto.getId());

        if (model != null)
            return new Project(dto.getId(), dto.getName(), model.getDescription(), model.getPrefix(),
                    model.getOwner(), model.getCreated(), model.getTasks(), model.getTaskTypes(),
                    model.getLastTaskCode(), model.isDeleted());

        return null;
    }

    @Override
    public ProjectDtoPreview toDto(Project model) {
        return new ProjectDtoPreview(model.getId(), model.getName());
    }
}