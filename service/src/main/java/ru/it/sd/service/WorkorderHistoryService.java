package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.WorkorderHistoryDao;
import ru.it.sd.model.HistoryType;
import ru.it.sd.model.User;
import ru.it.sd.model.Workorder;
import ru.it.sd.model.WorkorderHistory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис записей в истории для изменений
 */
@Service
public class WorkorderHistoryService extends History<Workorder, WorkorderHistory> {

	private static final Logger LOG = LoggerFactory.getLogger(WorkorderHistoryService.class);

	private final WorkorderHistoryDao dao;
	private final SecurityService securityService;
	private final WorkorderService workorderService;

	@Autowired
	public WorkorderHistoryService(WorkorderHistoryDao dao, SecurityService securityService, WorkorderService workorderService) {
		this.dao = dao;
		this.securityService = securityService;
		this.workorderService = workorderService;
	}

	@Override
	public WorkorderHistory read(long id) {
		return dao.read(id);
	}

	@Override
	public List<WorkorderHistory> list(Map<String, String> filter) {
		List<WorkorderHistory> list = dao.list(filter);
		User user  = securityService.getCurrentUser();
		for(WorkorderHistory history: list){
			//Проставление значения isOwner(является ли user владельцем сообщения)
            if(history.getAccount() != null) {
                if (history.getAccount().getId() == user.getId()) {
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
		Workorder workorder = workorderService.read(entityId);
		switch (historyType){
			case WORKORDER_EXECUTOR:{
				fields.add("commentToExecutor");
				workorder.setCommentToExecutor(message);
			}break;
			case WORKORDER_INITIATOR:{
				fields.add("commentToInitiator");
				workorder.setSolution(message);
			}break;
		}
		if(!fields.isEmpty()){
			workorderService.patch(workorder, fields);
		}
	}
}
