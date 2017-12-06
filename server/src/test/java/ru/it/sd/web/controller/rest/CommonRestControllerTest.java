package ru.it.sd.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тесты для общего контроллера CRUD-интерфейса сущностей.
 * 
 * @author quadrix
 */
@Sql("CommonRestController.sql")
public class CommonRestControllerTest extends AbstractWebTest{
    private static final MediaType MEDIA_TYPE = MediaType.parseMediaType("application/json;charset=UTF-8");

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testRead() throws Exception {
        mockMvc.perform(get("/rest/entity/Person/1").accept(MEDIA_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MEDIA_TYPE))
                .andExpect(jsonPath("$.job").value("Менеджер"));
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(get("/rest/entity/Person/count").accept(MEDIA_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MEDIA_TYPE))
                .andExpect(jsonPath("$").value(4));
	}

	@Test
	public void testStatusRead() throws Exception {
		mockMvc.perform(get("/rest/entity/EntityStatus/3095134329").accept(MEDIA_TYPE))
				.andDo(MockMvcResultHandlers.log())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MEDIA_TYPE))
				.andExpect(jsonPath("$.name").value("Реализация"));


		mockMvc.perform(get("/rest/entity/EntityStatus?entityType=724041768").accept(MEDIA_TYPE))
				.andDo(MockMvcResultHandlers.log())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MEDIA_TYPE));
				//.andExpect(jsonPath("$.name").value("Реализация"));
	}
}