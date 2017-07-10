package ru.it.sd.dao;

import com.jcabi.aspects.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.WorkorderMapper;
import ru.it.sd.model.Change;
import ru.it.sd.model.Workorder;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Дао для работы с данными нарядов
 */
@Repository
public class WorkorderDao extends AbstractDao {

	@Autowired
	private WorkorderMapper mapper;

	/**
	 * Общий запрос получения данных о наряде
	 */

	private static final String SELECT_ALL_SQL =
			"SELECT " +
					"WORKORDER.WOR_OID, " +
					"WORKORDER.WOR_ID, " +
					"WORKORDER.REG_CREATED, " +
					"WORKORDER.WOR_DEADLINE, " +
					"WORKORDER.REG_MODIFIED, " +
					"WORKORDER.WOR_ACTUALFINISH, " +
					"WORKORDER.WOR_DESCRIPTION, " +
					"WOR_INFO.WOI_INFORMATION, " +
					"WORKORDER.WOR_STA_OID, " +
					"WORKORDER.WOR_CAT_OID, " +
					"WORKORDER.WOR_CLO_OID, " +
					"WORCUSTOM.WCF_BOOLEAN2, " +
					"WORKORDER.WOR_REQUESTOR_PER_OID, " +
					"WORKORDER.ASS_WORKGROUP, " +
					"WORKORDER.ASS_PER_TO_OID, " +
					"WORCUSTOM.WCF_DURATION1, " +
					"WOR4K1.WO1_4K1, " +
					"WORKORDER.WOR_SER_OID, " +
					"WORKORDER.WOR_CHA_OID, " +
					"WORCUSTOM.WCF_ORG1_OID " +
				"FROM " +
					"ITSM_WORKORDERS as WORKORDER " +
					"LEFT OUTER JOIN ITSM_WOR_INFORMATION as WOR_INFO ON WOR_INFO.WOI_WOR_OID = WORKORDER.WOR_OID " +
					"LEFT OUTER JOIN ITSM_WOR_CUSTOM_FIELDS as WORCUSTOM ON WORCUSTOM.WCF_WOR_OID = WORKORDER.WOR_OID " +
					"LEFT OUTER JOIN ITSM_WOR_4K1 AS WOR4K1 ON WOR4K1.WO1_WOR_OID = WORKORDER.WOR_OID " +
				"{0}";

	/**
	 * Возвращает наряд по его идентификатору
	 * @param id идентификатор наряда
	 * @return наряд
	 */
	@Cacheable(lifetime = 5, unit = TimeUnit.SECONDS)
	public Workorder read(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			Workorder workorder = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE WORKORDER.WOR_OID = :id"),
					params, mapper);
			return workorder;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	/**
	 * Возвращает изменение по идентификатору изменения
	 * @param changeId идентификатор изменения
	 * @return лист нарядов
	 */
	public List<Workorder> findByChange(Long changeId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("changeId", changeId);
		try {
			List<Workorder> workorders = namedJdbc.query(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE change.cha_oid = :changeId"),
					params, ((ResultSetExtractor<List<Workorder>>) mapper));
			return workorders;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Cacheable(lifetime = 5, unit = TimeUnit.SECONDS)
	public List<Workorder> list(Map<String, String> filter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder queryPart = new StringBuilder();
		queryPart.append(" WHERE TRUE ");
		if (filter != null) {
			if (filter.containsKey("no")) {
				params.addValue("no",filter.get("no"));
				queryPart.append(" AND WORKORDER.WOR_ID = :no");
			}
			if (filter.containsKey("assigneePerson")) {
				params.addValue("assigneePerson",filter.get("assigneePerson"));
				queryPart.append(" AND WORKORDER.ASS_PER_TO_OID = :assigneePerson ");
			}
			if (filter.containsKey("initiator")) {
				params.addValue("initiator",filter.get("initiator"));
				queryPart.append(" AND WORKORDER.WOR_REQUESTOR_PER_OID = :initiator ");
			}
			if (filter.containsKey("changeId")) {
				params.addValue("changeId",filter.get("changeId"));
				queryPart.append(" AND WORKORDER.WOR_REQUESTOR_PER_OID = :changeId");
			}
			if (filter.containsKey("status")) {
				params.addValue("status",filter.get("status"));
				queryPart.append(" AND WORKORDER.WOR_STA_OID = :status");
			}
		}
		try {
			List<Workorder> workorders = namedJdbc.query(
					MessageFormat.format(SELECT_ALL_SQL, queryPart),
					params, ((ResultSetExtractor<List<Workorder>>) mapper));
			return workorders;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	/**
	 * Возвращает все наряды
	 * @return лист нарядов
	 */
	public List<Workorder> findAll() {
		try {
			List<Workorder> workorders = namedJdbc.query(SELECT_ALL_SQL,
					((ResultSetExtractor<List<Workorder>>) mapper));
			return workorders;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}