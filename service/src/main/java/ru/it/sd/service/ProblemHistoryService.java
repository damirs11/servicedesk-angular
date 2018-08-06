package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ChangeHistoryDao;
import ru.it.sd.dao.ProblemHistoryDao;
import ru.it.sd.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис записей в истории для изменений
 */
@Service
public class ProblemHistoryService extends History<Problem, ProblemHistory> {

	private static final Logger LOG = LoggerFactory.getLogger(ProblemHistoryService.class);

	private final ProblemHistoryDao dao;
	private final SecurityService securityService;
	private final ProblemService problemService;

	@Autowired
	public ProblemHistoryService(ProblemHistoryDao dao, SecurityService securityService, ProblemService problemService) {
		this.dao = dao;
		this.securityService = securityService;
		this.problemService = problemService;
	}

	@Override
	public ProblemHistory read(long id) {
		return dao.read(id);
	}

	@Override
	public List<ProblemHistory> list(Map<String, String> filter) {
		List<ProblemHistory> list = dao.list(filter);
		User user  = securityService.getCurrentUser();
		for(ProblemHistory history: list){
			//Проставление значения isOwner(является ли user владельцем сообщения)
            if(history.getAccount() != null){
                if (history.getAccount().getId() == user.getId()){
                    history.setIsOwner(true);
                } else {
                    history.setIsOwner(false);
                }
	        }
		}
		return list;
	}

	@Override
	public int count(Map<String, String> filter) {
		return dao.count(filter);
	}

	@Override
	public void talkToChat(long entityId, String message, HistoryType historyType) {
		Set<String> fields = new HashSet<>();
		Problem change = problemService.read(entityId);
		switch (historyType){
			case CHANGE_INITIATOR:{
				fields.add("commentToInitiator");
				change.setCommentToInitiator(message);
			}break;
			case CHANGE_MANAGER:{
				fields.add("commentToManager");
				//todo проверить наличие поля ниже. В проблемах есть менеджер?
				//change.setCommentToManager(message);
			}break;
			case CHANGE_EXECUTOR:{
				fields.add("commentToExecutor");
				change.setCommentToExecutor(message);
			}break;
		}
		if(!fields.isEmpty()){
			problemService.patch(change, fields);
		}
	}
}
