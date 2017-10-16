package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IApproval;
import com.hp.itsm.api.interfaces.IWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.model.Approval;
import ru.it.sd.model.EntityType;

import java.util.Set;

/**
 * Created by user on 27.07.2017.
 */
@Repository
public class IApprovalDao implements HpCrudDao<Approval, Approval> {

    @Autowired
    private HpApi api;
    @Autowired
    private IWorkflowDao iWorkflowDao;

    @Override
    public long create(Approval entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Approval read(long id) {
        return null;
    }

    @Override
    public void update(Approval entity) {
        //все поля
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    public Approval read(Long id, EntityType entityType) {

        IWorkflow iWorkflow = iWorkflowDao.read(id,entityType);

        IApproval iApproval = iWorkflow.getApproval();

        Approval approval = new Approval();

        approval.setApprovalDescription(iApproval.getDescription());
        approval.setNumberOfApprovers(iApproval.getNrOfApprovers());
        approval.setNumberOfApproversApproved(iApproval.getNrOfApproversApproved());
        approval.setNumberOfApproversRequired(iApproval.getNrOfApproversRequired());

        return approval;
    }

    public void patch(Approval entity, Set<String> fields) {
        /*IWorkflow iWorkflow = iWorkflowDao.read(entity.getId(),entity.getEntityType());
        IApproval iApproval = iWorkflow.getApproval();

        if(fields.contains("approvalStatus")){
            IApprovalStatus iApprovalStatus = api.getSdClient().sd_session().getApprovalStatusHome().openApprovalStatus(entity.getStatus().getId());
            iApproval.setApprovalStatus(iApprovalStatus);
        }
        iApproval.transfer();
        iWorkflow.save();*/
    }
}