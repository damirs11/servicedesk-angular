package ru.it.sd.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.AttributeAccessMapper;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.AttributeAccess;
import ru.it.sd.util.ResourceMessages;

import java.util.List;
import java.util.Map;

/**
 * Дао для работы с правами доступа атрибутов
 *
 * @author Nsychev
 * @since 30.12.2017
 */
@Repository
public class AttributeAccessDao extends AbstractEntityDao<AttributeAccess> {

	private static final String BASE_SQL =
			"SELECT \n" +
            "   raa.ata_oid," +
            "   raa.ata_modify," +
            "   raa.ata_ena_oid," +
            "   raa.ata_atr_oid\n" +
            "FROM\n" +
            "   rep_attribute_access raa\n"+
            "LEFT JOIN rep_entity_access rea ON rea.ena_oid = raa.ata_ena_oid\n"+
            "LEFT JOIN rep_roles_per_account rpa ON rpa.rpa_rol_oid = rea.ena_rol_oid\n";

	private AttributeAccessMapper mapper;

	public AttributeAccessDao(AttributeAccessMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<AttributeAccess> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (RowMapper<AttributeAccess>) mapper);
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
	    //todo Проверить почему при filter = null test list проходит
	    if(filter == null || filter.isEmpty() && !filter.containsKey("accountId")){
            throw new ServiceException(ResourceMessages.getMessage("error.dao.filter"));
        }
	    super.buildWhere(filter, sql, params);

        if(filter.containsKey("accountId")){
            params.addValue("accountId", filter.get("accountId"));
            sql.append(" AND rpa.rpa_acc_oid = :accountId");
        }
	}
}