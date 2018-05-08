package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ApproverVoteDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.hp.IApproverVoteDao;
import ru.it.sd.model.ApproverVote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с голосованием
 * @author quadrix
 * @since 13.05.2017
 */
@Service
public class ApproverVoteService extends CrudService<ApproverVote>{

	private static final Logger logger = LoggerFactory.getLogger(ApproverVoteService.class);

	private ApproverVoteDao dao;
	private SecurityService securityService;
	private IApproverVoteDao hpDao;

	public ApproverVoteService(ApproverVoteDao dao, SecurityService securityService, IApproverVoteDao hpDao) {
		this.dao = dao;
		this.securityService = securityService;
		this.hpDao = hpDao;
	}

	@Override
	public ApproverVote read(long id) {
		return dao.read(id);
	}

	@Override
	public List<ApproverVote> list(Map<String, String> filter) {
		return dao.list(filter);
	}

	@Override
	public int count(Map<String, String> filter) {
		return dao.count(filter);
	}

	@Override
	public ApproverVote create(ApproverVote entity) {
	    Map<String, String> filter = new HashMap<>();
	    filter.put("entityId", entity.getEntityId().toString());
	    filter.put("approver", entity.getApprover().getId().toString());
	    List<ApproverVote> approverVotes = list(filter);
	    if(approverVotes.size()>0) throw new ServiceException("Персона уже добавлена в список согласующих");
		try{
			Long id = hpDao.create(entity);
			return dao.read(id);
		}catch (Exception e){
			throw new ServiceException("Возникли проблемы при создании мнения. " + e.getMessage(), e);
		}
	}

	@Override
	public ApproverVote update(ApproverVote entity) {
		try{
			hpDao.update(entity);
			return dao.read(entity.getId());
		} catch(Exception e){
			throw new ServiceException("Возникли проблемы при редактировании мнения. " + e.getMessage(), e);
		}
	}

	@Override
	public void delete(long id) {
		try{
			hpDao.delete(id);
		} catch(Exception e){
			throw new ServiceException("Возникли проблемы при удалении мнения. " + e.getMessage(), e);
		}
	}

	@Override
	public ApproverVote patch(ApproverVote entity, Set<String> fields) {
		hpDao.patch(entity, fields);
		return dao.read(entity.getId());
	}

}
