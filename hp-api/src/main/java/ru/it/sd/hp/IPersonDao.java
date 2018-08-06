package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.Person;

import java.util.Set;

/**
 * Сервис для работы с персонами
 *
 * Created by user on 27.07.2017.
 */
@Repository
public class IPersonDao implements HpCrudDao<Person, IPerson> {

    @Autowired
    private HpApi api;

    @Override
    public long create(Person entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IPerson read(long id) {
        try{
            return api.getSdClient().sd_session().getPersonHome().openPerson(id);
        } catch (Exception e){
            throw new ServiceException("Не найдена персона "+e.getMessage(),e);
        }

    }

    @Override
    public void update(Person entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}