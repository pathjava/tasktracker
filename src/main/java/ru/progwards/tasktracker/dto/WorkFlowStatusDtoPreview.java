package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.model.types.WorkFlowState;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class WorkFlowStatusDtoPreview {

    @NotNull
    Long id;

    String name;

    WorkFlowState state;

}
