package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ApprovalDao;
import ru.it.sd.hp.IApprovalDao;
import ru.it.sd.model.Approval;
import ru.it.sd.model.EntityStatus;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с согласованием
 * @author nsychev
 * @since 07.10.2017
 */
@Service
public class ApprovalService implements CrudService<Approval> {

	private static final Logger LOG = LoggerFactory.getLogger(ApprovalService.class);
	private static final long APPROVAL_PREPARING_STATUS = 281478256721931L; // подготавливается

	private final IApprovalDao iApprovalDao;
	private final ApprovalDao approvalDao;

    @Autowired
    public ApprovalService(IApprovalDao iApprovalDao, ApprovalDao approvalDao) {
        this.iApprovalDao = iApprovalDao;
        this.approvalDao = approvalDao;
    }

    @Override
    public Approval update(Approval entity) {
        if(entity.getStatus() == null || entity.getStatus().getId() == APPROVAL_PREPARING_STATUS) {
            iApprovalDao.update(entity);
        }
        return read(entity.getId());
    }

    @Override
    public Approval patch(Approval entity, Set<String> fields) {
        iApprovalDao.patch(entity,fields);
        return read(entity.getId());
    }

    @Override
    public Approval read(long id) {
        return approvalDao.read(id);
    }

    @Override
    public List<Approval> list(Map<String, String> filter) {
        return null;
    }
    @Override
    public Approval create(Approval entity) {
        return null;
    }
    @Override
    public int count(Map<String, String> filter) {
        return 0;
    }
    @Override
    public void delete(long id) {

    }
}
