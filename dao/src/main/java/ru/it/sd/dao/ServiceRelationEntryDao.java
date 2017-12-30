package ru.it.sd.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ServiceRelationEntryMapper;
import ru.it.sd.model.ServiceRelationEntry;

import java.util.List;
import java.util.Map;

/**
 * Дао для работы с данными взаимосвязями
 */
@Repository
public class ServiceRelationEntryDao extends AbstractEntityDao<ServiceRelationEntry> {

	private ServiceRelationEntryMapper mapper;

	/**
	 * Общий запрос получения данных о согласовании
	 */

	private static final String BASE_SQL =
			"SELECT\n" +
            "   sre.sre_oid," +
            "   sre.sre_revrty_oid," +
            "   sre.sre_ent_oid," +
            "   sre.sre_inc_oid," +
            "   sre.sre_pro_oid," +
            "   sre.sre_ser_oid," +
            "   sre.sre_cha_oid\n" +
            "FROM\n" +
            "itsm_service_relations sre";

	public ServiceRelationEntryDao(ServiceRelationEntryMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<ServiceRelationEntry> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<ServiceRelationEntry>) mapper);
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		/*if (Objects.isNull(filter) || filter.isEmpty() ||
				(!filter.containsKey("entityId") && !filter.containsKey("id"))) {
			throw new BadRequestException("При запросе согласований необходимо указать условия отбора записей (фильтр), например, по полю \"entityId\"");
		}*/
		super.buildWhere(filter, sql, params);
		//todo добавить проверку прав доступа в personId entityType entityId
	}
}