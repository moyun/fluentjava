package org.fluentjava.collections;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReversedComparatorTest {

	@Test
	public void testCompare() {
		CheckedComparableComparator<Integer> comp = new CheckedComparableComparator<Integer>();
		ReversedComparator<Integer> reversed = new ReversedComparator<Integer>(comp);
		assertTrue(comp.compare(1, 2) < 0);
		assertTrue(reversed.compare(2, 1) < 0);
	}

}
