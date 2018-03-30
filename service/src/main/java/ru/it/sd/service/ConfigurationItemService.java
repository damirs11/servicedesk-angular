package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.dao.ConfigurationItemDao;
import ru.it.sd.model.ConfigurationItem;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ConfigurationItemService extends CrudService<ConfigurationItem> {

    private ConfigurationItemDao dao;

    public ConfigurationItemService(ConfigurationItemDao dao){
        this.dao = dao;
    }
    @Override
    public ConfigurationItem create(ConfigurationItem entity) {
        return null;
    }

    @Override
    public ConfigurationItem update(ConfigurationItem entity) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public ConfigurationItem patch(ConfigurationItem entity, Set<String> fields) {
        return null;
    }

    @Override
    public ConfigurationItem read(long id) {
        return dao.read(id);
    }

    @Override
    public List<ConfigurationItem> list(Map<String, String> filter) {
        return dao.list(filter);
    }

    @Override
    public int count(Map<String, String> filter) {
        return dao.count(filter);
    }
}
