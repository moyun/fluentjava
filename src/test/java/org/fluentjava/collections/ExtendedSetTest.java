package org.fluentjava.collections;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtendedSetTest {

	@Test
	public void testCreatingSequence() throws Exception {
		ExtendedSet<Integer> set = new ExtendedSet<Integer>(1, 2, 2, 3);
		assertEquals(asSet(1, 2, 2, 3), set);
	}
	
	@Test
	public void testFactoryMethods() throws Exception {
		assertEquals(asSet(1, 2, 3, 3), ExtendedSet.set(1, 2, 3, 3));
	}

	@Test
	public void testFluentStyle() throws Exception {
		ExtendedSet<Integer> list = new ExtendedSet<Integer>();
		list.insert(1)
			.insert(2, 2, 3)
			.insert(4, 4, 4, 5, 5)
			.insert(asSet(6, 7, 8))
			.delete(4, 5, 6, 7, 8)
			.delete(110)
			.add(3);
		assertEquals(asSet(1, 2, 3), list);
	}
	
	@Test
	public void testCheckedToArrayIsUsefullForVariableArgumentsMethods() throws Exception {
		ExtendedSet<Number> set = new ExtendedSet<Number>(2, 4, 6);
		assertEquals(asSet(1, 2, 3), half(set.array(Integer.class)));
	}
	
	private Set<Integer> half(Integer...array) {
		Set<Integer> set = new HashSet<Integer>();
		for (Integer integer : array) {
			set.add(integer / 2);
		}
		return set;
	}

	@Test(expected = Exception.class)
	public void testCannotConvertEverything() throws Exception {
		FluentList<Integer> list = Sequence.list(2, 4, 6);
		list.array(String.class);
	}
	
	private <E> Set<E> asSet(E...element) {
		Set<E> set = new HashSet<E>();
		for (E e : element) {
			set.add(e);
		}
		return set;
	}
	
}
