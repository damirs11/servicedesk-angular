package ru.it.sd.dao;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.it.sd.model.TestModel;
import java.sql.Timestamp;
import java.util.HashMap;


/**
 * @author quadrix
 * @since 05.05.2017
 */
public class FilterUtilsTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(FilterUtilsTest.class);
    MultiMap filterFields = new MultiValueMap();
    @BeforeTest
    private void initFields(){

        filterFields.put("test_table1","test_Integer");
        filterFields.put("test_table2","test_int");
        filterFields.put("test_table3","test_Long");
        filterFields.put("test_table4","test_long");
        filterFields.put("test_table5","test_Double");
        filterFields.put("test_table6","test_double");
        filterFields.put("test_table7","test_String");
        filterFields.put("test_table8","test_Boolean");
        filterFields.put("test_table9","test_boolean");
        filterFields.put("test_table10","test_Date");
        filterFields.put("test_table11","test_Organization");
        filterFields.put("test_table12","test_Folder");
        filterFields.put("test_table13","test_Person");
        //Добавить остальные сущности
    }
	@Test
	private void testEquals() {
		StringBuilder sql = new StringBuilder();


        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        HashMap<String, String> filter = new HashMap<>();

		filter.put("testInteger","1234");
        filter.put("testint","-1234");
        filter.put("testLong","123456789");
        filter.put("testlong","123456789");
        filter.put("testBoolean","true");
        filter.put("testboolean","false");
        filter.put("testDouble","1.45");
        filter.put("testdouble","0.45");
        filter.put("testString","testetestest");
        filter.put("testDate", "1045645465000");

        FilterUtils.createFilter(sql,mapSqlParameterSource,filter, filterFields, TestModel.class);

        Assert.assertEquals(mapSqlParameterSource.getValue("testInteger"),1234);
        Assert.assertEquals(mapSqlParameterSource.getValue("testint"),-1234);
        Assert.assertEquals(mapSqlParameterSource.getValue("testLong"),123456789L);
        Assert.assertEquals(mapSqlParameterSource.getValue("testlong"),123456789L);
        Assert.assertEquals(mapSqlParameterSource.getValue("testBoolean"),true);
        Assert.assertEquals(mapSqlParameterSource.getValue("testboolean"),false);
        Assert.assertEquals(mapSqlParameterSource.getValue("testDouble"),1.45);
        Assert.assertEquals(mapSqlParameterSource.getValue("testdouble"),0.45);
        Assert.assertEquals(mapSqlParameterSource.getValue("testString"),"testetestest");
        Assert.assertEquals(mapSqlParameterSource.getValue("testDate"), new Timestamp(1045645465000L));
		LOG.debug(sql.toString());


	}
    @Test
    private void testOtherModels() {
        StringBuilder sql = new StringBuilder();


        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        HashMap<String, String> filter = new HashMap<>();

        filter.put("testOrganization","1234");
        filter.put("testFolder","12345");
        filter.put("testPerson","123456");

        FilterUtils.createFilter(sql,mapSqlParameterSource,filter, filterFields, TestModel.class);

        Assert.assertEquals(mapSqlParameterSource.getValue("testOrganization"),1234L);
        Assert.assertEquals(mapSqlParameterSource.getValue("testFolder"),12345L);
        Assert.assertEquals(mapSqlParameterSource.getValue("testPerson"),123456L);
        LOG.debug(sql.toString());


    }
    @Test
    private void testList() {
        StringBuilder sql = new StringBuilder();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        HashMap<String, String> filter = new HashMap<>();
        filter.put("testdouble","1.5;1.6;1.7");

        FilterUtils.createFilter(sql,mapSqlParameterSource,filter, filterFields, TestModel.class);

        Assert.assertEquals(mapSqlParameterSource.getValue("testdouble0"),1.5);
        Assert.assertEquals(mapSqlParameterSource.getValue("testdouble1"),1.6);
        Assert.assertEquals(mapSqlParameterSource.getValue("testdouble2"),1.7);

        LOG.debug(sql.toString());

    }
    @Test
    private void testBetween() {
        StringBuilder sql = new StringBuilder();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        HashMap<String, String> filter = new HashMap<>();
        filter.put("testLong_between","111111:222222");

        FilterUtils.createFilter(sql,mapSqlParameterSource,filter, filterFields, TestModel.class);

        Assert.assertEquals(mapSqlParameterSource.getValue("testLong_from"),111111L);
        Assert.assertEquals(mapSqlParameterSource.getValue("testLong_to"),222222L);

        LOG.debug(sql.toString());


    }
    @Test
    private void testAfter() {
        StringBuilder sql = new StringBuilder();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        HashMap<String, String> filter = new HashMap<>();
        filter.put("testDate_after","1045645465000");

        FilterUtils.createFilter(sql,mapSqlParameterSource,filter, filterFields, TestModel.class);

        Assert.assertEquals(mapSqlParameterSource.getValue("testDate_after"),new Timestamp(1045645465000L));

        LOG.debug(sql.toString());
    }
    @Test
    private void testBefore() {
        StringBuilder sql = new StringBuilder();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        HashMap<String, String> filter = new HashMap<>();
        filter.put("testDate_before","1045645465000");

        FilterUtils.createFilter(sql,mapSqlParameterSource,filter, filterFields, TestModel.class);

        Assert.assertEquals(mapSqlParameterSource.getValue("testDate_before"),new Timestamp(1045645465000L));

        LOG.debug(sql.toString());
    }
    @Test
    private void testLike() {
        StringBuilder sql = new StringBuilder();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        HashMap<String, String> filter = new HashMap<>();
        filter.put("testString_like","asd");

        FilterUtils.createFilter(sql,mapSqlParameterSource,filter, filterFields, TestModel.class);

        Assert.assertEquals(mapSqlParameterSource.getValue("testString_like"), "asd");

        LOG.debug(sql.toString());
    }
}