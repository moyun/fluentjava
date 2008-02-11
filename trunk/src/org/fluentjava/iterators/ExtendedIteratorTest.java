package org.fluentjava.iterators;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

//TODO: move this to the test source folder when there is more than one.
public class ExtendedIteratorTest {

	private ExtendedIterator<Integer> extendedIterator;

	@Before
	public void setUp() {
		extendedIterator = new ExtendedIteratorAdapter<Integer>(asList(1, 2, 3));
	}
	
	@Test
	public void testAdaptIterator() {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (Integer integer : extendedIterator) {
			ret.add(integer);
		}
		assertEquals(asList(1, 2, 3), ret);
	}
	
	@Test
	public void testForEachIterationStyleOnAExtendedIterator() throws Exception {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (Integer integer : extendedIterator.iterator().iterator()) {
			ret.add(integer);
		}
		assertEquals(asList(1, 2, 3), ret);
	}

}
