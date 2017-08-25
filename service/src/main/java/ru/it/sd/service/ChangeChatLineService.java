package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.ChangeChatLineDao;
import ru.it.sd.model.ChangeChatLine;

import java.util.List;
import java.util.Map;

/**
 * Сервис записей в истории для изменений
 */
@Service
public class ChangeChatLineService implements ReadService<ChangeChatLine> {

	private static final Logger LOG = LoggerFactory.getLogger(ChangeChatLineService.class);

	@Autowired
	private ChangeChatLineDao dao;

	@Override
	public ChangeChatLine read(long id) {
		return dao.read(id);
	}

	@Override
	public List<ChangeChatLine> list(Map<String, String> filter) {
		return dao.list(filter);
	}

	@Override
	public int count(Map<String, String> filter) {
		return dao.count(filter);
	}

}
