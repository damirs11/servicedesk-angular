package ru.it.sd.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Маппер персон
 *
 * @author quadrix
 * @since 03.05.2017
 */
@Component
public class ChangeExtractor implements ResultSetExtractor<List<Change>> {

	private PersonDao personDao;
	private ChangeMapper changeMapper;
	private WorkgroupDao workgroupDao;

	public ChangeExtractor(PersonDao personDao, ChangeMapper changeMapper, WorkgroupDao workgroupDao) {
		this.personDao = personDao;
		this.changeMapper = changeMapper;
		this.workgroupDao = workgroupDao;
	}

	@Override
	public List<Change> extractData(ResultSet rs) throws SQLException, DataAccessException {
		// Кэш для оптимизации чтения данных из бд. Один из вариантов для оптимизации
		Map<Long, Person> personCache = new HashMap<>();

		List<Change> list = new ArrayList<>();
		while(rs.next()){
			Change change = changeMapper.mapRow(rs, 0);

			Long statusId = DBUtils.getLong(rs, "cha_sta_oid");
			if(statusId !=null) change.setStatus(EntityStatus.get(statusId));

			Long priorityId = DBUtils.getLong(rs, "cha_imp_oid");
			if(priorityId != null) change.setPriority(EntityPriority.get(priorityId));

			Long executorId = DBUtils.getLong(rs, "ass_per_to_oid");
			if(executorId != null) change.setExecutor(getPerson(personCache, executorId));

			Long assWorkgroupID = DBUtils.getLong(rs, "ass_wog_oid");
			if(assWorkgroupID != null) {
			    Workgroup workgroup = workgroupDao.read(assWorkgroupID);
			    change.setAssWorkgroup(workgroup);
            }

			Long initiatorId = DBUtils.getLong(rs, "cha_requestor_per_oid");
			if(initiatorId !=null) change.setInitiator(getPerson(personCache, initiatorId));

			Long managerId = DBUtils.getLong(rs, "cha_per_man_oid");
			if(managerId != null) change.setManager(getPerson(personCache, managerId));

			Long categoryId = DBUtils.getLong(rs, "cha_cat_oid");
			if(categoryId != null) change.setCategory(EntityCategory.getById(categoryId));

            Long classificationId = DBUtils.getLong(rs, "cha_cla_oid");
            if(classificationId != null) change.setClassification(EntityClassification.getById(classificationId));

			list.add(change);
		}
		return list;
	}

	/**
	 * Получает персону по идентификатору. При этом используется кэширование записей
	 *
	 * @param cache кэш персон
	 * @param id идентификатор персоны
	 * @return персона
	 */
	private Person getPerson(Map<Long, Person> cache, Long id) {
		Person person = cache.get(id);
		if (Objects.isNull(person)) {
			person = personDao.read(id);
			cache.put(id, person);
		}
		return person;
	}
}