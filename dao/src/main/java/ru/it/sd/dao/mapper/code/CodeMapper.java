package ru.it.sd.dao.mapper.code;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CodeMapper extends EntityRowMapper<BaseCode> {

    private final CodeDao codeDao;

    public CodeMapper(@Lazy CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    @Override
    public BaseCode mapRow(ResultSet rs, int rowNum) throws SQLException {
        BaseCode baseCode = super.mapRow(rs, rowNum);
        Long parentId = DBUtils.getLong(rs, "parentCode");
        if(parentId != null) {
            BaseCode parent = codeDao.read(parentId);
            baseCode.setParent(parent);
        }
        return baseCode;
    }
}