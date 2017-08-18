package ru.it.sd.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.model.Change;
import ru.it.sd.model.EntityPriority;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Person;

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

	@Autowired
	private PersonDao personDao;
	@Autowired
	private ChangeMapper changeMapper;

	@Override
	public List<Change> extractData(ResultSet rs) throws SQLException, DataAccessException {
		// Кэш для оптимизации чтения данных из бд. Один из вариантов для оптимизации
		Map<Long, Person> personCache = new HashMap<>();

		List<Change> list = new ArrayList<>();
		int i = 0;
		while(rs.next()){
			Change change = changeMapper.mapRow(rs, 0);
			//Change change = new Change();
			Long statusId = DBUtils.getLong(rs, "cha_sta_oid");
			change.setStatus(EntityStatus.get(statusId));
			Long priorityId = DBUtils.getLong(rs, "cha_imp_oid");
			change.setPriority(EntityPriority.get(priorityId));

			Long executorId = DBUtils.getLong(rs, "ass_per_to_oid");
			change.setExecutor(getPerson(personCache, executorId));
			Long initiatorId = DBUtils.getLong(rs, "cha_requestor_per_oid");
			change.setInitiator(getPerson(personCache, initiatorId));
			Long managerId = DBUtils.getLong(rs, "cha_per_man_oid");
			change.setManager(getPerson(personCache, managerId));
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