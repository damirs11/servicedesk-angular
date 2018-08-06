package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IApprovalStatus;
import com.hp.itsm.api.interfaces.IWorkorderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;

import java.util.Set;

/**
 * Created by user on 27.07.2017.
 */
@Repository
public class IApprovalStatusDao {

    @Autowired
    private HpApi api;

    public long create(IWorkorderStatus entity) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public long create(Object entity) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public IApprovalStatus read(long id) {
        try{
            return api.getSdClient().sd_session().getApprovalStatusHome().openApprovalStatus(id);
        } catch (Exception e){
            throw new ServiceException("Не найден статус согласования "+e.getMessage(),e);
        }
    }
    //@Override
    public void update(Object entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}