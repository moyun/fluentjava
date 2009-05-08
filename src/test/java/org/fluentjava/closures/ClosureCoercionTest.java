package org.fluentjava.closures;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.concurrent.Callable;

import org.fluentjava.collections.Sequence;
import org.junit.Test;

public class ClosureCoercionTest {

	@Test
	public void testToClosureFromSingleAbstractMethod() throws Exception {
		final Sequence<String> result = new Sequence<String>();
		Runnable run = new Runnable() {
			public void run() {
				result.add("invoked");
			}
		};
		Closure closure = ClosureCoercion.toClosure(run);
		closure.call();
		assertEquals(asList("invoked"), result);
	}

	@Test
	public void testCoercingComparatorsWhichHasToStringOnItsInteface() throws Exception {
		Comparator<String> stringComparator = new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.length() - o2.length();
			}
		};
		Closure closure = ClosureCoercion.toClosure(stringComparator);
		Integer result = closure.invoke("one", "two");
		assertEquals(0, result);
	}

	@Test(expected = ClosureCoercionException.class)
	public void testNullDoesNotCoerceToClosure() throws Exception {
		ClosureCoercion.toClosure(null);
	}

	@Test
	public void testMethodsFromObjectClassAreIgnoredFromInterfaceCoercion()
			throws Exception {
		OverrideAllObjectMethods caller = new OverrideAllObjectMethods() {
			@Override
			public String call() {
				return "All others come from Object!";
			}
		};
		Closure closure = ClosureCoercion.toClosure(caller);
		assertEquals("All others come from Object!", closure.call());
	}


	@Test(expected = ClosureCoercionException.class)
	public void testClassesWithTwoInterfacesEvenWithOneMethodEachDoNotCoerce()
			throws Exception {
		ClosureCoercion.toClosure(new SwissArmyKnife());
	}

	private class SwissArmyKnife implements Runnable, Callable<String> {
		public void run() {
		}

		public String call() throws Exception {
			return null;
		}
	}

	private static interface OverrideAllObjectMethods {
		boolean equals(Object obj);

		int hashCode();

		String toString();

		String call();
	}
}
