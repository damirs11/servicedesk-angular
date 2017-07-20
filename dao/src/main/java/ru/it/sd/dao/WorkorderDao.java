package ru.it.sd.dao;

import com.jcabi.aspects.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.WorkorderMapper;
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
					"w.wor_oid, " +
					"w.wor_id, " +
					"w.reg_created, " +
					"w.wor_deadline, " +
					"w.reg_modified, " +
					"w.wor_actualfinish, " +
					"w.wor_description, " +
					"winfo.woi_information, " +
					"w.wor_sta_oid, " +
					"w.wor_cat_oid, " +
					"w.wor_clo_oid, " +
					"wcustom.wcf_boolean2, " +
					"w.wor_requestor_per_oid, " +
					"w.ass_workgroup, " +
					"w.ass_per_to_oid, " +
					"wcustom.wcf_duration1, " +
					"wor4k1.wo1_4k1, " +
					"w.wor_ser_oid, " +
					"w.wor_cha_oid, " +
					"wcustom.wcf_org1_oid " +
				"FROM " +
					"itsm_workorders AS w " +
					"LEFT OUTER JOIN itsm_wor_information AS winfo ON winfo.woi_wor_oid = w.wor_oid " +
					"LEFT OUTER JOIN itsm_wor_custom_fields AS wcustom ON wcustom.wcf_wor_oid = w.wor_oid " +
					"LEFT OUTER JOIN itsm_wor_4k1 AS wor4k1 ON wor4k1.wo1_wor_oid = w.wor_oid " +
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
					MessageFormat.format(SELECT_ALL_SQL, " WHERE w.wor_oid = :id"),
					params, mapper);
			return workorder;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Cacheable(lifetime = 5, unit = TimeUnit.SECONDS)
	public List<Workorder> list(Map<String, String> filter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder queryPart = new StringBuilder();

		FilterUtils.createFilter(queryPart, params, filter, Workorder.class);
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
	 * @return список нарядов
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