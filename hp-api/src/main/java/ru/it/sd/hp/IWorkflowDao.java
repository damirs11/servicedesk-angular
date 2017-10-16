package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.model.EntityType;

/**
 * Created by user on 27.07.2017.
 */
@Repository
public class IWorkflowDao  {

    @Autowired
    private HpApi api;
    @Autowired
    private IChangeDao iChangeDao;
    @Autowired
    private IWorkorderDao iWorkorderDao;

    public IWorkflow read(long id, EntityType entityType) {

        switch (entityType){
            case CHANGE:{
                return iChangeDao.read(id);
            }
            case WORKORDER:{
                return iWorkorderDao.read(id);
            }
            default:{
                throw new IllegalArgumentException("Не указан тип сущности");
            }
        }


    }

}