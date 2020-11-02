package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.ProjectDto;
import ru.progwards.tasktracker.service.vo.Project;

/**
 * Конвертер из dto-объекта в бизнес-модель и обратно
 * @author Pavel Khovaylo
 */
@Component
public class ProjectDtoConverter implements Converter<Project, ProjectDto> {

    @Override
    public Project toModel(ProjectDto dto) {
        return new Project(dto.getId(), dto.getName(), dto.getDescription(),
                dto.getPrefix(), dto.getOwner(), dto.getCreated(), dto.getWorkFlow(),
                dto.getTasks(), dto.getLastTaskCode());
    }

    @Override
    public ProjectDto toDto(Project model) {
        return new ProjectDto(model.getId(), model.getName(), model.getDescription(),
                model.getPrefix(), model.getOwner());
    }
}