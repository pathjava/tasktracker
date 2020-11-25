package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.ProjectDtoFull;
import ru.progwards.tasktracker.service.vo.Project;

/**
 * Конвертер dtoFull <-> model
 * @author Pavel Khovaylo
 */
@Component
public class ProjectDtoFullConverter implements Converter<Project, ProjectDtoFull> {

    //TODO как будем получать поля бизнес-модели, если они отсутствуют в Dto  ???

    /**
     * метод конвертирует объект ProjectDtoFull в model Project
     * @param dto объект ProjectDto, который конвертируется в модель
     * @return бизнес-модель проекта
     */
    @Override
    public Project toModel(ProjectDtoFull dto) {
//        return new Project(dto.getId(), dto.getName(), dto.getDescription(),
//                dto.getPrefix(), dto.getOwner(), dto.getCreated(), dto.getLastTaskCode());
        return null;
    }

    /**
     * метод конвертирует model Project в объект ProjectDtoFull
     * @param model бизнес-модель проекта, которая конвертируется в ProjectDto
     * @return объект ProjectDto
     */
    @Override
    public ProjectDtoFull toDto(Project model) {
//        return new ProjectDto(model.getId(), model.getName(), model.getDescription(), model.getPrefix(),
//                model.getOwner(), model.getCreated(), model.getTasks(), model.getLastTaskCode());

        return null;
    }
}