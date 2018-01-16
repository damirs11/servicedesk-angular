package ru.it.sd.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AttributeAccessDao;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;
import ru.it.sd.model.AttributeAccess;
import ru.it.sd.model.Change;
import ru.it.sd.model.Grant;
import ru.it.sd.model.GrantRule;
import ru.it.sd.util.EntityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccessService {

    private AttributeAccessDao attributeAccessDao;

    public AccessService(AttributeAccessDao attributeAccessDao){
        this.attributeAccessDao = attributeAccessDao;
    }

    public Map<String, Integer> getAttributeAccess(Grant grant, Change change){
        Map<String,Integer> result = new HashMap<>();
        Class clazz = EntityUtils.getEntityClass(grant.getEntityType().getAlias());
        Map<String, FieldMetaData> fieldMetaDataMap = MetaUtils.getFieldsMetaData(clazz);
        for(FieldMetaData fmd: fieldMetaDataMap.values()){
            if(fmd.getAttribute() != Long.MIN_VALUE){
                Map<String, String> filter = new HashMap<>();
                filter.put("grant",grant.getId().toString());
                filter.put("attributeId", String.valueOf(fmd.getAttribute()));
                List<AttributeAccess> attributeAccessList = attributeAccessDao.list(filter);
                //todo Добавить поле ordering в сущность basecode
                //todo определить входит ли текущий статус сущности в grant.statusFrom - grant.statusTo
                //todo опредлить относится ли текущая папка сущности к grant.code
                if(grant.getRead() != GrantRule.NONE && grant.getUpdate() == GrantRule.NONE){
                    if(attributeAccessList != null){
                        if(attributeAccessList.get(0).getModify()){
                            result.put(fmd.getName(), 0);
                        } else if(!attributeAccessList.get(0).getModify()){
                            result.put(fmd.getName(), 1);
                        }
                    }
                }

                if(grant.getRead() != GrantRule.NONE && grant.getUpdate() != GrantRule.NONE){
                    if(attributeAccessList == null){
                        result.put(fmd.getName(), 2);
                    }else if(!attributeAccessList.get(0).getModify()){
                        result.put(fmd.getName(), 1);
                    }else{
                        result.put(fmd.getName(), 0);
                    }
                }
            }
        }
        return result;
    }
}
