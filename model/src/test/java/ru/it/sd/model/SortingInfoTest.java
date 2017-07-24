package ru.it.sd.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

/**
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a>
 * @since 01.06.2016 20:23
 */

public class SortingInfoTest {

	@Test
	private void testFromFilterParams() {
		final Map<String, String> filterParams = new HashMap<>();
		filterParams.put("name", "Вася");

		SortingInfo sortingInfo = SortingInfo.fromFilter(filterParams);
		assertNull(sortingInfo);

		char PS = SortingInfo.SORTING_PARAM_SEPARATOR;
		char DS = SortingInfo.SORTING_DIRECTION_SEPARATOR;

		String sort = "name" + DS + "asc";
		filterParams.put(SortingInfo.SORTING_PARAM_NAME, sort);
		Map<String, Boolean> columns = SortingInfo.fromFilter(filterParams).getColumns();
		assertEquals(columns.size(), 1);
		assertTrue(columns.containsKey("name"));
		assertTrue(columns.get("name"));

		filterParams.put(SortingInfo.SORTING_PARAM_NAME, sort += PS);
		columns = SortingInfo.fromFilter(filterParams).getColumns();
		assertEquals(columns.size(), 1);
		assertTrue(columns.containsKey("name"));
		assertTrue(columns.get("name"));

		// Дубликаты
		filterParams.put(SortingInfo.SORTING_PARAM_NAME, sort + sort);
		assertThrows(IllegalArgumentException.class, new Assert.ThrowingRunnable(){
			@Override
			public void run() {
				SortingInfo.fromFilter(filterParams).getColumns();
			}
		});

		filterParams.put(SortingInfo.SORTING_PARAM_NAME, "count" + DS + "desc" + PS + sort);
		columns = SortingInfo.fromFilter(filterParams).getColumns();
		assertEquals(columns.size(), 2);
		assertTrue(columns.containsKey("count"));
		assertFalse(columns.get("count"));
		assertTrue(columns.containsKey("name"));
		assertTrue(columns.get("name"));

		filterParams.put(SortingInfo.SORTING_PARAM_NAME, "count" + PS);
		assertThrows(IllegalArgumentException.class, new Assert.ThrowingRunnable(){
			@Override
			public void run() {
				SortingInfo.fromFilter(filterParams).getColumns();
			}
		});

		filterParams.put(SortingInfo.SORTING_PARAM_NAME, "count" + DS + "qqq");
		assertThrows(IllegalArgumentException.class, new Assert.ThrowingRunnable(){
			@Override
			public void run() {
				SortingInfo.fromFilter(filterParams).getColumns();
			}
		});
	}

	@Test
	private void testToString() {
		char PS = SortingInfo.SORTING_PARAM_SEPARATOR;
		char DS = SortingInfo.SORTING_DIRECTION_SEPARATOR;

		SortingInfo sort = new SortingInfo();
		Map<String, Boolean> columns = sort.getColumns();
		columns.put("name", true);
		columns.put("count", false);
		assertEquals(sort.toString(),
				"name" + DS + SortingInfo.SORTING_DIRECTION_ASC + PS +
				"count" + DS + SortingInfo.SORTING_DIRECTION_DESC);
	}
}