package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.ProjectDtoPreview;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Project;

/**
 * Конвертер Project <-> ProjectDtoPreview
 * @author Pavel Khovaylo
 */
@Component
public class ProjectDtoPreviewConverter implements Converter<Project, ProjectDtoPreview> {
    /**
     * сервис для получения бизнес модели Project
     */
    @Autowired
    private GetService<Long, Project> projectGetService;

    @Override
    public Project toModel(ProjectDtoPreview dto) {
        if (dto == null)
            return null;

        Project model = projectGetService.get(dto.getId());

        //проверка на наличие этого проекта в базе данных
        if (model != null)
            return new Project(dto.getId(), dto.getName(), model.getDescription(), model.getPrefix(),
                    model.getOwner(), model.getCreated(), model.getTaskTypes(),
                    projectGetService.get(dto.getId()).getLastTaskCode());

        return null;
    }

    @Override
    public ProjectDtoPreview toDto(Project model) {
        return new ProjectDtoPreview(model.getId(), model.getName());
    }
}