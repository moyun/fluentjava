package org.fluentjava.closures;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Comparator;

import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.Sequence;
import org.junit.Test;

public class ClosureTest {

	@Test
	public void testBasicCall() throws Exception {
		final FluentList<Integer> list = Sequence.list(1, 2, 3);
		Closure c = new Closure() {
			public Object call(Object... args) throws Exception {
				list.add(5);
				return null;
			}
		};
		c.call();
		assertEquals(asList(1, 2, 3, 5), list);
	}

	@Test
	public void testInvokeWithCast() throws Exception {
		Closure c = new Closure() {
			public Object call(Object... args) throws Exception {
				return 5;
			}
		};
		int ret = c.<Integer>invoke();
		assertEquals(5, ret);
	}

	@SuppressWarnings("unused")
	@Test(expected = ClassCastException.class)
	public void testInvokeWithCastFail() throws Exception {
		Closure c = new Closure() {
			public Object call(Object... args) throws Exception {
				return 5;
			}
		};
		boolean ret = c.<Boolean>invoke();
	}

	@Test
	public void testClosureToInterface() throws Exception {
		Comparator<String> comparator = comparatorClosure().toInteface(Comparator.class);
		assertTrue(comparator.compare("small", "very lage String") < 0);
	}

	public void testOnlyAdpatedInterfaceMethodsAreForwarded() throws Exception {
		Closure comparatorClosure = comparatorClosure();
		Comparator<String> comparator = comparatorClosure.toInteface(Comparator.class);
		assertEquals(comparatorClosure.hashCode(), comparator.hashCode());
	}

	private Closure comparatorClosure() {
		Closure c = new Closure() {
			public Object call(Object... args) throws Exception {
				String i = first(args);
				String j = second(args);
				return i.length() - j.length();
			}
		};
		return c;
	}
}