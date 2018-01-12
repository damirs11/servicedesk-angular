package ru.it.sd.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeExtractor;
import ru.it.sd.dao.mapper.GrantMapper;
import ru.it.sd.model.Change;
import ru.it.sd.model.Grant;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Дао для работы с правами доступа
 *
 * @author quadrix
 * @since 10.10.2017
 */
@Repository
public class GrantDao extends AbstractEntityDao<Grant> {

	private static final String BASE_SQL =
			"SELECT ena_oid,\n" +
			"       ena_rol_oid,\n" +
			"       ena_ent_oid,\n" +
			"       ena_view,\n" +
			"       ena_viewasnuser,\n" +
			"       ena_viewasnwg,\n" +
			"       ena_modify,\n" +
			"       ena_modifyasnuser,\n" +
			"       ena_modifyasnwg,\n" +
			"       ena_status_from_oid,\n" +
			"       ena_status_to_oid,\n" +
			"       ena_new,\n" +
			"       ena_delete,\n" +
			"       ena_historyview,\n" +
			"       ena_historynew,\n" +
			"       ena_historymodify,\n" +
			"       ena_historymodifycreateduser,\n" +
			"       ena_historymodifycreatedwg,\n" +
			"       ena_historydelete,\n" +
			"       ena_historydeletecreatedwg,\n" +
			"       ena_historydeletecreateduser,\n" +
			"       ena_cod_oid,\n" +
			"       ena_lockseq,\n" +
			"       ena_tem_oid,\n" +
			"       ena_modifytemplate\n" +
			"FROM rep_entity_access";

	private GrantMapper mapper;

	public GrantDao(GrantMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<Grant> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<Grant>) mapper);
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		super.buildWhere(filter, sql, params);
		// todo добавить фильтрацию по роли
	}
}