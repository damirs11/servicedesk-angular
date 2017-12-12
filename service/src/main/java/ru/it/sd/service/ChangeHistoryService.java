package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ChangeHistoryDao;
import ru.it.sd.model.Change;
import ru.it.sd.model.ChangeHistory;

import java.util.List;
import java.util.Map;

/**
 * Сервис записей в истории для изменений
 */
@Service
public class ChangeHistoryService implements History<Change, ChangeHistory> {

	private static final Logger LOG = LoggerFactory.getLogger(ChangeHistoryService.class);

	private final ChangeHistoryDao dao;

	@Autowired
	public ChangeHistoryService(ChangeHistoryDao dao) {
		this.dao = dao;
	}

	@Override
	public ChangeHistory read(long id) {
		return dao.read(id);
	}

	@Override
	public List<ChangeHistory> list(Map<String, String> filter) {
		return dao.list(filter);
	}

	@Override
	public int count(Map<String, String> filter) {
		return dao.count(filter);
	}

	@Override
	public void talkToChat(long entityId, String message) {
		//todo
	}
}
