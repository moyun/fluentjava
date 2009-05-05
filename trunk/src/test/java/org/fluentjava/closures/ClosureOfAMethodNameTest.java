package org.fluentjava.closures;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class ClosureOfAMethodNameTest {
	
	@Test
	public void testJustInvokingToString() throws Exception {
		List<Integer> list = asList(1, 2, 3);
		ClosureOfAMethodName cl = new ClosureOfAMethodName("toString");
		assertEquals("[1, 2, 3]", cl.invoke(list));
	}

	@Test
	public void testAClassWithOverloadedMethodsArgwise() throws Exception {
		OverloadedMethodsArgwise mock = new OverloadedMethodsArgwise();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("inc");
		Integer oneArgRet = closure.invoke(mock, 0);
		assertEquals(1, oneArgRet);

		Integer twoArgsRet = closure.invoke(mock, 0, 9);
		assertEquals(9, twoArgsRet);

	}

	@Test
	public void testSimpleVarArgs() throws Exception {
		SimpleVarArrgs mock = new SimpleVarArrgs();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("inc");
		Integer ret = closure.invoke(mock, 0, "one", "two");
		assertEquals(1, ret);
	}

	@Test
	public void testWithOverloadedVarARgs() throws Exception {
		OverloadedVarArgs mock = new OverloadedVarArgs();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("inc");
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
