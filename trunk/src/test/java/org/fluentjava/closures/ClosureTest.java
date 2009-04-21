package org.fluentjava.closures;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.Comparator;
import java.util.concurrent.Callable;

import org.fluentjava.FluentUtils;
import org.fluentjava.collections.FluentList;
import org.junit.Test;

public class ClosureTest {

	@Test
	public void testBasicCall() throws Exception {
		final FluentList<Integer> list = FluentUtils.list(1, 2, 3);
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
	
	@Test
	public void testClosureAsRunnable() throws Exception {
		Runnable runnable = runnableClosure().asRunnable();
		assertNotNull(runnable);
	}
	
	@Test
	public void testClosureAsCallable() throws Exception {
		Callable<String> callable = callableClosure().asCallable();
		assertTrue(callable.call().equals("Thet's the point!"));
	}

	private Closure callableClosure() {
		Closure c = new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				return "Thet's the point!";  
			}
		};
		return c;
	}

	private Closure runnableClosure() {
		Closure c = new Closure() {
			@Override
			public Object call(Object... args) throws Exception {
				return first(args);  
			}
		};
		return c;
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
