package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.dao.OrganizationDao;
import ru.it.sd.model.Organization;

import java.util.List;
import java.util.Map;

/**
 * Сервис организаций
 *
 * @author quadrix
 * @since 01.05.2019
 */
@Service
public class OrganizationService extends ReadService<Organization> {

    private final OrganizationDao dao;

    public OrganizationService(OrganizationDao dao) {
        this.dao = dao;
    }

    @Override
    public Organization read(long id) {
        return dao.read(id);
    }

    @Override
    public List<Organization> list(Map<String, String> filter) {
        return dao.list(filter);
    }

    @Override
    public int count(Map<String, String> filter) {
        return dao.count(filter);
    }

}
