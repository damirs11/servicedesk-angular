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
				queryPart.append(" AND ITEM.CIT_OID = :no");
			}
			if (filter.containsKey("searchCode")) {
				params.addValue("searchCode",filter.get("searchCode"));
				queryPart.append(" AND ITEM.CIT_SEARCHCODE = :searchCode ");
			}
			if (filter.containsKey("name")) {
				params.addValue("name",filter.get("name"));
				queryPart.append(" AND ITEM.CIT_NAME1 = :name ");
			}
			if (filter.containsKey("description")) {
				params.addValue("description",filter.get("description"));
				queryPart.append(" AND ITEM.CIT_NAME2 = :description");
			}
			if (filter.containsKey("orderNr")) {
				params.addValue("orderNr",filter.get("orderNr"));
				queryPart.append(" AND ITEM.CIT_ORDERNR = :orderNr");
			}
			if (filter.containsKey("serial")) {
				params.addValue("serial",filter.get("serial"));
				queryPart.append(" AND ITEM.CIT_SERIALNUMBER = :serial");
			}
			if (filter.containsKey("address")) {
				params.addValue("address",filter.get("address"));
				queryPart.append(" AND CUSTOM.CCF_CITEXT1 = :address");
			}
			if (filter.containsKey("remark")) {
				params.addValue("remark",filter.get("remark"));
				queryPart.append(" AND ITEM.CIT_REMARK = :remark");
			}
			if (filter.containsKey("ip")) {
				params.addValue("ip",filter.get("ip"));
				queryPart.append(" AND ITEM.CIT_IPADDRESS = :ip");
			}
			if (filter.containsKey("status")) {
				params.addValue("status",filter.get("status"));
				queryPart.append(" AND ITEM.CIT_STA_OID = :status");
			}
			if (filter.containsKey("category")) {
				params.addValue("category",filter.get("category"));
				queryPart.append(" AND ITEM.CIT_CAT_OID = :category");
			}
			if (filter.containsKey("location")) {
				params.addValue("location",filter.get("location"));
				queryPart.append(" AND ITEM.CIT_LOC_OID = :location");
			}
			if (filter.containsKey("admin")) {
				params.addValue("admin",filter.get("admin"));
				queryPart.append(" AND ITEM.CIT_ADMIN_PER_OID = :admin");
			}
			if (filter.containsKey("owner")) {
				params.addValue("owner",filter.get("owner"));
				queryPart.append(" AND ITEM.CIT_OWNER_PER_OID = :owner");
			}
			if (filter.containsKey("ownerOrganization")) {
				params.addValue("ownerOrganization",filter.get("ownerOrganization"));
				queryPart.append(" AND ITEM.CIT_OWNER_ORG_OID = :ownerOrganization");
			}
			if (filter.containsKey("payer")) {
				params.addValue("payer",filter.get("payer"));
				queryPart.append(" AND ITEM.CCF_ORG1_OID = :payer");
			}
			if (filter.containsKey("supplier")) {
				params.addValue("supplier",filter.get("supplier"));
				queryPart.append(" AND ITEM.CIT_ORG_OID = :supplier");
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