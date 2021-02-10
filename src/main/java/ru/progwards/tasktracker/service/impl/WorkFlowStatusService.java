package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.WorkFlowStatus;
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
public class WorkFlowStatusService implements CreateService<WorkFlowStatus>, RemoveService<WorkFlowStatus>, GetService<Long, WorkFlowStatus>, GetListService<WorkFlowStatus>, RefreshService<WorkFlowStatus>, CopyService<WorkFlowStatus> {

    private final WorkFlowStatusRepository statusRepository;

    /**
     * Создание нового WorkFlowStatus
     *
     * @param workFlowStatus новый WorkFlowStatus
     */
    @Transactional
    @Override
    public void create(WorkFlowStatus workFlowStatus) {
        statusRepository.save(workFlowStatus);
    }


    /**
     * Удаление WorkFlowStatus
     *
     * @param workFlowStatus удаляемый WorkFlowStatus
     */
    @Transactional
    @Override
    public void remove(WorkFlowStatus workFlowStatus) {
        statusRepository.delete(workFlowStatus);
    }


    /**
     * Получить информацию по WorkFlowStatus
     *
     * @param id идентификатор WorkFlowStatus
     * @return WorkFlowStatus
     */
    @Override
    public WorkFlowStatus get(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("WorkFlowStatus id=" + id + " not found"));
    }


    /**
     * Обновить поля WorkFlowStatus
     *
     * @param workFlowStatus измененный WorkFlowStatus
     */
    @Transactional
    @Override
    public void refresh(WorkFlowStatus workFlowStatus) {
        statusRepository.save(workFlowStatus);
    }


    /**
     * Получить весь список статусов
     *
     * @return список статусов
     */
    @Override
    public List<WorkFlowStatus> getList() {
        return statusRepository.findAll();
    }

    /**
     * Создать копию бизнес-объекта
     * Если задан шаблон, то свойства в скопированном объекте заменятся на не null свойства объекта
     *
     * @param modelFrom бизнес объект, который необходимо скопировать
     * @param template  шаблон для замены свойств во вновь созданном объекте
     * @return копия объекта {@code modelFrom}
     */
    @Override
    public WorkFlowStatus copy(WorkFlowStatus modelFrom, WorkFlowStatus template) {
        WorkFlowStatus clone = new WorkFlowStatus();
        clone.setWorkflow(template.getWorkflow() == null ? modelFrom.getWorkflow() : template.getWorkflow());
        clone.setAlwaysAllow(template.getAlwaysAllow() == null ? modelFrom.getAlwaysAllow() : template.getAlwaysAllow());
        clone.setName(template.getName() == null ? modelFrom.getName() : template.getName());
        return clone;
    }
}