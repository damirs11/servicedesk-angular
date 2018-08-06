package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IApproval;
import com.hp.itsm.api.interfaces.IApprovalStatus;
import com.hp.itsm.api.interfaces.IWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.hp.utils.DateUtils;
import ru.it.sd.model.Approval;

import java.util.Set;

/**
 * API для работы с согласованиями
 *
 * Created by user on 27.07.2017.
 */
@Repository
public class IApprovalDao implements HpCrudDao<Approval, IApproval> {

    @Autowired
    private IWorkflowDao iWorkflowDao;

    @Autowired
    private IWorkgroupDao iWorkgroupDao;

    @Autowired
    private IApprovalStatusDao iApprovalStatusDao;

    @Override
    public void update(Approval entity, Set<String> fields) {
        IWorkflow iWorkflow = iWorkflowDao.read(entity.getId(),entity.getEntityType());
        IApproval iApproval = iWorkflow.getApproval();

        if(fields.contains("status")){
            IApprovalStatus iApprovalStatus = entity.getStatus() != null ? iApprovalStatusDao.read(entity.getStatus().getId()) : null;
            iApproval.setApprovalStatus(iApprovalStatus);
        }
        if(fields.contains("description")){
            iApproval.setDescription(entity.getDescription());
        }
        if(fields.contains("deadline")){
            iApproval.setApprovalDeadline(DateUtils.toSDDate(entity.getDeadline()));
        }
        if(fields.contains("numberOfApproversRequired")){
            iApproval.setNrOfApproversRequired(entity.getNumberOfApproversRequired());
        }
        if(fields.contains("approvalWorkgroup")){
            iApproval.setWorkGroup(iWorkgroupDao.read(entity.getApprovalWorkgroup().getId()));
        }
        iApproval.transfer();
        iWorkflow.save();
    }

    @Override
    public long create(Approval entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IApproval read(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }


}