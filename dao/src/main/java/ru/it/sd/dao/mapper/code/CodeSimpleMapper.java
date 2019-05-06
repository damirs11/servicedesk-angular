package ru.it.sd.dao.mapper.code;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CodeSimpleMapper extends EntityRowMapper<BaseCode> {

    @Override
    public BaseCode mapRow(ResultSet rs, int rowNum) throws SQLException {
        BaseCode baseCode = super.mapRow(rs, rowNum);
        Long parentId = DBUtils.getLong(rs, "parentCode");
        if(parentId != null) {
            BaseCode parent = new BaseCode(parentId);
            baseCode.setParent(parent);
        }
        return baseCode;
    }
}