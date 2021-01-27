package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.WorkFlowDtoFull;
import ru.progwards.tasktracker.dto.WorkFlowStatusDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.model.WorkFlowStatus;
import ru.progwards.tasktracker.service.GetService;

import java.util.Collections;


/**
 * Преобразование valueObject <-> dto
 *
 * WorkFlow <-> WorkFlowDtoFull
 *
 * @author Gregory Lobkov
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class WorkFlowDtoFullConverter implements Converter<WorkFlow, WorkFlowDtoFull> {

    private final GetService<Long, WorkFlow> getService;
    private final Converter<WorkFlowStatus, WorkFlowStatusDtoPreview> statusDtoConverter;

    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public WorkFlow toModel(WorkFlowDtoFull dto) {
        WorkFlow model = null;
        if (dto != null) {
            WorkFlowStatus status = statusDtoConverter.toModel(dto.getStartStatus());
            if (dto.getId() == null) {
                model = new WorkFlow(
                        null,
                        dto.getName(),
                        dto.getPattern(),
                        status,
                        Collections.emptyList(),
                        Collections.emptyList()
                );
            } else {
                model = getService.get(dto.getId());
                model.setName(dto.getName());
                model.setPattern(dto.getPattern());
                model.setStartStatus(status);
            }
        }
        return model;
    }

    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public WorkFlowDtoFull toDto(WorkFlow model) {
        WorkFlowDtoFull dto = null;
        if (model != null) {
            dto = new WorkFlowDtoFull(
                    model.getId(),
                    model.getName(),
                    model.getPattern(),
                    statusDtoConverter.toDto(model.getStartStatus())
            );
        }
        return dto;
    }


}