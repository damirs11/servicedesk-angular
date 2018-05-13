package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.WorkorderHistoryDao;
import ru.it.sd.model.HistoryType;
import ru.it.sd.model.Workorder;
import ru.it.sd.model.WorkorderHistory;

import java.util.List;
import java.util.Map;

/**
 * Сервис записей в истории для изменений
 */
@Service
public class WorkorderHistoryService extends History<Workorder, WorkorderHistory> {

	private static final Logger LOG = LoggerFactory.getLogger(WorkorderHistoryService.class);

	@Autowired
	private WorkorderHistoryDao dao;

	@Override
	public WorkorderHistory read(long id) {
		return dao.read(id);
	}

	@Override
	public List<WorkorderHistory> list(Map<String, String> filter) {
		return dao.list(filter);
	}

	@Override
	public int count(Map<String, String> filter) {
		return dao.count(filter);
	}

	@Override
	public void talkToChat(long entityId, String message, HistoryType historyType) {
		//todo
	}
}
