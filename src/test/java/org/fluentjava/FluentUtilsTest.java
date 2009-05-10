package org.fluentjava;

import static org.fluentjava.FluentUtils.as;
import static org.fluentjava.FluentUtils.irange;
import static org.fluentjava.FluentUtils.list;
import static org.fluentjava.FluentUtils.listFromIterable;
import static org.fluentjava.FluentUtils.map;
import static org.fluentjava.FluentUtils.pair;
import static org.fluentjava.FluentUtils.range;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fluentjava.collections.EnumeratingException;
import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.FluentMap;
import org.fluentjava.reflection.Mock;
import org.junit.Test;

public class FluentUtilsTest {
	@Test
	public void testClosureFromAString() throws Exception {
		FluentList<String> list = list("a", "to", "you");
		ArrayList<Integer> esperado = new ArrayList<Integer>();
		for (String string : list) {
			esperado.add(string.length());
		}
		assertEquals(esperado, list.map("length"));
		assertEquals(list(1, 2, 3), list.map("length"));
	}

	@Test(expected = EnumeratingException.class)
	public void testDontUseMethoOfAStringToLookOverNonPublicMethods() throws Exception {
		FluentList<Mock> list = list(new Mock("a mock"));
		list.map("getPrivateName");
	}

	@Test
	public void testRange() throws Exception {
		assertEquals(list(0, 1, 2, 3), range(4));
		assertFalse(list(0, 1, 2, 3).equals(irange(4)));
	}

	@Test
	public void testIRange() throws Exception {
		assertEquals(list(0, 1, 2, 3), listFromIterable(irange(4)));
	}

	@Test
	public void testIfTheEndOfTheRangeIsGreaterThanItsStartReturnsEmpty()
			throws Exception {
		FluentList<Integer> list = range(4, 0);
		assertTrue(list.isEmpty());
	}

	@Test
	public void testIRangeWithStartAndStopParameters() throws Exception {
		assertEquals(list(1, 2, 3), listFromIterable(irange(1, 4)));
	}

	@Test
	public void testRangeWithStartAndStopParameters() throws Exception {
		assertEquals(list(1, 2, 3), range(1, 4));
	}

	@Test
	public void testFluentCast() throws Exception {
		List<Object> upCasted = list();
		FluentList<Object> fluentCasted = as(upCasted);
		assertEquals(upCasted, fluentCasted);
	}

	@Test
	public void testMap() throws Exception {
		FluentMap<Integer, Integer> map = map(pair(1, 1), pair(2, 2));
		Map<Integer, Integer> expected = new HashMap<Integer, Integer>();
		expected.put(1, 1);
		expected.put(2, 2);
		assertEquals(expected, map);
	}
}
