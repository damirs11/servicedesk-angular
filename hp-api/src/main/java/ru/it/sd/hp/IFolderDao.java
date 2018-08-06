package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.Folder;

import java.util.Set;

/**
 * Created by nsyhev 29.09.2017
 */
@Repository
public class IFolderDao implements HpCrudDao<Folder, IFolder> {

    @Autowired
    private HpApi api;

    @Override
    public long create(Folder entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IFolder read(long id) {
        try{
            return api.getSdClient().sd_session().getFolderHome().openFolder(id);
        }catch (Exception e){
            throw new ServiceException("Не найден", e);
        }
    }

    @Override
    public void update(Folder entity, Set<String> fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}