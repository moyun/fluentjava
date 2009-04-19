package org.fluentjava.collections;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.fluentjava.closures.Closure;
import org.fluentjava.closures.Predicate;
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
		assertTrue(list.exists(greaterThan4()));
	}
	
	@Test(expected = EnumeratingException.class)
	public void testClosureMustReturnBooleanWhenUsingExists() throws Exception {
		FluentList<Integer> list = Sequence.list(1, 2, 3, 4, 5);
		Closure badClosure = new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				return new ArrayList<Object>();
			}
		};
		list.exists(badClosure);
	}
	
	@Test
	public void testAllSatisfy() throws Exception {
		FluentList<Integer> list = Sequence.list(5, 6, 7);
		assertTrue(list.allSatisfy(greaterThan4()));
	}
	
	@Test
	public void testAnySatisfy() throws Exception {
		FluentList<Integer> list = Sequence.list(1, 2, 3);
		assertFalse(list.anySatisfy(greaterThan4()));
	}
	
	@Test
	public void testAnySatisfyWithOneSatifying() throws Exception {
		FluentList<Integer> list = Sequence.list(1, 2, 3, 30);
		assertTrue(list.anySatisfy(greaterThan4()));
	}
	
	@Test
	public void testNoneSatisfy() throws Exception {
		FluentList<Integer> list = Sequence.list(1, 2, 3);
		assertTrue(list.noneSatisfy(greaterThan4()));
	}
	
	@Test
	public void testSelect() throws Exception {
		FluentList<Integer> list = Sequence.list(1, 2, 3, 5, 6, 7);
		assertEquals(asList(5, 6, 7), list.select(greaterThan4()));
	}
	
	@Test
	public void testReject() throws Exception {
		FluentList<Integer> list = Sequence.list(1, 2, 3, 5, 6, 7);
		assertEquals(asList(1, 2, 3), list.reject(greaterThan4()));
	}
	
	@Test
	public void testDetectNonExistant() throws Exception {
		FluentList<Integer> list = Sequence.list(1, 2, 3);
		assertNull(list.detect(greaterThan4()));
	}
	
	@Test
	public void testDetect() throws Exception {
		FluentList<Integer> list = Sequence.list(1, 2, 3, 8, 9, 10);
		assertEquals(8, list.detect(greaterThan4()));
	}
	
	
	@Test
	public void testDetectIfNone() throws Exception {
		FluentList<Integer> list = Sequence.list(1, 2, 3);
		assertEquals(8, list.detectIfNone(greaterThan4(), 8));
	}
	
	@Test
	public void testCount() throws Exception {
		FluentList<Integer> list = Sequence.list(1, 2, 3, 5, 6, 7);
		assertEquals(3, list.count(greaterThan4()));
	}
	
	@Test
	public void testName() throws Exception {
		final FluentList<Integer> acummulated = new Sequence<Integer>();
		FluentList<Integer> list = Sequence.list(1, 2, 3, 4, 5, 6, 7);
		list.foreach(new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				Integer i = (Integer) args[0];
				if (i % 2 == 0) {
					acummulated.add(i);
				}
				return null;
			}
		});
		assertEquals(asList(2, 4, 6), acummulated);
	}
	
	@Test
	public void testMap() throws Exception {
		FluentList<Integer> list = Sequence.list(1, 2, 3);
		FluentList<Integer> ret = list.map(new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				Integer i = (Integer) args[0];
				return i * 3;
			}
		});
		assertEquals(asList(3, 6, 9), ret);	
	}

	private Predicate greaterThan4() {
		Predicate anyGreaterThan4 = new Predicate() {
			@Override
			public boolean eval(Object... args) throws Exception {
				Integer i = (Integer) args[0];
				return i > 4;
			}
		};
		return anyGreaterThan4;
	}

	
	private List<Integer> half(Integer... array) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (Integer integer : array) {
			ret.add(integer / 2);
		}
		return ret;
	}
	
	
	
}