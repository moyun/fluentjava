package org.fluentjava.collections;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.fluentjava.FluentUtils;
import org.junit.Test;

public class ExtendedSetTest {

	@Test
	public void testCreatingSequence() throws Exception {
		FluentSet<Integer> set = new ExtendedSet<Integer>(1, 2, 2, 3);
		assertEquals(asSet(1, 2, 2, 3), set);
	}
	
	@Test
	public void testFactoryMethods() throws Exception {
		assertEquals(asSet(1, 2, 3, 3), FluentUtils.set(1, 2, 3, 3));
	}

	@Test
	public void testFluentStyle() throws Exception {
		FluentSet<Integer> list = new ExtendedSet<Integer>();
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
		ExtendedSet<Number> set = new ExtendedSet<Number>(2, 4, 4, 6);
		assertEquals(asSet(1, 2, 3), half(set.array(Integer.class)));
	}

	@Test(expected = Exception.class)
	public void testCannotConvertEverything() throws Exception {
		FluentSet<Integer> set = FluentUtils.set(2, 4, 4, 6);
		set.array(String.class);
	}
	
//	@Test
//	public void testExistsWithClosures() {
//		FluentList<Integer> list = Sequence.list(1, 2, 3, 4, 5);
//		assertTrue(list.exists(greaterThan4()));
//	}
	
	private Set<Integer> half(Integer...array) {
		Set<Integer> set = new HashSet<Integer>();
		for (Integer integer : array) {
			set.add(integer / 2);
		}
		return set;
	}
	
	private <E> Set<E> asSet(E...element) {
		Set<E> set = new HashSet<E>();
		for (E e : element) {
			set.add(e);
		}
		return set;
	}
	
}
