package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.it.sd.model.FileInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quadrix
 * @since 05.05.2017
 */
@Sql("FileInfoDaoTest.sql")
public class FileInfoDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(FileInfoDaoTest.class);

	@Autowired
	private FileInfoDao dao;

	@Test
	private void testRead() {
        FileInfo fileInfo = dao.read(123L);
        Assert.assertEquals(fileInfo.getEntityId(), (Long)321L);
    }

    @Test
    private void testList() {
        List<FileInfo> fileInfo = dao.list(null);
        Assert.assertEquals(fileInfo.size(), 4);


        Map<String,String> filter = new HashMap<>();
        filter.put("entityId","322");
        fileInfo = dao.list(filter);
        Assert.assertEquals(fileInfo.size(), 2);
    }


}