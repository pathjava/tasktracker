package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.progwards.tasktracker.dto.WorkFlowStateDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.types.WorkFlowState;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для работы с состоянием (WorkFlowState)
 *
 * @author Gregory Lobkov
 */
@RestController
@RequestMapping(value = "/rest/workflowstate")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
@Validated
public class WorkFlowStateController {

    private final Converter<WorkFlowState, WorkFlowStateDtoPreview> dtoPreviewConverter;

    /**
     * Метод получения всех состояний (WorkFlowState)
     *
     * @return полный список WorkFlowStateDtoPreview
     */
    @GetMapping(value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkFlowStateDtoPreview>> getList(
    ) {
//        List<WorkFlowStateDtoPreview> result = Arrays.stream(WorkFlowState.values()) //тоже рабочий вариант, но без стримов будет быстрее
//                .map((s)->dtoPreviewConverter.toDto(s))
//                .collect(Collectors.toList());
        WorkFlowState[] values = WorkFlowState.values();
        List<WorkFlowStateDtoPreview> result = new ArrayList<>(values.length);
        for(WorkFlowState s: values) {
            WorkFlowStateDtoPreview dto = dtoPreviewConverter.toDto(s);
            result.add(dto);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}