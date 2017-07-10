package ru.it.sd.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
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

		SortingInfo sortingInfo = SortingInfo.fromFilterParams(filterParams);
		assertNull(sortingInfo);

		char PS = SortingInfo.SORTING_PARAM_SEPARATOR;
		char DS = SortingInfo.SORTING_DIRECTION_SEPARATOR;

		String sort = "name" + DS + "asc";
		filterParams.put(SortingInfo.SORTING_PARAM_NAME, sort);
		List<Map.Entry<String, Boolean>> columns = SortingInfo.fromFilterParams(filterParams).getColumns();
		assertEquals(columns.size(), 1);
		assertEquals(columns.get(0).getKey(), "name");
		assertTrue(columns.get(0).getValue());

		filterParams.put(SortingInfo.SORTING_PARAM_NAME, sort += PS);
		columns = SortingInfo.fromFilterParams(filterParams).getColumns();
		assertEquals(columns.size(), 1);
		assertEquals(columns.get(0).getKey(), "name");
		assertTrue(columns.get(0).getValue());

		// Дубликаты
		filterParams.put(SortingInfo.SORTING_PARAM_NAME, sort + sort);
		assertThrows(IllegalArgumentException.class, new Assert.ThrowingRunnable(){
			@Override
			public void run() {
				SortingInfo.fromFilterParams(filterParams).getColumns();
			}
		});

		filterParams.put(SortingInfo.SORTING_PARAM_NAME, "count" + DS + "desc" + PS + sort);
		columns = SortingInfo.fromFilterParams(filterParams).getColumns();
		assertEquals(columns.size(), 2);
		assertEquals(columns.get(0).getKey(), "count");
		assertFalse(columns.get(0).getValue());
		assertEquals(columns.get(1).getKey(), "name");
		assertTrue(columns.get(1).getValue());

		filterParams.put(SortingInfo.SORTING_PARAM_NAME, "count" + PS);
		assertThrows(IllegalArgumentException.class, new Assert.ThrowingRunnable(){
			@Override
			public void run() {
				SortingInfo.fromFilterParams(filterParams).getColumns();
			}
		});

		filterParams.put(SortingInfo.SORTING_PARAM_NAME, "count" + DS + "qqq");
		assertThrows(IllegalArgumentException.class, new Assert.ThrowingRunnable(){
			@Override
			public void run() {
				SortingInfo.fromFilterParams(filterParams).getColumns();
			}
		});
	}

	@Test
	private void testToString() {
		char PS = SortingInfo.SORTING_PARAM_SEPARATOR;
		char DS = SortingInfo.SORTING_DIRECTION_SEPARATOR;

		SortingInfo sort = new SortingInfo();
		List<Map.Entry<String, Boolean>> columns = sort.getColumns();
		columns.add(new AbstractMap.SimpleEntry<>("name", true));
		columns.add(new AbstractMap.SimpleEntry<>("count", false));
		assertEquals(sort.toString(),
				"name" + DS + SortingInfo.SORTING_DIRECTION_ASC + PS +
				"count" + DS + SortingInfo.SORTING_DIRECTION_DESC);
	}
}