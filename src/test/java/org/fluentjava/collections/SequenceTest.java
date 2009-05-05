package org.fluentjava.collections;

import static java.util.Arrays.asList;
import static org.fluentjava.FluentUtils.list;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fluentjava.FluentUtils;
import org.junit.Test;

public class SequenceTest {

	@Test
	public void testCreatingSequence() throws Exception {
		Sequence<Integer> list = new Sequence<Integer>(1, 2, 3);
		assertEquals(asList(1, 2, 3), list);
	}

	@Test
	public void testFactoryMethods() throws Exception {
		assertEquals(asList(1, 2, 3), list(1, 2, 3));
	}

	@Test
	public void testFluentStyle() throws Exception {
		Sequence<Integer> list = new Sequence<Integer>();
		list.insert(1).insert(2, 3).insert(4, 4, 4, 5).insert(asList(6, 7, 8)).delete(4, 5, 6, 7, 8).delete(
				110).add(3);
		assertEquals(asList(1, 2, 3, 3), list);
	}

	@Test
	public void testCheckedToArrayIsUsefullForVariableArgumentsMethods() throws Exception {
		Sequence<Number> list = new Sequence<Number>(2, 4, 6);
		assertEquals(asList(1, 2, 3), half(list.array(Integer.class)));
	}

	@Test(expected = Exception.class)
	public void testCannotConvertEverything() throws Exception {
		FluentList<Integer> list = list(2, 4, 6);
		list.array(String.class);
	}

	@Test
	public void testChangeOnInputArraysDoNotAffectSequence() throws Exception {
		Integer[] array = {1, 2, 3};
		List<Integer> asListResult = asList(array);
		FluentList<Integer> list = list(array);
		array[0] = 10;
		assertEquals(asList(10, 2, 3), asListResult);
		assertEquals(asList(1, 2, 3), list);
	}

	@Test
	public void testToListCreatesACopy() throws Exception {
		FluentList<Integer> list = list(1, 2, 3);
		FluentList<Integer> copy = list.toList();
		assertEquals(copy, list);
	}

	@Test
	public void testFlatten() throws Exception {
		FluentList<List<String>> list = list();
		list.insert(list("a", "b")).insert(asList("1", "2", "3"));
		assertEquals(asList("a", "b", "1", "2", "3"), list.flatten());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFlattenDoesNotAllowRecursiveReferences() throws Exception {
		FluentList<Object> a = list();
		FluentList<Object> b = list();
		a.insert(list("a", FluentUtils.alist(b)));
		b.insert(a);
		a.flatten();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSelfReferencesAreCaughtWhenYouFlatten() throws Exception {
		FluentList<Object> a = list();
		a.add(a);
		a.flatten();
	}

	@Test
	public void testFlattenDoesNotFlatPrimitiveArrays() throws Exception {
		Integer[] ar = new Integer[] {1, 2, 3};
		FluentList<Object> a = FluentUtils.alist();
		a.add(ar);
		assertEquals(a, a.flatten());
	}
	
	@Test
	public void testSequenceOfAlist() throws Exception {
		Sequence<Integer> list = new Sequence<Integer>(asList(1, 2, 3));
		assertEquals(asList(1, 2, 3), list);
	}
	
	private List<Integer> half(Integer... array) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (Integer integer : array) {
			ret.add(integer / 2);
		}
		return ret;
	}

	

}
