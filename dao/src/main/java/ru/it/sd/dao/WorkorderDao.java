package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeMapper;
import ru.it.sd.dao.mapper.WorkorderMapper;
import ru.it.sd.model.Change;
import ru.it.sd.model.Workorder;

import java.text.MessageFormat;
import java.util.List;

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
			"SELECT" +
					"wor.wor_oid, wor.wor_id, " +
					"wor.reg_created, wor.wor_deadline, wor.wor_actualfinish, " +
					"wor.wor_description, info.woi_information, " +
					"wor.wor_sta_oid, status_name.rct_name, " +
					"worcustom.wcf_boolean2, " +
					"wor.wor_cat_oid, category_name.rct_name, " +
					"change.cha_oid" +
				"FROM" +
					"itsm_workorders as wor" +
					"LEFT OUTER JOIN rep_codes AS status ON status.rcd_oid = wor.wor_sta_oid" +
					"LEFT OUTER JOIN rep_codes_text AS status_name ON status_name.rct_rcd_oid = wor.wor_sta_oid AND status_name.rct_lng_oid = 1049" +
					"LEFT OUTER JOIN itsm_wor_information as info ON info.woi_wor_oid = wor.wor_oid" +
					"LEFT OUTER JOIN itsm_wor_custom_fields as worcustom ON worcustom.wcf_wor_oid = wor.wor_oid" +
					"LEFT OUTER JOIN itsm_changes as change on change.cha_oid = workorder.wor_cha_oid" +
					"LEFT OUTER JOIN rep_codes_text AS category_name ON category_name.rct_rcd_oid = workorder.wor_cat_oid AND category_name.rct_lng_oid = 1049" +
				"{0}";

	/**
	 * Возвращает наряд по его идентификатору
	 * @param id идентификатор наряда
	 * @return наряд
	 */
	public Workorder findOne(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			Workorder workorder = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE wor.wor_oid = :id"),
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