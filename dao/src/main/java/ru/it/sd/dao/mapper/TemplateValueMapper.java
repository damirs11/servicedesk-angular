package ru.it.sd.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Маппер шаблонов
 *
 * @author nsychev
 * @since 19.02.2018
 */
@Component
public class TemplateValueMapper extends EntityRowMapper<TemplateValue> {

	@Override
	public TemplateValue mapRow(ResultSet rs, int rowNum) throws SQLException {
		TemplateValue templateValue = super.mapRow(rs, rowNum);
		Object value = rs.getObject("tva_value");
		if (value != null) {
			templateValue.setValue(value);
		}
		return templateValue;
	}
}