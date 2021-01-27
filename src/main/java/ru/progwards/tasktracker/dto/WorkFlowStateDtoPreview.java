package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Объект, содержащий состояние WF
 *
 * @author Gregory Lobkov
 */
@Data
@AllArgsConstructor
public class WorkFlowStateDtoPreview {

    @NotNull // это идентификатор (ENUM)
    private String name;

}
