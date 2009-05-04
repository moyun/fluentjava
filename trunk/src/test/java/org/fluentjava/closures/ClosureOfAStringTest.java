package org.fluentjava.closures;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClosureOfAStringTest {

	@Test
	public void testAClassWithOverloadedMethodsArgwise() throws Exception {
		OverloadedMethodsArgwise mock = new OverloadedMethodsArgwise();
		ClosureOfAString closure = new ClosureOfAString("inc");
		Integer oneArgRet = closure.invoke(mock, 0);
		assertEquals(1, oneArgRet);

		Integer twoArgsRet = closure.invoke(mock, 0, 9);
		assertEquals(9, twoArgsRet);

	}

	@Test
	public void testSimpleVarArgs() throws Exception {
		SimpleVarArrgs mock = new SimpleVarArrgs();
		ClosureOfAString closure = new ClosureOfAString("inc");
		Integer ret = closure.invoke(mock, 0, "one", "two");
		assertEquals(1, ret);
	}

	@Test
	public void testWithOverloadedVarARgs() throws Exception {
		OverloadedVarArgs mock = new OverloadedVarArgs();
		ClosureOfAString closure = new ClosureOfAString("inc");
		Integer oneArgRet = closure.invoke(mock, 0);
		assertEquals(1, oneArgRet);

		Integer twoArgsRet = closure.invoke(mock, 0, 9);
		assertEquals(9, twoArgsRet);
	}

	private static class OverloadedVarArgs {
		public int inc(Integer i, Integer offset, String... rest) {
			return i + offset;
		}

		public int inc(Integer i, String... rest) {
			return i + 1;
		}
	}

	private static class SimpleVarArrgs {
		public int inc(Integer i, String... rest) {
			return i + 1;
		}
	}

	private static class OverloadedMethodsArgwise {
		public int inc(Integer i) {
			return inc(i, 1);
		}

		public int inc(Integer i, Integer offset) {
			return i + offset;
		}
	}
}
