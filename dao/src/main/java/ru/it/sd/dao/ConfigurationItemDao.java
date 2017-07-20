package ru.it.sd.dao;

import com.jcabi.aspects.Cacheable;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
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
					"ITEM.CIT_OID, " +
					"ITEM.CIT_ID, " +
					"ITEM.CIT_SEARCHCODE, " +
					"ITEM.CIT_SERIALNUMBER," +
					"CUSTOM.CCF_CISHORTTEXT1," +
					"ITEM.CIT_CAT_OID," +
					"ITEM.CIT_IPADDRESS, " +
					"ITEM.CIT_PURCHASEDATE, " +
					"ITEM.CIT_PRICE, " +
					"ITEM.CIT_WARRANTYDATE, " +
					"ITEM.CIT_ADMIN_PER_OID, " +
					"ITEM.CIT_OWNER_PER_OID, " +
					"ITEM.CIT_ATTACHMENT_EXISTS, " +
					"ITEM.CIT_NAME1, " +
					"ITEM.CIT_NAME2, " +
					"ITEM.CIT_ORDERNR, " +
					"ITEM.CIT_LOC_OID, " +
					"CUSTOM.CCF_CITEXT1," +
					"ITEM.CIT_POO_OID, " +
					"ITEM.CIT_STA_OID, " +
					"ITEM.CIT_POO_OID, " +
					"ITEM.CIT_REMARK, " +
					"ITEM.CIT_BRA_OID, " +
					"CUSTOM.CCF_CIDATE1, " +
					"ITEM.CIT_OWNER_ORG_OID, " +
					"CUSTOM.CCF_ORG1_OID, " +
					"ITEM.CIT_ORG_OID " +
				" FROM " +
					"ITSM_CONFIGURATION_ITEMS as ITEM " +
					"LEFT OUTER JOIN ITSM_CIT_CUSTOM_FIELDS as CUSTOM ON CUSTOM.CCF_CIT_OID = ITEM.CIT_OID " +
				"{0}";

	/**
	 * Возвращает объект по его идентификатору
	 * @param id идентификатор объекта
	 * @return объект
	 */
	@Cacheable(lifetime = 5, unit = TimeUnit.SECONDS)
	public ConfigurationItem read(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			ConfigurationItem item = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE ITEM.CIT_OID = :id"),
					params, mapper);
			return item;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Cacheable(lifetime = 5, unit = TimeUnit.SECONDS)
	public List<ConfigurationItem> list(Map<String, String> filter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder queryPart = new StringBuilder();
		MultiMap filterFields = new MultiValueMap();
		filterFields.put("ITEM","CIT_SEARCHCODE");
		filterFields.put("ITEM","CIT_NAME1");
        filterFields.put("ITEM","CIT_ID");
        filterFields.put("ITEM","CIT_OWNER_PER_OID");
		filterFields.put("ITEM","CIT_PURCHASEDATE");
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