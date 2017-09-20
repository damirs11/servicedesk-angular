package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.WorkorderHistoryLineDao;
import ru.it.sd.model.Workorder;
import ru.it.sd.model.WorkorderHistoryLine;

import java.util.List;
import java.util.Map;

/**
 * Сервис записей в истории для изменений
 */
@Service
public class WorkorderHistoryLineService implements History<Workorder, WorkorderHistoryLine> {

	private static final Logger LOG = LoggerFactory.getLogger(WorkorderHistoryLineService.class);

	@Autowired
	private WorkorderHistoryLineDao dao;

	@Override
	public WorkorderHistoryLine read(long id) {
		return dao.read(id);
	}

	@Override
	public List<WorkorderHistoryLine> list(Map<String, String> filter) {
		return dao.list(filter);
	}

	@Override
	public int count(Map<String, String> filter) {
		return dao.count(filter);
	}

}
