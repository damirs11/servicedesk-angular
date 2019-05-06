package ru.it.sd.dao.mapper.code;

import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.BaseCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CodeListMapper extends EntityRowMapper<BaseCode> {

    private final CodeDao codeDao;

    public CodeListMapper(@Lazy CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    @Override
    public List<BaseCode> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, BaseCode> codeCache = new HashMap<>();
        List<BaseCode> baseCodes = new ArrayList<>();
        while (rs.next()) {
            BaseCode baseCode = super.mapRow(rs, 0);
            Long parentId = DBUtils.getLong(rs, "parentCode");
            if(parentId != null) {
                BaseCode parent = getBaseCode(codeCache, parentId);
                baseCode.setParent(parent);
            }
            baseCodes.add(baseCode);
        }
        return baseCodes;
    }

    private BaseCode getBaseCode(Map<Long, BaseCode> cache, Long id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        BaseCode code = codeDao.read(id, AbstractEntityDao.MapperMode.SIMPLEST);
        cache.put(id, code);
        return code;
    }
}