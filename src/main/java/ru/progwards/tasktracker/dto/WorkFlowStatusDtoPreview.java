package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.progwards.tasktracker.model.types.WorkFlowState;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkFlowStatusDtoPreview {

    @NotNull
    Long id;

    String name;

    WorkFlowStateDtoPreview state;

}
