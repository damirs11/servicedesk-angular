package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.dao.utils.FilterMap;
import ru.it.sd.model.GrantRule;
import ru.it.sd.model.Person;
import ru.it.sd.util.ResourceMessages;

import java.util.List;
import java.util.Map;

/**
 * Сервис для работы с информацией о людях
 *
 * @author quadrix
 * @since 13.05.2017
 */
@Service
public class PersonService extends ReadService<Person> {

    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

    private final PersonDao dao;
    private final AccessService accessService;

    public PersonService(PersonDao dao, AccessService accessService) {
        this.dao = dao;
        this.accessService = accessService;
    }

    @Override
    public Person read(long id) {
        Person person = dao.read(id);
        if (accessService.getEntityAccess(person).getLeft().getRead() == GrantRule.NONE) {
            throw new SecurityException(ResourceMessages.getMessage("error.service.access.denied"));
        }
        return person;
    }

    @Override
    public List<Person> list(Map<String, String> filter) {
        FilterMap filterMap = new FilterMap();
        filterMap.putAll(filter);
        accessService.applyReadFilter(filterMap, Person.class);
        return dao.list(filterMap, AbstractEntityDao.MapperMode.LIST);
    }

    @Override
    public int count(Map<String, String> filter) {
        return dao.count(filter);
    }
}
