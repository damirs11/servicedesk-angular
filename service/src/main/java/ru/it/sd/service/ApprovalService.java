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

	@Autowired
	private IApprovalDao iApprovalDao;

	@Autowired
    private ApprovalDao approvalDao;
    @Override
    public Approval update(Approval entity) {
        if(entity.getStatus() == EntityStatus.APPROVAL_PREPARING || entity.getStatus() == null) {
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
