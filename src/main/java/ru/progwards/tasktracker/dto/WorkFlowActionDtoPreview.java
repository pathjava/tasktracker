package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Объект, содержащий краткие данные о действии workflow, выводимые в пользовательском интерфейсе
 *
 * @author Aleksandr Sidelnikov
 */
@Data
@AllArgsConstructor
public class WorkFlowActionDtoPreview {

    @Min(0)
//    @Max(Long.MAX_VALUE)
    @NotNull
    private Long id;

    private String name;

}
