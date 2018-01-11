package ru.it.sd.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author quadrix
 * @since 06.09.2017
 */
public class EnumJsonDeserializerTest {

	private static final Logger LOG = LoggerFactory.getLogger(EnumJsonDeserializerTest.class);

	private static final ObjectMapper objectMapper = new ObjectMapper()
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	@Test
	private void serializeTest() throws Exception {
		String s = objectMapper.writeValueAsString(EntityType.CHANGE);
		LOG.debug("serialize: " + s);
		EntityType category = objectMapper.readValue(s, EntityType.class);
		LOG.debug("deserialize: " + category.toString());
		assertEquals(category, EntityType.CHANGE);
	}
}
