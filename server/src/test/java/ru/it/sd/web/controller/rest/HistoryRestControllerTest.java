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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты для тестирования контроллера работы с историей сущности
 * 
 * @author quadrix
 */
@Sql("HistoryRestController.sql")
public class HistoryRestControllerTest extends AbstractWebTest{
    private static final MediaType MEDIA_TYPE = MediaType.parseMediaType("application/json;charset=UTF-8");

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(
        		    get("/rest/service/history")
		            .accept(MEDIA_TYPE)
		            .param("entityType", "Change")
				    .param("entityId", "111"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MEDIA_TYPE));
                //.andExpect(jsonPath("$").value(4));
	}
}