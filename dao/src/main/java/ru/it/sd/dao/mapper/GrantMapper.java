package ru.it.sd.dao.mapper;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.DBUtils;
import ru.it.sd.dao.RoleDao;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.Grant;
import ru.it.sd.model.GrantRule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Component
public class GrantMapper extends EntityRowMapper<Grant> {

	private RoleDao roleDao;

	public GrantMapper(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public Grant mapRow(ResultSet rs, int rowNum) throws SQLException {
		Grant grant = super.mapRow(rs, rowNum);
		Long id;
		if (Objects.nonNull(id = DBUtils.getLong(rs, "ena_rol_oid"))) {
			grant.setRole(roleDao.read(id));
		}
		if (Objects.nonNull(id = DBUtils.getLong(rs, "ena_ent_oid"))) {
			grant.setEntityType(EntityType.get(id));
		}
		grant.setCreate(GrantRule.get(
				rs.getBoolean("ena_new"),
				false,
				false));
		grant.setRead(GrantRule.get(
				rs.getBoolean("ena_view"),
				rs.getBoolean("ena_viewasnuser"),
				rs.getBoolean("ena_viewasnwg")));
		grant.setUpdate(GrantRule.get(
				rs.getBoolean("ena_modify"),
				rs.getBoolean("ena_modifyasnuser"),
				rs.getBoolean("ena_modifyasnwg")));
		grant.setDelete(GrantRule.get(
				rs.getBoolean("ena_delete"),
				false,
				false));
		if (Objects.nonNull(id = DBUtils.getLong(rs, "ena_status_from_oid"))) {
			grant.setStatusFrom(EntityStatus.get(id));
		}
		if (Objects.nonNull(id = DBUtils.getLong(rs, "ena_status_to_oid"))) {
			grant.setStatusTo(EntityStatus.get(id));
		}
		// Права доступа на историю сущности
		grant.setHistoryCreate(GrantRule.get(
				rs.getBoolean("ena_historynew"),
				false,
				false));
		grant.setHistoryRead(GrantRule.get(
				rs.getBoolean("ena_historyview"),
				false,
				false));
		grant.setHistoryUpdate(GrantRule.get(
				rs.getBoolean("ena_historymodify"),
				rs.getBoolean("ena_historymodifycreateduser"),
				rs.getBoolean("ena_historymodifycreatedwg")));
		grant.setHistoryDelete(GrantRule.get(
				rs.getBoolean("ena_historydelete"),
				rs.getBoolean("ena_historydeletecreateduser"),
				rs.getBoolean("ena_historydeletecreatedwg")));
		return grant;
	}
}