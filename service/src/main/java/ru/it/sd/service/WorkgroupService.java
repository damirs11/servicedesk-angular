package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.model.Workgroup;

import java.util.List;
import java.util.Map;

/**
 * Сервис рабочих групп
 *
 * @author quadrix
 * @since 15.08.2017
 */
@Service
public class WorkgroupService implements ReadService<Workgroup> {

	private static final Logger LOG = LoggerFactory.getLogger(WorkgroupService.class);

	@Autowired
	private WorkgroupDao dao;

	@Override
	public Workgroup read(long id) {
		return dao.read(id);
	}

	@Override
	public List<Workgroup> list(Map<String, String> filter) {
		return dao.list(filter);
	}

	@Override
	public int count(Map<String, String> filter) {
		return dao.count(filter);
	}

}
