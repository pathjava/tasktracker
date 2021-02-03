package ru.progwards.tasktracker.service.template;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.model.WorkFlowStatus;
import ru.progwards.tasktracker.service.*;

import java.util.List;

/**
 * Бизнес-логика с шаблоном воркфлоу
 *
 * @author Gregory Lobkov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class WorkFlowTemplateService implements TemplateService<WorkFlow> {

    TemplateService<WorkFlowStatus> workFlowStatusTemplateService;
    CreateService<WorkFlow> workFlowCreateService;
    RefreshService<WorkFlow> workFlowRefreshService;

    /**
     * Метод создания WorkFlow по шаблону
     *
     * @param args
     */
    @Override
    public List<WorkFlow> createFromTemplate(Object... args) {
        if (args.length != 0)
            throw new OperationIsNotPossibleException("WorkFlow.createFromTemplate: no arguments expected");

        WorkFlow workflow = new WorkFlow();
        workflow.setPattern(true);
        workflow.setName("Простой");
        workFlowCreateService.create(workflow);

        List<WorkFlowStatus> statuses = workFlowStatusTemplateService.createFromTemplate(workflow);
        workflow.setStartStatus(statuses.get(0));
        workFlowRefreshService.refresh(workflow);

        return List.of(workflow);
    }

}