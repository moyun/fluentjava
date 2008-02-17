package org.fluentjava.iterators;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class CountingIteratorTest {

	private CountingIterator<Integer> extendedIterator;
	private ArrayList<Integer> ret;

	@Before
	public void setUp() {
		extendedIterator = new CountingIterator<Integer>(1, 2, 3);
		ret = new ArrayList<Integer>();
	}
	
	@Test
	public void testAdaptIterator() {
		for (Integer integer : extendedIterator) {
			ret.add(integer);
		}
		assertEquals(asList(1, 2, 3), ret);
	}
	
	@Test
	public void testForEachIterationStyleOnAExtendedIterator() throws Exception {
		for (Integer integer : extendedIterator.iterator().iterator()) {
			ret.add(integer);
		}
		assertEquals(asList(1, 2, 3), ret);
	}
	
	@Test
	public void testSkippingSecondElement() throws Exception {
		for (Integer integer : extendedIterator) {
			if (extendedIterator.iterationNumber() == 2) {
				continue;
			}
			ret.add(integer);
			
		}
		assertEquals(asList(1, 3), ret);
	}
	
	@Test
	public void testIterationNumberIsZeroAtTheStart() throws Exception {
		assertEquals(0, extendedIterator.iterationNumber());
		
	}
	
	@Test
	public void testExtendedIteratorsAreStillNotReusable() throws Exception {
		for (Integer integer : extendedIterator) {
			ret.add(integer);
		}
		assertEquals(3, extendedIterator.iterationNumber());
		assertEquals(false, extendedIterator.hasNext());
	}

}
