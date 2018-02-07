package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ru.it.sd.hp.IWorkgroupDao;
import ru.it.sd.model.*;
import ru.it.sd.service.utils.validation.Validator;

import java.util.Date;

public class ValidatorTest {
    @Autowired
    IWorkgroupDao iWorkgroupDao;

    private static final Logger LOG = LoggerFactory.getLogger(ValidatorTest.class);

    @Test
    private void test(){
        Change change = new Change();
        change.setNo(123L);
        change.setId(321L);
        change.setSubject("12");
        change.setDescription("sdasd");
        //change.setPriority(EntityPriority.MINOR);
        change.setManager(new Person());
        change.setWorkgroup(new Workgroup());
        change.setInitiator(new Person());
        //change.setClassification(EntityClassification.CHANGE_NEW_CI);
        change.setExecutor(new Person());
        //change.setCategory(EntityCategory.CHANGE_CAB);
        change.setDeadline(new Date());
        change.setClassification(new EntityClassification());
        change.setCategory(new EntityCategory());
        change.setPriority(new EntityPriority());
        Validator.validate(change);

    }
}
