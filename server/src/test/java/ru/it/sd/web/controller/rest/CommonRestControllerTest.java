package ru.it.sd.web.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.it.sd.model.*;
import ru.it.sd.service.CrudService;
import ru.it.sd.service.holder.CrudServiceHolder;
import ru.it.sd.service.holder.ReadServiceHolder;

import java.util.Date;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.testng.Assert.assertEquals;

/**
 * Тесты для общего контроллера CRUD-интерфейса сущностей.
 * 
 * @author quadrix
 */
@Sql("CommonRestController.sql")
public class CommonRestControllerTest extends AbstractWebTest{
    private static final MediaType MEDIA_TYPE = MediaType.parseMediaType("application/json;charset=UTF-8");

	@Autowired
	private ReadServiceHolder readServiceHolder;

    private MockMvc mockMvc;

    @BeforeMethod
    private void setup() {
	    CrudServiceHolder crudServiceHolder = mock(CrudServiceHolder.class);
	    CrudService<Change> crudService = mock(CrudService.class);
	    when(crudServiceHolder.findFor(anyString())).thenReturn(crudService);
	    // Подменяем сервисный метод update
	    Change mockChange = new Change();
	    mockChange.setId(123L);
	    when(crudService.update(isA(Change.class))).thenReturn(mockChange);

	    CommonRestController commonRestController = new CommonRestController(
			    readServiceHolder, crudServiceHolder, null
	    );
        this.mockMvc = MockMvcBuilders
		        .standaloneSetup(commonRestController)
		        .build();
    }

    @Test
    private void testRead() throws Exception {
        mockMvc.perform(get("/rest/entity/Person/1").accept(MEDIA_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MEDIA_TYPE))
                .andExpect(jsonPath("$.job").value("Менеджер"));
    }

    @Test
    private void testList() throws Exception {
        mockMvc.perform(get("/rest/entity/Person/count").accept(MEDIA_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MEDIA_TYPE))
                .andExpect(jsonPath("$").value(4));
	}

	@Test
	private void testStatusRead() throws Exception {
		mockMvc.perform(get("/rest/entity/EntityStatus/3095134329").accept(MEDIA_TYPE))
				.andDo(MockMvcResultHandlers.log())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MEDIA_TYPE))
				.andExpect(jsonPath("$.name").value("Реализация"));


		mockMvc.perform(get("/rest/entity/EntityStatus?entityTypeId=724041768").accept(MEDIA_TYPE))
				.andDo(MockMvcResultHandlers.log())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MEDIA_TYPE));
				//.andExpect(jsonPath("$.name").value("Реализация"));
	}

	@Test
	private void testUpdate() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Change change = new Change();
		change.setAssignment(new Assignment());
		change.setId(321L);
		change.setManager(new Person());
		change.getAssignment().setWorkgroup(new Workgroup());
		change.setSubject("qwerty");
		change.setInitiator(new Person());
		change.setDescription("asdfg");
		change.setClassification(new EntityClassification());
		change.setPriority(new EntityPriority());
		change.getAssignment().setExecutor(new Person());
		change.setCategory(new EntityCategory());
		change.setDeadline(new Date());

		MvcResult result = mockMvc.perform(
					put("/rest/entity/Change/321")
					.accept(MEDIA_TYPE)
					.content(objectMapper.writeValueAsString(change))
				)
				.andDo(MockMvcResultHandlers.log())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MEDIA_TYPE))
				.andReturn();

		MockHttpServletResponse response = result.getResponse();
		showResponseInfo(response);

		Change data = objectMapper.readValue(response.getContentAsString(), Change.class);
		assertEquals(data.getId().longValue(), 123L);
	}
}