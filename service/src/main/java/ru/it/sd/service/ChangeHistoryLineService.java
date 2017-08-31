package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ChangeHistoryLineDao;
import ru.it.sd.model.Change;
import ru.it.sd.model.ChangeHistoryLine;

import java.util.List;
import java.util.Map;

/**
 * Сервис записей в истории для изменений
 */
@Service
public class ChangeHistoryLineService implements History<Change, ChangeHistoryLine> {

	private static final Logger LOG = LoggerFactory.getLogger(ChangeHistoryLineService.class);

	@Autowired
	private ChangeHistoryLineDao dao;

	@Override
	public ChangeHistoryLine read(long id) {
		return dao.read(id);
	}

	@Override
	public List<ChangeHistoryLine> list(Map<String, String> filter) {
		return dao.list(filter);
	}

	@Override
	public int count(Map<String, String> filter) {
		return dao.count(filter);
	}

}
