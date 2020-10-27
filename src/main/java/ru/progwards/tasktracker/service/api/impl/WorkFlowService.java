package ru.progwards.tasktracker.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.WorkFlowEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Бизнес-логика работы с воркфлоу
 *
 * @author Gregory Lobkov
 */
@Service
public class WorkFlowService implements CreateService<WorkFlow>, RemoveService<WorkFlow>, GetService<Long, WorkFlow>, RefreshService<WorkFlow>, GetListService<WorkFlow> {

    @Autowired
    private Repository<Long, WorkFlowEntity> workFlowRepository;
    @Autowired
    private Converter<WorkFlowEntity, WorkFlow> workFlowConverter;


    /**
     * Создание нового Workflow
     *
     * @param workFlow новый Workflow
     */
    @Override
    public void create(WorkFlow workFlow) {
        WorkFlowEntity entity = workFlowConverter.toEntity(workFlow);
        workFlowRepository.create(entity);
        workFlow.setId(entity.getId());
    }


    /**
     * Удаление Workflow
     *
     * @param workFlow удаляемый Workflow
     */
    @Override
    public void remove(WorkFlow workFlow) {
        workFlowRepository.delete(workFlow.getId());
    }


    /**
     * Получить информацию по Workflow
     *
     * @param id идентификатор Workflow
     * @return Workflow
     */
    @Override
    public WorkFlow get(Long id) {
        return workFlowConverter.toVo(workFlowRepository.get(id));
    }


    /**
     * Обновить поля Workflow
     *
     * @param workFlow измененный Workflow
     */
    @Override
    public void refresh(WorkFlow workFlow) {
        workFlowRepository.update(workFlowConverter.toEntity(workFlow));
    }

    /**
     * Получить список всех Workflow
     *
     * @return список Workflow
     */
    @Override
    public Collection<WorkFlow> getList() {
        // получили список сущностей
        Collection<WorkFlowEntity> WorkFlowEntities = workFlowRepository.get();
        List<WorkFlow> WorkFlows = new ArrayList<>(WorkFlowEntities.size());
        // преобразуем к бизнес-объектам
        for (WorkFlowEntity entity : WorkFlowEntities) {
            WorkFlows.add(workFlowConverter.toVo(entity));
        }
        return WorkFlows;
    }

}