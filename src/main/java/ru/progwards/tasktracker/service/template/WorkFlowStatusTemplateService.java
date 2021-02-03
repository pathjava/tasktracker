package ru.progwards.tasktracker.service.template;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.model.WorkFlowStatus;
import ru.progwards.tasktracker.model.types.WorkFlowState;
import ru.progwards.tasktracker.repository.WorkFlowStatusRepository;
import ru.progwards.tasktracker.service.*;

import java.util.List;

/**
 * Бизнес-логика работы со статусами бизнес процесса
 *
 * @author Gregory Lobkov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class WorkFlowStatusTemplateService implements TemplateService<WorkFlowStatus> {

    CreateService<WorkFlowStatus> workFlowStatusCreateService;

    /**
     * Создание бизнес сущностей по шаблону
     *
     * @param args [0] - Workflow
     * @return
     */
    @Override
    public List<WorkFlowStatus> createFromTemplate(Object... args) {
        if (args.length != 1)
            throw new OperationIsNotPossibleException("WorkFlowStatus.createFromTemplate: 1 argument expected");
        if (!(args[0] instanceof WorkFlow))
            throw new OperationIsNotPossibleException("WorkFlowStatus.createFromTemplate: argument 0 must be WorkFlow");
        WorkFlow workflow = (WorkFlow) args[0];

        WorkFlowStatus s1 = new WorkFlowStatus();
        s1.setState(WorkFlowState.TO_DO);
        s1.setName("Сделать");
        s1.setAlwaysAllow(true);
        s1.setWorkflow(workflow);

        WorkFlowStatus s2 = new WorkFlowStatus();
        s1.setState(WorkFlowState.IN_PROGRESS);
        s1.setName("В работе");
        s1.setAlwaysAllow(true);
        s1.setWorkflow(workflow);

        WorkFlowStatus s3 = new WorkFlowStatus();
        s1.setState(WorkFlowState.DONE);
        s1.setName("Выполнено");
        s1.setAlwaysAllow(true);
        s1.setWorkflow(workflow);

        workFlowStatusCreateService.create(s1);
        workFlowStatusCreateService.create(s2);
        workFlowStatusCreateService.create(s3);
        return List.of(s1, s2, s3);
    }
}
