package org.fluentjava.collections;

import static java.util.Arrays.asList;
import static org.fluentjava.FluentUtils.map;
import static org.fluentjava.FluentUtils.pair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.fluentjava.FluentUtils;
import org.fluentjava.closures.Closure;
import org.fluentjava.closures.Predicate;
import org.junit.Test;

public class EnumeratorTest {

	@Test
	public void testExistsWithClosures() {
		Enumerable<Integer> list = list(1, 2, 3, 4, 5);
		assertTrue(list.exists(greaterThan(4)));
	}

	@Test(expected = EnumeratingException.class)
	public void testClosureMustReturnBooleanWhenUsingExists() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3, 4, 5);
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
		Enumerable<Integer> list = list(5, 6, 7);
		assertTrue(list.allSatisfy(greaterThan(4)));
	}

	@Test
	public void testAnySatisfy() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3);
		assertFalse(list.anySatisfy(greaterThan(4)));
	}

	@Test
	public void testAnySatisfyWithOneSatifying() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3, 30);
		assertTrue(list.anySatisfy(greaterThan(4)));
	}

	@Test
	public void testNoneSatisfy() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3);
		assertTrue(list.noneSatisfy(greaterThan(4)));
	}

	@Test
	public void testSelect() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3, 5, 6, 7);
		assertEquals(asList(5, 6, 7), list.select(greaterThan(4)));
	}

	@Test
	public void testReject() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3, 5, 6, 7);
		assertEquals(asList(1, 2, 3), list.reject(greaterThan(4)));
	}

	@Test
	public void testDetectNonExistant() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3);
		assertNull(list.detect(greaterThan(4)));
	}

	@Test
	public void testDetect() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3, 8, 9, 10);
		assertEquals(8, list.detect(greaterThan(4)));
	}

	@Test
	public void testDetectIfNone() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3);
		assertEquals(8, list.detectIfNone(greaterThan(4), 8));
	}

	@Test
	public void testCount() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3, 5, 6, 7);
		assertEquals(3, list.count(greaterThan(4)));
	}

	@Test
	public void testForEach() throws Exception {
		final FluentList<Integer> acummulated = new Sequence<Integer>();
		Enumerable<Integer> list = list(1, 2, 3, 4, 5, 6, 7);
		list.foreach(new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				Integer i = first(args);
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
		Enumerable<Integer> list = list(1, 2, 3);
		Enumerable<Integer> ret = list.map(new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				Integer i = first(args);
				return i * 3;
			}
		});
		assertEquals(asList(3, 6, 9), ret);
	}

	@Test
	public void testMapToDifferentTypes() throws Exception {
		Enumerable<String> list = list("I", "am", "one");
		Enumerable<Integer> ret = list.map(new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				String i = first(args);
				return i.length();
			}
		});
		assertEquals(asList(1, 2, 3), ret);
	}

	@Test
	public void testSortWithClosure() throws Exception {
		Enumerable<String> list = list("big", "ones", "always come", "before");
		Enumerable<String> ret = list.sort(stringLengthReversedComparatorClosure());
		assertEquals(asList("always come", "before", "ones", "big"), ret);
	}

	@Test
	public void testSortingWithNaturalOrder() throws Exception {
		Enumerable<Integer> list = list(3, 2, 1);
		assertEquals(asList(1, 2, 3), list.sort());
	}

	@Test
	public void testReduce() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3);
		Integer resultado = list.reduce(sumBlock());
		assertEquals(6, resultado);
	}

	@Test
	public void testReduceReturnsNullIfListIsEmpty() throws Exception {
		Enumerable<Integer> list = emptyEnum();
		assertNull(list.reduce(sumBlock()));
	}

	@Test
	public void testReduceWithStartingElement() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3);
		Integer resultado = list.reduce(10, sumBlock());
		assertEquals(16, resultado);
	}

	@Test
	public void testReducewithEmtpyListAndStartingEelment() throws Exception {
		Enumerable<Integer> list = emptyEnum();
		assertEquals(10, list.reduce(10, sumBlock()));
	}

	@Test
	public void testTake() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3, 4);
		assertEquals(asList(1, 2), list.take(2));
	}

	@Test
	public void testMaxWithoutClosure() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3, 4);
		assertEquals(4, list.max());
	}

	@Test
	public void testMaxReturnsNullOnEmptyLists() throws Exception {
		Enumerable<Object> list = list();
		assertEquals(null, list.max());
	}

	@Test
	public void testMaxWithClosures() throws Exception {
		Enumerable<String> list = list("big", "ones", "always come", "before");
		assertEquals("big", list.max(stringLengthReversedComparatorClosure()));
	}

	@Test
	public void testMinWithClosures() throws Exception {
		Enumerable<String> list = list("big", "ones", "always come", "before");
		assertEquals("always come", list.min(stringLengthReversedComparatorClosure()));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMaxWithKeysGivenByClosure() throws Exception {
		Enumerable<Pair<Integer, Integer>> list = list(pair(1, -1), pair(20, -20), pair(
				300, -300));
		assertEquals(pair(300, -300), list.maxBy(clousureThatGetsTheFirstFromPair()));
		assertEquals(pair(1, -1), list.maxBy(clousureThatGetsTheSecondFromPair()));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMinByTest() throws Exception {
		Enumerable<Pair<Integer, Integer>> list = list(pair(1, -1), pair(20, -20), pair(
				300, -300));
		assertEquals(pair(1, -1), list.minBy(clousureThatGetsTheFirstFromPair()));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSortBy() throws Exception {
		Enumerable<Pair<Integer, Integer>> list = list(pair(1, -1), pair(20, -20), pair(
				300, -300));
		FluentList<Pair<Integer, Integer>> ret = list.sortBy(clousureThatGetsTheSecondFromPair());
		assertEquals(FluentUtils.list(pair(300, -300), pair(20, -20), pair(1, -1)), ret);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToMap() throws Exception {
		Enumerable<Pair<Integer, Integer>> list = list(pair(1, -1), pair(20, -20), pair(
				300, -300));
		FluentMap<Integer, Integer> expected = map(pair(1, -1), pair(20, -20), pair(300,
				-300));
		assertEquals(expected, list.toMap());
	}

	@Test(expected = ClassCastException.class)
	public void testToMapExpectsGoesBadWhenThereIsOneNotEntry() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3);
		list.toMap();
	}

	@Test
	public void testMapWithKeys() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3);
		FluentList<Entry<Integer, Integer>> result = list.mapWithKeys(squareAnIntegerClosure());
		FluentList<Pair<Integer, Integer>> expected = FluentUtils.list();
		expected.insert(pair(1, 1)).insert(pair(2, 4)).insert(pair(3, 9));
		assertEquals(expected, result);
	}

	@Test
	public void testToMapBy() throws Exception {
		Enumerable<Integer> list = list(1, 2, 3);
		FluentMap<Integer, Integer> result = list.toMapBy(squareAnIntegerClosure());
		FluentMap<Integer, Integer> expected = map(pair(1, 1), pair(2, 4), pair(3, 9));
		assertEquals(expected, result);
	}

	private Closure squareAnIntegerClosure() {
		return new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				Integer i = first(args);
				return i * i;
			}
		};
	}

	private Closure clousureThatGetsTheFirstFromPair() {
		return new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				Pair<Integer, Integer> pair = first(args);
				return pair.first;
			}
		};
	}

	private Closure clousureThatGetsTheSecondFromPair() {
		return new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				Pair<Integer, Integer> pair = first(args);
				return pair.second;
			}
		};
	}

	private Predicate greaterThan(final int number) {
		Predicate anyGreaterThan = new Predicate() {
			@Override
			public boolean eval(Object... args) throws Exception {
				Integer i = first(args);
				return i > number;
			}
		};
		return anyGreaterThan;
	}

	private Closure sumBlock() {
		Closure sumBlock = new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				Integer x = first(args);
				Integer y = second(args);
				return x + y;
			}
		};
		return sumBlock;
	}

	private <T> Enumerable<T> emptyEnum() {
		return new Enumerator<T>(new ArrayList<T>());
	}

	private <T> Enumerable<T> list(T... args) {
		ArrayList<T> ret = new ArrayList<T>();
		ret.addAll(asList(args));
		return new Enumerator<T>(ret);
	}

	private Closure stringLengthReversedComparatorClosure() {
		Closure cl = new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				String i = first(args);
				String j = second(args);
				return j.length() - i.length();
			}
		};
		return cl;
	}
}
