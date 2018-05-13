package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ChangeHistoryDao;
import ru.it.sd.model.Change;
import ru.it.sd.model.ChangeHistory;
import ru.it.sd.model.HistoryType;
import ru.it.sd.model.User;

import java.util.*;

/**
 * Сервис записей в истории для изменений
 */
@Service
public class ChangeHistoryService extends History<Change, ChangeHistory> {

	private static final Logger LOG = LoggerFactory.getLogger(ChangeHistoryService.class);

	private final ChangeHistoryDao dao;
	private final SecurityService securityService;
	private final ChangeService changeService;

	@Autowired
	public ChangeHistoryService(ChangeHistoryDao dao, SecurityService securityService, ChangeService changeService) {
		this.dao = dao;
		this.securityService = securityService;
		this.changeService = changeService;
	}

	@Override
	public ChangeHistory read(long id) {
		return dao.read(id);
	}

	@Override
	public List<ChangeHistory> list(Map<String, String> filter) {
		List<ChangeHistory> list = dao.list(filter);
		User user  = securityService.getCurrentUser();
		for(ChangeHistory changeHistory: list){
			//Проставление значения isOwner(является ли user владельцем сообщения)
			if (changeHistory.getAccount().getId() == user.getId()){
				changeHistory.setIsOwner(true);
			} else {
				changeHistory.setIsOwner(false);
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
		Change change = changeService.read(entityId);
		switch (historyType){
			case CHANGE_INITIATOR:{
				fields.add("commentToInitiator");
				change.setCommentToInitiator(message);
			}break;
			case CHANGE_MANAGER:{
				fields.add("commentToManager");
				change.setCommentToManager(message);
			}break;
			case CHANGE_EXECUTOR:{
				fields.add("commentToExecutor");
				change.setCommentToExecutor(message);
			}break;
		}
		if(!fields.isEmpty()){
			changeService.patch(change, fields);
		}

	}
}
