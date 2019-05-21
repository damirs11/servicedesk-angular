package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.dao.utils.FilterMap;
import ru.it.sd.model.GrantRule;
import ru.it.sd.model.Workgroup;
import ru.it.sd.util.ResourceMessages;

import java.util.List;
import java.util.Map;

/**
 * Сервис рабочих групп
 *
 * @author quadrix
 * @since 15.08.2017
 */
@Service
public class WorkgroupService extends ReadService<Workgroup> {

    private static final Logger LOG = LoggerFactory.getLogger(WorkgroupService.class);

    private final AccessService accessService;
    private final WorkgroupDao dao;

    public WorkgroupService(AccessService accessService, WorkgroupDao dao) {
        this.accessService = accessService;
        this.dao = dao;
    }

    @Override
    public Workgroup read(long id) {
        Workgroup workgroup = dao.read(id, AbstractEntityDao.MapperMode.LIST);
        if (accessService.getEntityAccess(workgroup).getLeft().getRead() == GrantRule.NONE) {
            throw new SecurityException(ResourceMessages.getMessage("error.service.access.denied"));
        }
        return workgroup;
    }

    @Override
    public List<Workgroup> list(Map<String, String> filter) {
        FilterMap filterMap = new FilterMap();
        filterMap.putAll(filter);
        accessService.applyReadFilter(filterMap, Workgroup.class);
        return dao.list(filter);
    }

    @Override
    public int count(Map<String, String> filter) {
        return dao.count(filter);
    }

}
