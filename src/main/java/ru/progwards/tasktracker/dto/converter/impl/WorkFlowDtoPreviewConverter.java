package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.WorkFlowDtoPreview;
import ru.progwards.tasktracker.dto.WorkFlowStatusDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.model.WorkFlowStatus;
import ru.progwards.tasktracker.service.GetService;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class WorkFlowDtoPreviewConverter implements Converter<WorkFlow, WorkFlowDtoPreview> {

    private final GetService<Long, WorkFlow> getService;
    private final GetService<Long, WorkFlowStatus> statusGetService;
    private final Converter<WorkFlowStatus, WorkFlowStatusDtoPreview> statusDtoConverter;


    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public WorkFlow toModel(WorkFlowDtoPreview dto) {
        WorkFlow model = null;
        if (dto != null && dto.getId() != null)
            model = getService.get(dto.getId());
        return model;
    }

    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public WorkFlowDtoPreview toDto(WorkFlow model) {
        WorkFlowDtoPreview dto = null;
        if (model != null) {
            dto = new WorkFlowDtoPreview(
                    model.getId(),
                    model.getName()
            );
        }
        return dto;
    }

}
