package ru.progwards.tasktracker.service.api.impl;

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
public class WorkFlowStatusService implements CreateService<WorkFlowStatus>, RemoveService<WorkFlowStatus>, GetService<Long, WorkFlowStatus>, RefreshService<WorkFlowStatus>, GetListByParentService<Long, WorkFlowStatus> {

    @Autowired
    private Repository<Long, WorkFlowStatusEntity> workFlowStatusRepository;
    @Autowired
    private Converter<WorkFlowStatusEntity, WorkFlowStatus> workFlowStatusConverter;
    @Autowired
    private RepositoryByParentId<Long, WorkFlowStatusEntity> workFlowStatusEntityRepositoryByParentId;


    /**
     * Создание нового WorkFlowStatus
     *
     * @param WorkFlowStatus новый WorkFlowStatus
     */
    @Override
    public void create(WorkFlowStatus WorkFlowStatus) {
        workFlowStatusRepository.create(workFlowStatusConverter.toEntity(WorkFlowStatus));
    }


    /**
     * Удаление WorkFlowStatus
     *
     * @param WorkFlowStatus удаляемый WorkFlowStatus
     */
    @Override
    public void remove(WorkFlowStatus WorkFlowStatus) {
        workFlowStatusRepository.delete(WorkFlowStatus.getId());
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
     * @param WorkFlowStatus измененный WorkFlowStatus
     */
    @Override
    public void refresh(WorkFlowStatus WorkFlowStatus) {
        workFlowStatusRepository.update(workFlowStatusConverter.toEntity(WorkFlowStatus));
    }




    /**
     * Получить список действий для определенного статуса
     *
     * @param parentId WorkFlowStatusStatus.id
     * @return список действий
     */
    @Override
    public Collection<WorkFlowStatus> getListByParentId(Long parentId) {
        // получили список сущностей
        Collection<WorkFlowStatusEntity> WorkFlowStatusActionEntities = workFlowStatusEntityRepositoryByParentId.getByParentId(parentId);
        List<WorkFlowStatus> WorkFlowStatusActions = new ArrayList<>(WorkFlowStatusActionEntities.size());
        // преобразуем к бизнес-объектам
        for (WorkFlowStatusEntity entity:WorkFlowStatusActionEntities) {
            WorkFlowStatusActions.add(workFlowStatusConverter.toVo(entity));
        }
        return WorkFlowStatusActions;
    }


}
