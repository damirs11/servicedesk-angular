package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IOrganization;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.Organization;

/**
 * Created by user on 27.07.2017.
 */
@Repository
public class IOrganizationDao implements HpCrudDao<Organization, IOrganization> {

    @Autowired
    private HpApi api;

    @Override
    public long create(Organization entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IOrganization read(long id) {
        try{
            return api.getSdClient().sd_session().getOrganizationHome().openOrganization(id);
        }catch (Exception e){
            throw new ServiceException("Не найдена организация "+e.getMessage(),e);
        }

    }

    @Override
    public void update(Organization entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}