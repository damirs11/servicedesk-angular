package ru.it.sd.dao;

import com.jcabi.aspects.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.WorkgroupMapper;
import ru.it.sd.dao.mapper.WorkorderMapper;
import ru.it.sd.model.PagingRange;
import ru.it.sd.model.Workgroup;
import ru.it.sd.model.Workorder;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Дао для работы с данными нарядов
 */
@Repository
public class WorkgroupDao extends AbstractDao {

	@Autowired
	private WorkgroupMapper mapper;

	/**
	 * Общий запрос получения данных о наряде
	 */

	private static final String SELECT_ALL_SQL =
			"SELECT " +
					"wg.wog_oid, " +
					"wg.wog_name, " +
					"wg.wog_searchcode, " +
					"wg.wog_sta_oid " +

				"FROM " +
					"itsm_workgroups AS wg " +
				"{0}";



	public Workgroup read(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			Workgroup workgroup = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE wg.wog_oid = :id"),
					params, mapper);
			return workgroup;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	public List<Workgroup> list(Map<String, String> filter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder queryPart = new StringBuilder();

		FilterUtils.createFilter(queryPart, params, filter, Workorder.class);
		try {
			List<Workgroup> workgroups = namedJdbc.query(
					MessageFormat.format(SELECT_ALL_SQL, queryPart),
					params, ((ResultSetExtractor<List<Workgroup>>) mapper));
			return workgroups;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int getTotal(Map<String, String> filter) {
		// Чтобы получить общее количество строк с учетом фильтрации, то
		// выполняем обычный запрос на получение списка, но без условия отбора страницы
		Map<String, String> filterWithoutPaging = new HashMap<>(filter);
		filterWithoutPaging.remove(PagingRange.PAGING_PARAM_NAME);
		List<Workgroup> list = list(filterWithoutPaging);
		return list.size();
	}
}