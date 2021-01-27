package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class WorkFlowDtoPreview {

    @NotNull
    Long id;

    String name;

}
