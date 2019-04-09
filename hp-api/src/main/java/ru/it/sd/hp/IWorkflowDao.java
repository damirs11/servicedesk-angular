package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.hp.change.IChangeDao;
import ru.it.sd.hp.problem.IProblemDao;
import ru.it.sd.hp.servicecall.IServicecallDao;
import ru.it.sd.hp.workorder.IWorkorderDao;
import ru.it.sd.model.EntityType;

/**
 * Created by user on 27.07.2017.
 */
@Repository
public class IWorkflowDao {

    private final IChangeDao iChangeDao;
    private final IWorkorderDao iWorkorderDao;
    private final IProblemDao iProblemDao;
    private final IServicecallDao iServicecallDao;

    @Autowired
    public IWorkflowDao(IChangeDao iChangeDao, IWorkorderDao iWorkorderDao, IProblemDao iProblemDao, IServicecallDao iServicecallDao) {
        this.iChangeDao = iChangeDao;
        this.iWorkorderDao = iWorkorderDao;
        this.iProblemDao = iProblemDao;
        this.iServicecallDao = iServicecallDao;
    }

    public IWorkflow read(long id, EntityType entityType) {

        if (entityType == null) throw new IllegalArgumentException("Не указан тип сущности");
        switch (entityType) {
            case CHANGE: return iChangeDao.read(id);
            case WORKORDER: return iWorkorderDao.read(id);
            case PROBLEM: return iProblemDao.read(id);
            case SERVICECALL: return iServicecallDao.read(id);
            default: throw new IllegalArgumentException("Не правильно указан тип сущности");
        }


    }

}