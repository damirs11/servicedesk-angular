package ru.it.sd.hp;

import com.hp.itsm.api.interfaces.IAttachedItem;
import com.hp.itsm.api.interfaces.IAttachment;
import com.hp.itsm.api.interfaces.IEntityInfo;
import com.hp.itsm.api.interfaces.IWorkflow;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.FileInfoDao;
import ru.it.sd.model.FileInfo;

@Repository
public class IAttachedItemDao implements HpCrudDao<FileInfo, IAttachedItem>{
    @Autowired
    private HpApi api;
    @Autowired
    private FileInfoDao fileInfoDao;
    @Autowired
    private IWorkflowDao iWorkflowDao;
    @Override
    public long create(FileInfo entity) {
        SdClientBean sdClientBean = api.getSdClient();
        IWorkflow iWorkflow = iWorkflowDao.read(entity.getEntityId(), entity.getEntityType());
        IAttachment iAttachment = iWorkflow.getAttachment();

        IAttachedItem iAttachedItem = sdClientBean.sd_session().getAttachedItemHome().openNewAttachedItem();
        IEntityInfo iEntityInfo = sdClientBean.sd_session().getEntityInfoHome().openEntityInfo(entity.getEntityType().getId());
        iAttachedItem.setParentEntityType(iEntityInfo);
        iAttachedItem.setFilename(entity.getPath());
        iAttachedItem.setBaseName(entity.getName());
        iAttachment.addAttachedItem(iAttachedItem);
        iAttachment.transfer();
        return iAttachedItem.getOID();
    }

    @Override
    public IAttachedItem read(long id) {
        SdClientBean sdClientBean = api.getSdClient();
        IAttachedItem iAttachedItem = sdClientBean.sd_session().getAttachedItemHome().openAttachedItem(id);
        return iAttachedItem;
    }

    @Override
    public void update(FileInfo entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        SdClientBean sdClientBean = api.getSdClient();
        IAttachedItem iAttachedItem = read(id);
        iAttachedItem.delete();
    }
}
