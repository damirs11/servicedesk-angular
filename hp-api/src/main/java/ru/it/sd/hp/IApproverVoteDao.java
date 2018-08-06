package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IApprovalVote;
import com.hp.itsm.api.interfaces.IPerson;
import com.hp.itsm.api.interfaces.IWorkflow;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.ApproverVote;

import java.util.Set;

@Repository
public class IApproverVoteDao implements HpCrudDao<ApproverVote, IApprovalVote>{

    @Autowired
    private HpApi api;
    @Autowired
    private IWorkflowDao iWorkflowDao;
    @Autowired
    private IPersonDao iPersonDao;

    @Override
    public long create(ApproverVote entity) {
        SdClientBean sdClientBean = api.getSdClient();
        //Открытие нового пустого мнения
        IApprovalVote iApprovalVote = sdClientBean.sd_session().getApprovalVoteHome().openNewApprovalVote();
        //Открытие персоны
        IPerson iPerson = iPersonDao.read(entity.getApprover().getId());
        //Получаем сущность
        IWorkflow iWorkflow = iWorkflowDao.read(entity.getEntityId(), entity.getEntityType());
        //Заполнение мнения и сохранения
        iApprovalVote.setApprover(iPerson);
        iApprovalVote.setApproval(iWorkflow);
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
    public void update(ApproverVote entity, Set<String> fields) {
        IApprovalVote iApprovalVote = read(entity.getId());
        if (fields.contains("approved")) {
            iApprovalVote.setApproved(entity.getApproved());
        }
        if (fields.contains("approver")) {
            iApprovalVote.setApprover(iPersonDao.read(entity.getApprover().getId()));
        }
        if (fields.contains("reason")) {
            iApprovalVote.setReason(entity.getReason());
        }
        iApprovalVote.save();
    }

    @Override
    public void delete(long id) {
        IApprovalVote iApprovalVote = read(id);
        iApprovalVote.delete();
    }
}
