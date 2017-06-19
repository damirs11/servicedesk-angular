package ru.it.sd.dao;

import com.jcabi.aspects.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ConfigurationItemMapper;
import ru.it.sd.dao.mapper.WorkorderMapper;
import ru.it.sd.model.ConfigurationItem;
import ru.it.sd.model.Workorder;

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
	 * Общий запрос получения данных о объекте
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
			List<ConfigurationItem> items = namedJdbc.query(SELECT_ALL_SQL,
					((ResultSetExtractor<List<ConfigurationItem>>) null));
			return items;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}