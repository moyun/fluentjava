package org.fluentjava;

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

public class FluencyTest extends Fluency {

	@Test
	public void testFluencyHasNoAttributes() {
		assertEquals(0, Fluency.class.getFields().length);
	}

	@Test
	public void testClosureFromPrivateMethod() throws Exception {
		FluentList<Integer> list = list(1, 2, 3);
		ArrayList<Integer> esperado = new ArrayList<Integer>();
		for (Integer integer : list) {
			esperado.add(squareOfInt(integer));
		}
		assertEquals(esperado, list.map(my("squareOfInt")));
		assertEquals(list(1, 4, 9), list.map(my("squareOfInt")));
	}

	@Test
	public void testClosureFrmomAString() throws Exception {
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

	private int squareOfInt(int i) {
		return i * i;
	}
}
