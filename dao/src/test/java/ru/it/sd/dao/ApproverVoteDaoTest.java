package ru.it.sd.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.it.sd.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

@Sql("ApproverVoteDaoTest.sql")
public class ApproverVoteDaoTest extends AbstractDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(ApproverVoteDaoTest.class);

	@Autowired
	private ApproverVoteDao dao;

	@Test
	private void testRead() {
		ApproverVote approverVote = dao.read(8183L);
		assertNotNull(approverVote);
		assertEquals(approverVote.getId().longValue(), 8183L);
	}

	@Test
	private void testFilter() {
		Map<String, String> filter = new HashMap<>();
		filter.put("entityId", "266633");
		List<ApproverVote> approverVote = dao.list(filter);

		Assert.assertEquals(approverVote.size(), 2);

	}

}