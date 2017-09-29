package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ApproverVoteDao;
import ru.it.sd.model.ApproverVote;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с голосованием
 * @author quadrix
 * @since 13.05.2017
 */
@Service
public class ApproverVoteService implements CrudService<ApproverVote>{

	private static final Logger logger = LoggerFactory.getLogger(ApproverVoteService.class);

	private ApproverVoteDao dao;
	private SecurityService securityService;

	public ApproverVoteService(ApproverVoteDao dao, SecurityService securityService) {
		this.dao = dao;
		this.securityService = securityService;
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
		//todo Никита
		return null;
	}

	@Override
	public ApproverVote update(ApproverVote entity) {
		//todo Никита
		return null;
	}

	@Override
	public void delete(long id) {
		//todo
	}

	@Override
	public ApproverVote patch(ApproverVote entity, Set<String> fields) {
		//todo
		return entity;
	}

}
