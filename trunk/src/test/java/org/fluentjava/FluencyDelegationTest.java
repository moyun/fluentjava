package org.fluentjava;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FluencyDelegationTest {

	@Test
	public void testDelegationOfMy() throws Exception {
		FluencyDelegator delegator = new FluencyDelegator(new SimpleClass());
		assertEquals("Hello", delegator.my("getHelloString").call());
	}

	private static class SimpleClass {
		public String getHelloString() {
			return "Hello";
		}

	}

}
