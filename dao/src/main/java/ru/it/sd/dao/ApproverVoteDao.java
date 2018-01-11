package ru.it.sd.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ApproverVoteMapper;
import ru.it.sd.exception.BadRequestException;
import ru.it.sd.model.ApproverVote;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Дао для работы с данными согласованием
 */
@Repository
public class ApproverVoteDao extends AbstractEntityDao<ApproverVote> {

	private ApproverVoteMapper mapper;

	/**
	 * Общий запрос получения данных о согласовании
	 */

	private static final String BASE_SQL =
			" SELECT\n" +
			"	apv.apv_oid, " +
			"	apv.apv_apt_oid, " +
			"	apv.apv_approved, " +
			"	apv_per_oid, " +
			"	apv.apv_reason "+
			" FROM itsm_approver_votes as apv";

	public ApproverVoteDao(ApproverVoteMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<ApproverVote> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<ApproverVote>) mapper);
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		if (Objects.isNull(filter) || filter.isEmpty() ||
				(!filter.containsKey("entityId") && !filter.containsKey("id"))) {
			throw new BadRequestException("При запросе согласований необходимо указать условия отбора записей (фильтр), например, по полю \"entityId\"");
		}
		super.buildWhere(filter, sql, params);
		//todo добавить проверку прав доступа в personId entityType entityId
	}
}