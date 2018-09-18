package ru.it.sd.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.TemplateMapper;
import ru.it.sd.dao.mapper.TemplateValueMapper;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.Template;
import ru.it.sd.model.TemplateValue;
import ru.it.sd.util.ResourceMessages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Дао для работы со значениями шаблона
 *
 * @author nsychev
 * @since 05.09.2018
 */
@Repository
public class TemplateValueDao extends AbstractEntityDao<TemplateValue> {

    String BASE_SQL =
            "SELECT " +
                    "	tva_oid,\n" +
                    "	tva_atr_oid,\n" +
                    "	tva_value,\n" +
                    "	tva_atr_aggregation_oid\n" +
                    "FROM rep_template_values tv\n";
    private TemplateValueMapper mapper;

    public TemplateValueDao(TemplateValueMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected StringBuilder getBaseSql() {
        return new StringBuilder(BASE_SQL);
    }

    @Override
    protected List<TemplateValue> executeQuery(String sql, SqlParameterSource params) {
        return namedJdbc.query(sql, params, mapper.asRowMapper());
    }

    @Override
    protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
       /* if (filter == null || filter.isEmpty() || !filter.containsKey("templateId")) {
            throw new ServiceException(ResourceMessages.getMessage("error.dao.filter"));
        }*/
        super.buildWhere(filter, sql, params);
        if (filter.containsKey("templateId")) {
            params.addValue("templateId", filter.get("templateId"));
            sql.append("	AND tva_tem_oid = :templateId\n");
        }
    }
}