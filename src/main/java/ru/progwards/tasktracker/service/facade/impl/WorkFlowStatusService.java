package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByParentId;
import ru.progwards.tasktracker.repository.entity.WorkFlowStatusEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.WorkFlowStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Бизнес-логика работы со статусами воркфлоу
 *
 * @author Gregory Lobkov
 */
@Service
public class WorkFlowStatusService implements CreateService<WorkFlowStatus>, RemoveService<WorkFlowStatus>, GetService<Long, WorkFlowStatus>, GetListService<WorkFlowStatus>, RefreshService<WorkFlowStatus>, GetListByParentService<Long, WorkFlowStatus> {

    @Autowired
    private Repository<Long, WorkFlowStatusEntity> workFlowStatusRepository;
    @Autowired
    private Converter<WorkFlowStatusEntity, WorkFlowStatus> workFlowStatusConverter;
    @Autowired
    private RepositoryByParentId<Long, WorkFlowStatusEntity> workFlowStatusEntityRepositoryByParentId;


    /**
     * Создание нового WorkFlowStatus
     *
     * @param workFlowStatus новый WorkFlowStatus
     */
    @Override
    public void create(WorkFlowStatus workFlowStatus) {
        WorkFlowStatusEntity entity = workFlowStatusConverter.toEntity(workFlowStatus);
        workFlowStatusRepository.create(entity);
        workFlowStatus.setId(entity.getId());
    }


    /**
     * Удаление WorkFlowStatus
     *
     * @param workFlowStatus удаляемый WorkFlowStatus
     */
    @Override
    public void remove(WorkFlowStatus workFlowStatus) {
        workFlowStatusRepository.delete(workFlowStatus.getId());
    }


    /**
     * Получить информацию по WorkFlowStatus
     *
     * @param id идентификатор WorkFlowStatus
     * @return WorkFlowStatus
     */
    @Override
    public WorkFlowStatus get(Long id) {
        return workFlowStatusConverter.toVo(workFlowStatusRepository.get(id));
    }


    /**
     * Обновить поля WorkFlowStatus
     *
     * @param workFlowStatus измененный WorkFlowStatus
     */
    @Override
    public void refresh(WorkFlowStatus workFlowStatus) {
        workFlowStatusRepository.update(workFlowStatusConverter.toEntity(workFlowStatus));
    }




    /**
     * Получить список статусов для определенного статуса
     *
     * @param parentId WorkFlowStatusStatus.id
     * @return список действий
     */
    @Override
    public Collection<WorkFlowStatus> getListByParentId(Long parentId) {
        // получили список сущностей
        Collection<WorkFlowStatusEntity> workFlowStatusEntities = workFlowStatusEntityRepositoryByParentId.getByParentId(parentId);
        List<WorkFlowStatus> workFlowStatusActions = new ArrayList<>(workFlowStatusEntities.size());
        // преобразуем к бизнес-объектам
        for (WorkFlowStatusEntity entity:workFlowStatusEntities) {
            workFlowStatusActions.add(workFlowStatusConverter.toVo(entity));
        }
        return workFlowStatusActions;
    }


    @Override
    public Collection<WorkFlowStatus> getList() {
        // получили список сущностей
        Collection<WorkFlowStatusEntity> workFlowStatusEntities = workFlowStatusRepository.get();
        List<WorkFlowStatus> workFlowStatusActions = new ArrayList<>(workFlowStatusEntities.size());
        // преобразуем к бизнес-объектам
        for (WorkFlowStatusEntity entity:workFlowStatusEntities) {
            workFlowStatusActions.add(workFlowStatusConverter.toVo(entity));
        }
        return workFlowStatusActions;
    }
}
