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
public class TemplateMapper extends EntityRowMapper<Template> {

	private final CodeDao codeDao;

	public TemplateMapper(CodeDao codeDao) {
		this.codeDao = codeDao;
	}

	@Override
	public List<Template> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Template> list = new ArrayList<>();
		while(rs.next()){
			Template template = super.mapRow(rs, 0);


			Long folderId = DBUtils.getLong(rs, "tem_rcd_oid");
			if(folderId != null) {
				BaseCode code = codeDao.read(folderId);
				template.setFolder(code.convertTo(Folder.class));
			}

			Long entityId = DBUtils.getLong(rs, "tem_ent_oid");
			if(entityId != null) {
				template.setEntityType(EntityType.get(entityId));
			}

			list.add(template);
		}
		return list;
	}
	@Override
	public Template mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Template template = super.mapRow(rs, rowNumber);

		Long folderId = DBUtils.getLong(rs, "tem_rcd_oid");
		if(folderId != null) {
			BaseCode code = codeDao.read(folderId);
			template.setFolder(code.convertTo(Folder.class));
		}

		Long entityId = DBUtils.getLong(rs, "tem_ent_oid");
		if(entityId != null) {
			template.setEntityType(EntityType.get(entityId));
		}

		return template;
	}
}