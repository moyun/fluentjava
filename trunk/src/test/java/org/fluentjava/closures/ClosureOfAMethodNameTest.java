package org.fluentjava.closures;

import static java.util.Arrays.asList;
import static org.fluentjava.FluentUtils.call;
import static org.fluentjava.FluentUtils.map;
import static org.fluentjava.FluentUtils.pair;
import static org.fluentjava.FluentUtils.range;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.fluentjava.collections.FluentMap;
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
		Object mock = new OverloadedMethodsArgwise();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("inc");
		Integer oneArgRet = closure.invoke(mock, 0);
		assertEquals(1, oneArgRet);

		Integer twoArgsRet = closure.invoke(mock, 0, 9);
		assertEquals(9, twoArgsRet);

	}

	@Test
	public void testSimpleVarArgs() throws Exception {
		Object mock = new SimpleVarArrgs();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("inc");
		Integer ret = closure.invoke(mock, 0, "one", "two");
		assertEquals(1, ret);
	}

	@Test
	public void testWithOverloadedVarARgs() throws Exception {
		Object mock = new OverloadedVarArgs();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("inc");
		Integer oneArgRet = closure.invoke(mock, 0);
		assertEquals(1, oneArgRet);
		Integer twoArgsRet = closure.invoke(mock, 0, 9);
		assertEquals(9, twoArgsRet);
	}

	@Test
	public void testSupportingOverloadedTypes() throws Exception {
		Object mock = new OverloadedTypes();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("typeName");
		assertEquals("int", closure.call(mock, 1));
		assertEquals("string", closure.call(mock, "one"));
	}

	@Test
	public void testSupportingOverloadedTypesOnVarargs() throws Exception {
		Object mock = new VarArgsOverloadedTypes();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("typeName");
		assertEquals("int varargs", closure.call(mock, 1));
		assertEquals("string varargs", closure.call(mock, "one"));
	}
	
	@Test
	public void testVarArgsOnlyDiffereingOnArray() throws Exception {
		OverloadedTypesDifferOnlyOnVarargs mock = new OverloadedTypesDifferOnlyOnVarargs();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("typeName");
		assertEquals("Integer...", closure.call(mock, "string", 1));
//		assertEquals("String...", closure.call(mock, "string", "string"));
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

	private static class OverloadedTypes {
		public String typeName(String str) {
			return "string";
		}

		public String typeName(Integer i) {
			return "int";
		}
	}

	private static class VarArgsOverloadedTypes {
		public String typeName(String str, Object... args) {
			return "string varargs";
		}

		public String typeName(Integer i, String... str) {
			return "int varargs";
		}
	}

	private static class OverloadedTypesDifferOnlyOnVarargs {
		public String typeName(String str, Integer... args) {
			return "Integer...";
		}

		public String typeName(String str, String... args) {
			return "String...";
		}
	}
}
