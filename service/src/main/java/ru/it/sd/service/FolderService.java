package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.Folder;

/**
 * Сервис категорий
 *
 * @author NSychev
 * @since 22.12.2017
 */
@Service
public class FolderService extends AbstractCodeService<Folder> {

    @Override
    protected Long getTypeId(EntityType entityType) {
        return Folder.getTypeId();
    }
}
