package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ServicecallHistoryDao;
import ru.it.sd.model.HistoryType;
import ru.it.sd.model.ServiceCall;
import ru.it.sd.model.ServicecallHistory;
import ru.it.sd.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис записей в истории для заявок
 */
@Service
public class ServicecallHistoryService extends History<ServiceCall, ServicecallHistory> {

	private static final Logger LOG = LoggerFactory.getLogger(ServicecallHistoryService.class);

	private final ServicecallHistoryDao dao;
	private final SecurityService securityService;
	private final ServiceCallService service;

	@Autowired
	public ServicecallHistoryService(ServicecallHistoryDao dao, SecurityService securityService, ServiceCallService service) {
		this.dao = dao;
		this.securityService = securityService;
		this.service = service;
	}

	@Override
	public ServicecallHistory read(long id) {
		return dao.read(id);
	}

	@Override
	public List<ServicecallHistory> list(Map<String, String> filter) {
		List<ServicecallHistory> list = dao.list(filter);
		User user  = securityService.getCurrentUser();
		for(ServicecallHistory history: list){
			//Проставление значения isOwner(является ли user владельцем сообщения)
            if(history.getAccount() != null){
                if (history.getAccount().getId() == user.getId()){
                    history.setOwner(true);
                } else {
                    history.setOwner(false);
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
		ServiceCall entity = service.read(entityId);
		switch (historyType){
			case SERVICECALL_INITIATOR:{
				fields.add("commentToInitiator");
				entity.setCommentToInitiator(message);
			}break;
			case CHANGE_EXECUTOR:{
				fields.add("commentToExecutor");
				entity.setCommentToExecutor(message);
			}break;
		}
		if(!fields.isEmpty()){
			service.patch(entity, fields);
		}
	}
}
