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
		ClosureOfAMethodName closure = new ClosureOfAMethodName("sum");
		Integer ret = closure.invoke(mock, 0, 1, 2, 3);
		assertEquals(6, ret);
	}
	
	@Test
	public void testPrimitiveVarArgs() throws Exception {
		PrimitiveVarArgs mock = new PrimitiveVarArgs();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("sum");
		Integer ret = closure.invoke(mock, 1, 2, 3);
		assertEquals(6, ret);
		
	}
	
	@Test
	public void testPrimitiveVarArgsAcceptsPrimitiveArrays() throws Exception {
		PrimitiveVarArgs mock = new PrimitiveVarArgs();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("sum");
		int[] ar = {1, 2, 3};
		Integer ret = closure.invoke(mock, ar);
		assertEquals(6, ret);
		
	}

	@Test
	public void testVarArgMethodsCanReceiveArrayInstead() throws Exception {
		SimpleVarArrgs mock = new SimpleVarArrgs();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("sum");
		Integer[] ar = {1, 2, 3};
		Integer ret = closure.invoke(mock, 0, ar);
		assertEquals(6, ret);
	}
	
	@Test
	public void testVarArgMethodsWithArrays() throws Exception {
		VarArrgsWithArrays mock = new VarArrgsWithArrays();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("sum");
		Integer[] ar = {1, 2, 3};
		Integer ret = closure.invoke(mock, 0, ar);
		assertEquals(6, ret);
		Integer retWithBothArraysAndVarargs = closure.invoke(mock, 0, ar, 4, 5);
		assertEquals(15, retWithBothArraysAndVarargs);
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
		OverloadedTypesDifferOnlyOnVarargs mock =
				new OverloadedTypesDifferOnlyOnVarargs();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("typeName");
		assertEquals("Integer...", closure.call(mock, "string", 1));
		assertEquals("String...", closure.call(mock, "string", "arg"));
	}

	@Test
	public void testPrimitiveTypes() throws Exception {
		SimplePrimitiveType mock = new SimplePrimitiveType();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("typeName");
		assertEquals("primitive int", closure.call(mock, 1));
	}

	@Test
	public void testWhenThereIsAmbiguityOneIsPickedArbitrarly() throws Exception {
		AmbiguousMethods mock = new AmbiguousMethods();
		ClosureOfAMethodName closure = new ClosureOfAMethodName("typeName");
		int primitive = 1;
		Integer integer = new Integer(primitive);
		assertEquals(closure.call(mock, primitive), closure.call(mock, integer));
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
		public int sum(Integer i, Integer... rest) {
			for (Integer integer : rest) {
				i += integer;
			}
			return i;
		}
	}

	private static class VarArrgsWithArrays {
		public int sum(Integer i, Integer[] messy, Integer... rest) {
			for (Integer integer : messy) {
				i += integer;
			}
			for (Integer integer : rest) {
				i += integer;
			}
			return i;
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

	private static class SimplePrimitiveType {
		public String typeName(int i) {
			return "primitive int";
		}
	}

	private static class PrimitiveVarArgs {
		public int sum(int... rest) {
			int i = 0;
			for (int integer : rest) {
				i += integer;
			}
			return i;
		}
	}

	private static class AmbiguousMethods {
		public String typeName(int i) {
			return "primitive int";
		}

		public String typeName(Integer i) {
			return "integer";
		}
	}

}
