package org.fluentjava.collections;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.fluentjava.closures.Closure;
import org.junit.Test;

public class SequenceTest {

	@Test
	public void testCreatingSequence() throws Exception {
		Sequence<Integer> list = new Sequence<Integer>(1, 2, 3);
		assertEquals(asList(1, 2, 3), list);
	}
	
	@Test
	public void testFactoryMethods() throws Exception {
		assertEquals(asList(1, 2, 3), Sequence.list(1, 2, 3));
	}
	
	@Test
	public void testFluentStyle() throws Exception {
		Sequence<Integer> list = new Sequence<Integer>();
		list.insert(1)
			.insert(2, 3)
			.insert(4, 4, 4, 5)
			.insert(asList(6, 7, 8))
			.delete(4, 5, 6, 7, 8)
			.delete(110)
			.add(3);
		assertEquals(asList(1, 2, 3, 3), list);
	}
	
	@Test
	public void testCheckedToArrayIsUsefullForVariableArgumentsMethods() throws Exception {
		Sequence<Number> list = new Sequence<Number>(2, 4, 6);
		assertEquals(asList(1, 2, 3), half(list.array(Integer.class)));
	}
	
	@Test(expected = Exception.class)
	public void testCannotConvertEverything() throws Exception {
		FluentList<Integer> list = Sequence.list(2, 4, 6);
		list.array(String.class);
	}
	
	@Test
	public void testChangeOnInputArraysDoNotAffectSequence() throws Exception {
		Integer[] array = {1, 2, 3};
		List<Integer> asListResult = asList(array);
		FluentList<Integer> list = Sequence.list(array);
		array[0] = 10;
		assertEquals(asList(10, 2, 3), asListResult);
		assertEquals(asList(1, 2, 3), list);
	}

	@Test
	public void testExistsWithClosures() {
		FluentList<Integer> list = Sequence.list(1, 2, 3, 4, 5);
		Closure anyGreaterThan4 = new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				Integer i = (Integer) args[0];
				return i > 4;
			}
		};
		assertTrue(list.exists(anyGreaterThan4));
	}
	
	private List<Integer> half(Integer... array) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (Integer integer : array) {
			ret.add(integer / 2);
		}
		return ret;
	}
	
	
	
}
