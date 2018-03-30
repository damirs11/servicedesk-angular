package ru.it.sd.dao;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ConfigurationItemMapper;
import ru.it.sd.model.ConfigurationItem;

import java.util.List;

/**
 * Дао для работы с данными объектов
 */
@Repository
public class ConfigurationItemDao extends AbstractEntityDao<ConfigurationItem> {

	private final ConfigurationItemMapper mapper;

	public ConfigurationItemDao(ConfigurationItemMapper mapper){
		this.mapper = mapper;
	}
	/**
	 * Общий запрос получения данных об объекте
	 */

	private static final String BASE_SQL =
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
					"LEFT JOIN itsm_cit_custom_fields AS custom ON custom.ccf_cit_oid = item.cit_oid ";

	@Override
	protected List<ConfigurationItem> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, mapper.asRowMapper());
	}

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

}