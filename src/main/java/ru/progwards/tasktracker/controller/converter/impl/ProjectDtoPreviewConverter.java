package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.ProjectDtoPreview;
import ru.progwards.tasktracker.service.vo.Project;

/**
 * @author Pavel Khovaylo
 */
@Component
public class ProjectDtoPreviewConverter implements Converter<Project, ProjectDtoPreview> {
    @Override
    public Project toModel(ProjectDtoPreview dto) {
        return null;
    }

    @Override
    public ProjectDtoPreview toDto(Project model) {
        return null;
    }
}
