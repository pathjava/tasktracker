package ru.progwards.tasktracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.progwards.tasktracker.dto.WorkFlowStateDtoPreview;
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
public class WorkFlowStateController {

    /**
     * Метод получения всех состояний (WorkFlowState)
     *
     * @return полный список WorkFlowStateDtoPreview
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkFlowStateDtoPreview>> getList() {
//        List<WorkFlowStateDtoPreview> list = Arrays.stream(WorkFlowState.values()) //тоже рабочий вариант, но без стримов будет быстрее
//                .map((s)->new WorkFlowStateDtoPreview(s.toString()))
//                .collect(Collectors.toList());
        List<WorkFlowStateDtoPreview> list = new ArrayList<>();
        for(WorkFlowState s: WorkFlowState.values())
            list.add(new WorkFlowStateDtoPreview(s.toString()));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
