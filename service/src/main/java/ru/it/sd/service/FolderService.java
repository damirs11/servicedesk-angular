package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.Folder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис категорий
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class FolderService extends ReadService<Folder> {

    private static final Logger LOG = LoggerFactory.getLogger(StatusService.class);

    private final CodeDao codeDao;

    @Autowired
    public FolderService(CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    @Override
    public Folder read(long id) {
        BaseCode code = codeDao.read(id);
        return code == null ? null : code.convertTo(Folder.class);
    }

    @Override
    public List<Folder> list(Map<String, String> filter) {
        Long subType = Folder.getTypeId();
        Map<String, String> subFilter = new HashMap<>();
        subFilter.put("subtype", subType.toString());
        if (filter.get("disabled") != null) {
            subFilter.put("disabled", filter.get("disabled"));
        }
        List<BaseCode> codes = codeDao.list(subFilter);
        List<Folder> result = new ArrayList<>();
        codes.forEach((code) ->
                result.add(code.convertTo(Folder.class))
        );
        return result;
    }

    @Override
    public int count(Map<String, String> filter) {
        Map<String, String> clearFilter = AbstractEntityDao.clearFilter(filter);
        return list(clearFilter).size();
    }
}
