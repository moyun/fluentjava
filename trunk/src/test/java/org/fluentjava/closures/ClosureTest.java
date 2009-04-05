package org.fluentjava.closures;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.Sequence;
import org.junit.Test;

public class ClosureTest {

	@Test
	public void testBasicCall() throws Exception {
		final FluentList<Integer> list = Sequence.list(1, 2, 3);
		Closure c = new Closure() {
			public Object call(Object...args) throws Exception {
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
		int ret = c.invoke();
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
		boolean ret = c.invoke();
	}
	
		
}
