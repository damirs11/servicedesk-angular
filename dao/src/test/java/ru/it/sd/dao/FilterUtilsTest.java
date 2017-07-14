package ru.it.sd.dao;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.it.sd.model.ConfigurationItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author quadrix
 * @since 05.05.2017
 */
public class FilterUtilsTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(FilterUtilsTest.class);



	@Test
	private void testFields() {
		StringBuilder sql = new StringBuilder();

        MultiMap filterFields = new MultiValueMap();
        filterFields.put("ITEM","CIT_OID");
        filterFields.put("ITEM","CIT_IPADDRESS");
        filterFields.put("CUSTOM","CCF_CITEXT1");
        filterFields.put("ITEM","CIT_ATTACHMENT_EXISTS");
        filterFields.put("ITEM","CIT_PRICE");
        filterFields.put("ITEM","CIT_PRICE2");
        filterFields.put("ITEM","CIT_PURCHASEDATE");
        filterFields.put("ITEM","CIT_ID");
        filterFields.put("ITEM","CIT_OWNER_PER_OID");
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

		HashMap<String, String> filter = new HashMap<>();
		filter.put("id","1234");
		filter.put("ip","17.28.13.12");
		filter.put("price","56.5");
		filter.put("attachmentExists", "true");
        filter.put("address", "test address");
        filter.put("purchaseDate", "1045645465000");
        filter.put("no", "123dsad");
        filter.put("owner", "1");
		FilterUtils.createFilter(sql,mapSqlParameterSource,filter, filterFields, ConfigurationItem.class);
		System.out.println(sql);
        System.out.println(mapSqlParameterSource.getValues());
        Assert.assertNotNull(mapSqlParameterSource.getValue("id"));
        Assert.assertNotNull(mapSqlParameterSource.getValue("ip"));
        Assert.assertNotNull(mapSqlParameterSource.getValue("price"));
        Assert.assertNotNull(mapSqlParameterSource.getValue("address"));
        try{
            mapSqlParameterSource.getValue("no");
            Assert.fail();
        }
        catch(IllegalArgumentException e){
        }


	}
}