package ru.it.sd.web.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.it.sd.model.PagingRange;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;

/**
 * Тесты для тестирования контроллера работы с историей сущности
 * 
 * @author quadrix
 */
@Sql("HistoryRestController.sql")
public class HistoryRestControllerTest extends AbstractWebTest{

	private static final Logger logger = LoggerFactory.getLogger(HistoryRestControllerTest.class);

    private static final MediaType MEDIA_TYPE = MediaType.parseMediaType("application/json;charset=UTF-8");

	private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testList() throws Exception {
        MvcResult result = mockMvc.perform(
        		    get("/rest/service/history")
		            .accept(MEDIA_TYPE)
		            .param("entityType", "Change")
				    .param("entityId", "111"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MEDIA_TYPE))
		        .andReturn();
	    MockHttpServletResponse response = result.getResponse();
	    showResponseInfo(response);
	    assertEquals(response.getHeaderNames().size(), 1);
	    assertEquals(response.getHeaderNames().iterator().next(), "Content-Type");
	    List<Map> data = objectMapper.readValue(response.getContentAsString(), List.class);
	    assertEquals(data.size(), 2);
	    assertEquals(data.get(0).get("id"), 1);
	    assertEquals(data.get(1).get("id"), 2);
	}

	@Test
	public void testPagingList() throws Exception {
		MvcResult result = mockMvc.perform(
				get("/rest/service/history")
						.accept(MEDIA_TYPE)
						.param("entityType", "Change")
						.param("entityId", "111")
						.param(PagingRange.PAGING_PARAM_NAME, new PagingRange(2, 2).toString())
						)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MEDIA_TYPE))
				.andReturn();
		MockHttpServletResponse response = result.getResponse();
		showResponseInfo(response);

		assertEquals(response.getHeaderNames().size(), 2);
		assertEquals(response.getHeader("Content-Range"), "2-2/2");

		List<Map> data = objectMapper.readValue(response.getContentAsString(), List.class);
		assertEquals(data.size(), 1);
		assertEquals(data.get(0).get("id"), 2);
	}

	private void showResponseInfo(MockHttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("###");
		logger.info(response.getContentAsString());
		logger.info(response.getHeaderNames().toString());
		for (String header : response.getHeaderNames()) {
			logger.info("HEADER: " + header + "=" + response.getHeader(header));
		}
	}
}