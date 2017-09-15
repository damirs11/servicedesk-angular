package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ConfigurationItemMapper;
import ru.it.sd.model.ConfigurationItem;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Дао для работы с данными объектов
 */
@Repository
public class ConfigurationItemDao extends AbstractDao {

	@Autowired
	private ConfigurationItemMapper mapper;

	/**
	 * Общий запрос получения данных об объекте
	 */

	private static final String SELECT_ALL_SQL =
			"SELECT " +
					"item.cit_oid, " +
					"item.cit_id, " +
					"item.cit_searchcode, " +
					"item.cit_serialnumber," +
					"custom.ccf_cishorttext1," +
					"item.cit_cat_oid," +
					"item.cit_ipaddress, " +
					"item.cit_purchasedate, " +
					"item.cit_price, " +
					"item.cit_warrantydate, " +
					"item.cit_admin_per_oid, " +
					"item.cit_owner_per_oid, " +
					"item.cit_attachment_exists, " +
					"item.cit_name1, " +
					"item.cit_name2, " +
					"item.cit_ordernr, " +
					"item.cit_loc_oid, " +
					"custom.ccf_citext1," +
					"item.cit_poo_oid, " +
					"item.cit_sta_oid, " +
					"item.cit_poo_oid, " +
					"item.cit_remark, " +
					"item.cit_bra_oid, " +
					"custom.ccf_cidate1, " +
					"item.cit_owner_org_oid, " +
					"custom.ccf_org1_oid, " +
					"item.cit_org_oid " +
				" FROM " +
					"itsm_configuration_items AS item " +
					"LEFT JOIN itsm_cit_custom_fields AS custom ON custom.ccf_cit_oid = item.cit_oid " +
				"{0}";

	/**
	 * Возвращает объект по его идентификатору
	 * @param id идентификатор объекта
	 * @return объект
	 */
	public ConfigurationItem read(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			ConfigurationItem item = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE item.cit_oid = :id"),
					params, mapper);
			return item;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<ConfigurationItem> list(Map<String, String> filter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder queryPart = new StringBuilder();
        FilterUtils.createFilter(queryPart, params, filter, ConfigurationItem.class);

		try {
			List<ConfigurationItem> items = namedJdbc.query(
					MessageFormat.format(SELECT_ALL_SQL, queryPart),
					params, ((ResultSetExtractor<List<ConfigurationItem>>) mapper));
			return items;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	/**
	 * Возвращает все объекты
	 * @return лист объектов
	 */
	public List<ConfigurationItem> findAll() {
		try {
			return namedJdbc.query(SELECT_ALL_SQL, ((ResultSetExtractor<List<ConfigurationItem>>) null));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}