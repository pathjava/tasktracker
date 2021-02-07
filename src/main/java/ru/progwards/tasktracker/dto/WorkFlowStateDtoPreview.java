package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Объект, содержащий состояние WF
 *
 * @author Gregory Lobkov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkFlowStateDtoPreview {

    @NotNull // это идентификатор (ENUM)
    private String name;

}
