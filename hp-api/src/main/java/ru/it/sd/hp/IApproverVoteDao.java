package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.*;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.ApproverVoteDao;
import ru.it.sd.dao.ChangeDao;
import ru.it.sd.dao.WorkorderDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.Utils.DateUtils;
import ru.it.sd.model.ApproverVote;
import ru.it.sd.model.Change;
import ru.it.sd.model.EntityType;

@Repository
public class IApproverVoteDao implements HpCrudDao<ApproverVote, IApprovalVote>{
    @Autowired
    private HpApi api;


    @Autowired
    private IPersonDao iPersonDao;
    @Autowired
    private IChangeDao iChangeDao;
    @Autowired
    private IWorkorderDao iWorkorderDao;

    @Override
    public long create(ApproverVote entity) {
        SdClientBean sdClientBean = api.getSdClient();
        //Открытие нового пустого мнения
        IApprovalVote iApprovalVote = sdClientBean.sd_session().getApprovalVoteHome().openNewApprovalVote();
        //Открытие персоны
        IPerson iPerson = iPersonDao.read(entity.getApprover().getId());

        //Получаем сущность
        ILifeCycleObject iLifeCycleObject;
        switch (entity.getEntityType()){
            case CHANGE:{
                iLifeCycleObject = iChangeDao.read(entity.getEntityId());
            }break;

            case WORKORDER:{
                iLifeCycleObject = iWorkorderDao.read(entity.getEntityId());
            }break;

            default:{
                throw new IllegalArgumentException("Не указан тип сущности");
            }
        }

        //Заполнение мнения и сохранения
        iApprovalVote.setApprover(iPerson);
        iApprovalVote.setApproval(iLifeCycleObject);
        iApprovalVote.save();
        return iApprovalVote.getOID();
    }

    @Override
    public IApprovalVote read(long id) {
       try{
           return api.getSdClient().sd_session().getApprovalVoteHome().openApprovalVote(id);
       } catch (Exception e){
           throw new ServiceException("Не найден ApproverVote "+e.getMessage(),e);
       }
    }

    @Override
    public void update(ApproverVote entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
