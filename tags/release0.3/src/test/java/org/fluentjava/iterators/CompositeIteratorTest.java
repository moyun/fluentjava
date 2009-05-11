package org.fluentjava.iterators;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fluentjava.collections.Pair;
import org.junit.Test;

public class CompositeIteratorTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveIsUnsupported() {
		new CompositeIterator<Integer, String>(null, null).remove();
	}

	@Test
	public void testIteratesUpToSmallestCollection() throws Exception {
		List<String> l1 = asList("one", "two", "never reached", "too far", "way too far");
		List<String> l2 = asList("one", "three");
		CompositeIterator<String, String> iterator =
			new CompositeIterator<String, String>(new CountingIterator<String>(l1),
				new CountingIterator<String>(l2));
		ArrayList<String> ret = new ArrayList<String>();
		for (Pair<String, String> pair : iterator.iterator()) {
			if (pair.first.equals(pair.second)) {
				ret.add(pair.first);
			}
		}
		assertEquals(asList("one"), ret);
	}
}
