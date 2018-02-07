package ru.it.sd.service;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.it.sd.dao.AttributeAccessDao;
import ru.it.sd.dao.CodeChildsDao;
import ru.it.sd.dao.GrantDao;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Тестирование сервиса для работы с правами доступа
 *
 * @author nsychev
 * @since 20.01.2018
 */
public class AccessServiceTest extends AbstractServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(AccessServiceTest.class);
	private AttributeAccessDao attributeAccessDao;
	private GrantDao grantDao;
	private SecurityService securityService;
	private CodeChildsDao codeChildsDao;
	private WorkgroupDao workgroupDao;
	private AccessService accessService;

	@BeforeClass(enabled = false)
	private void init() {
		attributeAccessDao = mock(AttributeAccessDao.class);
		grantDao = mock(GrantDao.class);
		securityService = mock(SecurityService.class);
		codeChildsDao = mock(CodeChildsDao.class);
		workgroupDao = mock(WorkgroupDao.class);
        accessService = new AccessService(attributeAccessDao, grantDao, securityService, codeChildsDao, workgroupDao);
		//Текущий пользователь
        when(securityService.getCurrentUser()).thenAnswer(new Answer<User>() {
            public User answer(InvocationOnMock invocation) throws Throwable {
                Person person = new Person();
                person.setId(12L);
                person.setFirstName("TestPersonName");
                User user = new User();
                user.setId(1L);
                user.setName("TESTUSER");
                user.setPerson(person);
                return user;
            }
        });
        //Права доступа к сущности
        when(grantDao.list(anyMap())).thenAnswer(new Answer<List<Grant>>(){
            //Права доступа для проверки ALWAYS
            public List<Grant> answer(InvocationOnMock invocation) throws Throwable {
                List<Grant> grantList = new ArrayList<>();
                Grant grant1 = new Grant();
                grant1.setId(1L);
                grant1.setUpdate(GrantRule.NONE);
                grant1.setRead(GrantRule.ALWAYS);
                grantList.add(grant1);

                Grant grant2 = new Grant();
                grant2.setId(2L);
                grant2.setUpdate(GrantRule.ALWAYS);
                grant2.setRead(GrantRule.ALWAYS);
                grantList.add(grant2);

                return grantList;
            }
        }).thenAnswer(new Answer<List<Grant>>(){
            //Права доступа для проверки исполнителя и статуса
            public List<Grant> answer(InvocationOnMock invocation) throws Throwable {
                List<Grant> grantList = new ArrayList<>();
                Grant grant1 = new Grant();

                EntityStatus entityStatusFrom = new EntityStatus();
                EntityStatus entityStatusTo = new EntityStatus();
                entityStatusFrom.setId(10000L);
                entityStatusFrom.setOrder(1);
                entityStatusTo.setId(10004L);
                entityStatusTo.setOrder(3);

                grant1.setId(1L);
                grant1.setStatusFrom(entityStatusFrom);
                grant1.setStatusTo(entityStatusTo);
                grant1.setUpdate(GrantRule.ALWAYS);
                grant1.setRead(GrantRule.NONE);
                grantList.add(grant1);

                Grant grant2 = new Grant();
                grant2.setId(2L);
                grant2.setUpdate(GrantRule.NONE);
                grant2.setRead(GrantRule.EXECUTOR);
                grantList.add(grant2);

                return grantList;
            }
        }).thenAnswer(new Answer<List<Grant>>(){
            //Права доступа для проверки рабочей группы и папки
            public List<Grant> answer(InvocationOnMock invocation) throws Throwable {
                List<Grant> grantList = new ArrayList<>();
                Grant grant1 = new Grant();
                grant1.setId(1L);
                grant1.setUpdate(GrantRule.WORKGROUP);
                grant1.setRead(GrantRule.NONE);
                grantList.add(grant1);


                Folder folder = new Folder();
                folder.setId(1L);
                Grant grant2 = new Grant();
                grant2.setId(2L);
                grant2.setFolder(folder);
                grant2.setUpdate(GrantRule.NONE);
                grant2.setRead(GrantRule.ALWAYS);
                grantList.add(grant2);

                return grantList;
            }
        });
        when(grantDao.count(anyMap())).thenReturn(2).thenReturn(3).thenReturn(2);
        //Права на атрибуты
		when(attributeAccessDao.list(anyMap())).thenAnswer(new Answer<List<AttributeAccess>>() {
            @Override
            public List<AttributeAccess> answer(InvocationOnMock invocation) throws Throwable {
                List<AttributeAccess> attributeAccessList = new ArrayList<>();

                AttributeAccess attributeAccess1 = new AttributeAccess();
                attributeAccess1.setId(1L);
                attributeAccess1.setAttributeId(724041784L);
                attributeAccess1.setModify(false);
                attributeAccessList.add(attributeAccess1);

                AttributeAccess attributeAccess2 = new AttributeAccess();
                attributeAccess2.setId(2L);
                attributeAccess2.setAttributeId(724041784L);
                attributeAccess2.setModify(true);
                attributeAccessList.add(attributeAccess2);

                return attributeAccessList;
            }
        });
		//Список дочерних папок
		when(codeChildsDao.list(anyMap())).thenAnswer(new Answer<List<BaseCode>>() {
            public List<BaseCode> answer(InvocationOnMock invocation) throws Throwable {
                List<BaseCode> baseCodes = new ArrayList<>();
                BaseCode baseCode1 = new BaseCode();
                baseCode1.setId(2L);
                baseCodes.add(baseCode1);
                BaseCode baseCode2 = new BaseCode();
                baseCode2.setId(3L);
                baseCodes.add(baseCode2);
                BaseCode baseCode3 = new BaseCode();
                baseCode3.setId(4L);
                baseCodes.add(baseCode3);
                return baseCodes;
            }
        });
		//Список рабочих групп в которые входит пользователь
        when(workgroupDao.list(anyMap())).thenAnswer(new Answer<List<Workgroup>>() {
            public List<Workgroup> answer(InvocationOnMock invocation) throws Throwable {
                List<Workgroup> workgroups = new ArrayList<>();
                Workgroup workgroup1 = new Workgroup();
                workgroup1.setId(100L);
                workgroups.add(workgroup1);

                Workgroup workgroup2 = new Workgroup();
                workgroup2.setId(200L);
                workgroups.add(workgroup2);

                Workgroup workgroup3 = new Workgroup();
                workgroup3.setId(300L);
                workgroups.add(workgroup3);

                return workgroups;
            }
        });
	}

	@Test(enabled = false)
	private void getAttributeAccess() {

       /* Map<String, Boolean> entitlement = new HashMap<>();
        entitlement.put("entity_read", true);
        entitlement.put("entity_update", false);
        entitlement.put("entity_create", false);
        entitlement.put("entity_delete", false);

        Map<String, Integer> map = accessService.getAttributeAccess(entitlement, EntityType.CHANGE);
        map.forEach((k,v)-> assertEquals((Integer)v,(Integer)1));

        entitlement.put("entity_read", true);
        entitlement.put("entity_update", true);
        entitlement.put("entity_create", false);
        entitlement.put("entity_delete", false);

        map = accessService.getAttributeAccess(entitlement, EntityType.CHANGE);
        map.forEach((k,v)-> assertEquals((Integer)v,(Integer)2));

        entitlement.put("entity_read", true);
        entitlement.put("entity_update", true);
        entitlement.put("entity_create", false);
        entitlement.put("entity_delete", false);

        map = accessService.getAttributeAccess(entitlement, EntityType.CHANGE);
        map.forEach((k,v)-> assertEquals((Integer)v,(Integer)1));*/

    }

    @Test(enabled = false)
    private void getEntitlement(){
       /* Change change = new Change();
        Folder folder = new Folder();
        Workgroup workgroup = new Workgroup();
        EntityStatus entityStatus = new EntityStatus();

        entityStatus.setId(1L);
        entityStatus.setOrder(2);

        folder.setId(2L);

        workgroup.setId(100L);

        change.setId(123L);
        change.setFolder(folder);
        change.setStatus(entityStatus);
        change.setWorkgroup(workgroup);
        change.setExecutor(securityService.getCurrentUser().getPerson());
        //Проверка always
        Map<String, Boolean> entitlement = accessService.getEntityAccess(change, EntityType.CHANGE);
        assertEquals((Boolean)entitlement.get("entity_read"), Boolean.TRUE);
        assertEquals((Boolean)entitlement.get("entity_update"), Boolean.TRUE);
        assertEquals((Boolean)entitlement.get("entity_create"), Boolean.FALSE);
        assertEquals((Boolean)entitlement.get("entity_delete"), Boolean.FALSE);
        //Проверка executor и статуса
        entitlement = accessService.getEntityAccess(change, EntityType.CHANGE);
        assertEquals((Boolean)entitlement.get("entity_read"), Boolean.TRUE);
        assertEquals((Boolean)entitlement.get("entity_update"), Boolean.TRUE);
        assertEquals((Boolean)entitlement.get("entity_create"), Boolean.FALSE);
        assertEquals((Boolean)entitlement.get("entity_delete"), Boolean.FALSE);

        //Проверка workgroup и folder
        entitlement = accessService.getEntityAccess(change, EntityType.CHANGE);
        assertEquals((Boolean)entitlement.get("entity_read"), Boolean.TRUE);
        assertEquals((Boolean)entitlement.get("entity_update"), Boolean.TRUE);
        assertEquals((Boolean)entitlement.get("entity_create"), Boolean.FALSE);
        assertEquals((Boolean)entitlement.get("entity_delete"), Boolean.FALSE);*/

    }
}