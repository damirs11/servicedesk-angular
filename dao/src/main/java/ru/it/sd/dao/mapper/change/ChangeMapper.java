package ru.it.sd.dao.mapper.change;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.ConfigurationItemDao;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.assignment.AssignmentMapper;
import ru.it.sd.dao.utils.DBUtils;
import ru.it.sd.model.Assignment;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.Change;
import ru.it.sd.model.ConfigurationItem;
import ru.it.sd.model.EntityCategory;
import ru.it.sd.model.EntityClassification;
import ru.it.sd.model.EntityClosureCode;
import ru.it.sd.model.EntityCode1;
import ru.it.sd.model.EntityPriority;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Маппер для изменений
 *
 * @author quadrix
 * @since 03.05.2017
 */
@Component
public class ChangeMapper extends EntityRowMapper<Change> {

    private final PersonDao personDao;
    private final CodeDao codeDao;
    private final AssignmentMapper assignmentMapper;
    private final ConfigurationItemDao configurationItemDao;

    public ChangeMapper(PersonDao personDao, CodeDao codeDao, AssignmentMapper assignmentMapper, ConfigurationItemDao configurationItemDao) {
        this.personDao = personDao;
        this.codeDao = codeDao;
        this.assignmentMapper = assignmentMapper;
        this.configurationItemDao = configurationItemDao;
    }

    @Override
    public Change mapRow(ResultSet rs, int rowNum) throws SQLException {
        Change change = super.mapRow(rs, 0);
        Assignment assignment = assignmentMapper.mapRow(rs, 0);
        change.setAssignment(assignment);

        Double duration = rs.getDouble("cha_planduration");
        if (duration != 0) {
            //Переводим дату из double в миллисекунды
            Double time = duration * 24 * 60 * 60 * 1000;
            Date date = new Date(time.longValue());
            change.setPlanDuration(date);
        }

        Long statusId = DBUtils.getLong(rs, "cha_sta_oid");
        if (statusId != null) {
            BaseCode code = codeDao.read(statusId);
            change.setStatus(code.convertTo(EntityStatus.class));
        }

        Long priorityId = DBUtils.getLong(rs, "cha_imp_oid");
        if (priorityId != null) {
            BaseCode code = codeDao.read(priorityId);
            change.setPriority(code.convertTo(EntityPriority.class));
        }

        Long initiatorId = DBUtils.getLong(rs, "cha_requestor_per_oid");
        if (initiatorId != null) {
            Person person = personDao.read(initiatorId);
            change.setInitiator(person);
        }

        Long managerId = DBUtils.getLong(rs, "cha_per_man_oid");
        if (managerId != null) {
            Person person = personDao.read(managerId);
            change.setManager(person);
        }

        Long categoryId = DBUtils.getLong(rs, "cha_cat_oid");
        if (categoryId != null) {
            BaseCode code = codeDao.read(categoryId);
            change.setCategory(code.convertTo(EntityCategory.class));
        }

        Long classificationId = DBUtils.getLong(rs, "cha_cla_oid");
        if (classificationId != null) {
            BaseCode code = codeDao.read(classificationId);
            change.setClassification(code.convertTo(EntityClassification.class));
        }

        Long closureCodeId = DBUtils.getLong(rs, "cha_closurecode");
        if (closureCodeId != null) {
            BaseCode code = codeDao.read(closureCodeId);
            change.setClosureCode(code.convertTo(EntityClosureCode.class));
        }

        Long folderId = DBUtils.getLong(rs, "cha_poo_oid");
        if (folderId != null) {
            BaseCode code = codeDao.read(folderId);
            change.setFolder(code.convertTo(Folder.class));
        }

        Long systemCodeId = DBUtils.getLong(rs, "ccu_changecode1");
        if (systemCodeId != null) {
            BaseCode code = codeDao.read(systemCodeId);
            change.setSystem(code.convertTo(EntityCode1.class));
        }


        Long configurationItemId = DBUtils.getLong(rs, "cha_cit_oid");
        if (configurationItemId != null) {
            ConfigurationItem configurationItem = configurationItemDao.read(configurationItemId);
            change.setConfigurationItem(configurationItem);
        }
        return change;
    }
}