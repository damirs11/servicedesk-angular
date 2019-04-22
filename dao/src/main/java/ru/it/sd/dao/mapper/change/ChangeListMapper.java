package ru.it.sd.dao.mapper.change;

import org.springframework.stereotype.Component;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.PersonDao;
import ru.it.sd.dao.mapper.EntityRowMapper;
import ru.it.sd.dao.mapper.assignment.AssignmentListMapper;
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
public class ChangeListMapper extends EntityRowMapper<Change> {

    private final PersonDao personDao;
    private final CodeDao codeDao;
    private final AssignmentListMapper assignmentMapper;

    public ChangeListMapper(PersonDao personDao, CodeDao codeDao, AssignmentListMapper assignmentMapper) {
        this.personDao = personDao;
        this.codeDao = codeDao;
        this.assignmentMapper = assignmentMapper;
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
            BaseCode code = codeDao.read(statusId, AbstractEntityDao.MapperMode.SIMPLEST);
            change.setStatus(code.convertTo(EntityStatus.class));
        }

        Long priorityId = DBUtils.getLong(rs, "cha_imp_oid");
        if (priorityId != null) {
            BaseCode code = codeDao.read(priorityId, AbstractEntityDao.MapperMode.SIMPLEST);
            change.setPriority(code.convertTo(EntityPriority.class));
        }

        Long initiatorId = DBUtils.getLong(rs, "cha_requestor_per_oid");
        if (initiatorId != null) {
            Person person = personDao.read(initiatorId, AbstractEntityDao.MapperMode.SIMPLEST);
            change.setInitiator(person);
        }

        Long managerId = DBUtils.getLong(rs, "cha_per_man_oid");
        if (managerId != null) {
            Person person = personDao.read(managerId, AbstractEntityDao.MapperMode.SIMPLEST);
            change.setManager(person);
        }

        Long categoryId = DBUtils.getLong(rs, "cha_cat_oid");
        if (categoryId != null) {
            BaseCode code = new BaseCode(categoryId);
            change.setCategory(code.convertTo(EntityCategory.class));
        }

        Long classificationId = DBUtils.getLong(rs, "cha_cla_oid");
        if (classificationId != null) {
            BaseCode code = new BaseCode(classificationId);
            change.setClassification(code.convertTo(EntityClassification.class));
        }

        Long closureCodeId = DBUtils.getLong(rs, "cha_closurecode");
        if (closureCodeId != null) {
            BaseCode code = new BaseCode(closureCodeId);
            change.setClosureCode(code.convertTo(EntityClosureCode.class));
        }

        Long folderId = DBUtils.getLong(rs, "cha_poo_oid");
        if (folderId != null) {
            BaseCode code = new BaseCode(folderId);
            change.setFolder(code.convertTo(Folder.class));
        }

        Long systemCodeId = DBUtils.getLong(rs, "ccu_changecode1");
        if (systemCodeId != null) {
            BaseCode code = new BaseCode(systemCodeId);
            change.setSystem(code.convertTo(EntityCode1.class));
        }


        Long configurationItemId = DBUtils.getLong(rs, "cha_cit_oid");
        if (configurationItemId != null) {
            ConfigurationItem configurationItem = new ConfigurationItem(configurationItemId);
            change.setConfigurationItem(configurationItem);
        }
        return change;
    }
}