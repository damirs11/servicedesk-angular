package ru.it.sd.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.utils.DBUtils;
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

	private final PersonDao personDao;
	private final ChangeMapper changeMapper;
	private final WorkgroupDao workgroupDao;
	private final CodeDao codeDao;

	public ChangeExtractor(PersonDao personDao, ChangeMapper changeMapper, WorkgroupDao workgroupDao, CodeDao codeDao) {
		this.personDao = personDao;
		this.changeMapper = changeMapper;
		this.workgroupDao = workgroupDao;
		this.codeDao = codeDao;
	}

	@Override
	public List<Change> extractData(ResultSet rs) throws SQLException, DataAccessException {
		// Кэш для оптимизации чтения данных из бд. Один из вариантов для оптимизации
		Map<Long, Person> personCache = new HashMap<>();

		List<Change> list = new ArrayList<>();
		while(rs.next()){
			Change change = changeMapper.mapRow(rs, 0);

			Long statusId = DBUtils.getLong(rs, "cha_sta_oid");

			if(statusId !=null) {
                BaseCode code = codeDao.read(statusId);
			    change.setStatus(code.convertTo(EntityStatus.class));
            }

			Long priorityId = DBUtils.getLong(rs, "cha_imp_oid");
			if(priorityId != null) {
                BaseCode code = codeDao.read(priorityId);
			    change.setPriority(code.convertTo(EntityPriority.class));
            }

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
			if(categoryId != null) {
                BaseCode code = codeDao.read(categoryId);
			    change.setCategory(code.convertTo(EntityCategory.class));
            }

            Long classificationId = DBUtils.getLong(rs, "cha_cla_oid");
            if(classificationId != null) {
                BaseCode code = codeDao.read(classificationId);
                change.setClassification(code.convertTo(EntityClassification.class));
            }

            Long closureCodeId = DBUtils.getLong(rs, "cha_closurecode");
            if(closureCodeId != null) {
                BaseCode code = codeDao.read(closureCodeId);
                change.setClosureCode(code.convertTo(EntityClosureCode.class));
            }

            Long folderId = DBUtils.getLong(rs, "cha_poo_oid");
            if(folderId != null) {
                BaseCode code = codeDao.read(folderId);
                change.setFolder(code.convertTo(Folder.class));
            }

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