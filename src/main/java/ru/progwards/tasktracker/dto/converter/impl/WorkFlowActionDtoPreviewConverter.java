package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.WorkFlowActionDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.WorkFlowAction;
import ru.progwards.tasktracker.service.GetService;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class WorkFlowActionDtoPreviewConverter implements Converter<WorkFlowAction, WorkFlowActionDtoPreview> {
    private final GetService<Long, WorkFlowAction> workFlowActionGetService;

    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public WorkFlowAction toModel(WorkFlowActionDtoPreview dto) {
        if (dto == null)
            return null;
        else
            return workFlowActionGetService.get(dto.getId());

    }

    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public WorkFlowActionDtoPreview toDto(WorkFlowAction model) {
        if (model == null)
            return null;
        else
            return new WorkFlowActionDtoPreview(
                    model.getId(),
                    model.getName()
            );
    }

}
