package org.fluentjava;

import static org.fluentjava.FluentUtils.range;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FluencyDelegationTest {

	
	@Test
	public void testDelegationOfMy() throws Exception {
		FluencyDelegator delegator = new FluencyDelegator(new SimpleClass());		
		assertEquals("Hello", delegator.my("getHelloString").call());
	}
	
	@Test
	public void testB() throws Exception {
		System.out.println(range(3));		
	}
	
	private class SimpleClass {
		
		public String getHelloString() {
			return "Hello";
		}
		
	}
	
}
