package ru.it.sd.service;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.it.sd.dao.AttributeAccessDao;
import ru.it.sd.model.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Тестирование сервиса для работы с файлами
 *
 * @author quadrix
 * @since 24.10.2017
 */
public class AccessServiceTest extends AbstractServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(AccessServiceTest.class);



	private AttributeAccessDao attributeAccessDao;

	@BeforeClass
	private void init() {
		attributeAccessDao = mock(AttributeAccessDao.class);
        AttributeAccess attributeAccess = new AttributeAccess();
		when(attributeAccessDao.list(anyMap())).thenAnswer(new Answer<List<AttributeAccess>>() {
            @Override
            public List<AttributeAccess> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                HashMap<String, String> map  = (HashMap) args[0];
                switch (map.get("attributeId")){
                    case "281483590631438": {
                        attributeAccess.setModify(false);
                    }break;
                    case "556335107": {
                        attributeAccess.setModify(true);
                    }break;
                    case "281478292766727": {
                        attributeAccess.setModify(false);
                    }break;
                    case "165888": {
                        return null;
                    }
                    case "281478611337217": {
                        attributeAccess.setModify(true);
                    }break;
                    case "724041784": {
                        attributeAccess.setModify(false);
                    }break;
                    case "556335112": {
                        attributeAccess.setModify(false);
                    }break;
                    case "738983997": {
                        return null;
                    }
                    default:{
                        attributeAccess.setModify(true);
                    }
                }
                List<AttributeAccess> attributeAccessList = new ArrayList<>();
                attributeAccessList.add(attributeAccess);
                return attributeAccessList;
            }
        });
	}

	@Test
	private void getAttributeAccess() {
	    AccessService accessService = new AccessService(attributeAccessDao);
	    Grant grant = new Grant();
	    grant.setId(123L);
	    grant.setEntityType(EntityType.CHANGE);
	    grant.setRead(GrantRule.ALWAYS);
	    grant.setUpdate(GrantRule.ALWAYS);
	    EntityStatus entityStatusCur = new EntityStatus();
	    entityStatusCur.setId(123L);
	    Change change = new Change();
	    change.setStatus(entityStatusCur);

        Map<String, Integer> map = accessService.getAttributeAccess(grant, change);

        for(Map.Entry<String, Integer> entry:map.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }

}