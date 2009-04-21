package org.fluentjava;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.fluentjava.collections.EnumeratingException;
import org.fluentjava.collections.FluentList;
import org.fluentjava.reflection.Mock;
import org.junit.Test;

public class FluencyTest extends Fluency {

	@Test
	public void testFluencyHasNoAttributes() {
		assertEquals(0, Fluency.class.getFields().length);
	}

	@Test
	public void testClosureFromPrivateMethod() throws Exception {
		FluentList<Integer> list = list(1, 2, 3);
		ArrayList<Integer> esperado = new ArrayList<Integer>();
		for (Integer integer : list) {
			esperado.add(squareOfInt(integer));
		}
		assertEquals(esperado, list.map(my("squareOfInt")));
		assertEquals(list(1, 4, 9), list.map(my("squareOfInt")));
	}
	
	@Test
	public void testClosureFrmomAString() throws Exception {
		FluentList<String> list = list("a", "to", "you");
		ArrayList<Integer> esperado = new ArrayList<Integer>();
		for (String string : list) {
			esperado.add(string.length());
		}
		assertEquals(esperado, list.map("length"));
		assertEquals(list(1, 2, 3), list.map("length"));
	}
	
	@Test(expected = EnumeratingException.class)
	public void testDontUseMethoOfAStringToLookOverNonPublicMethods() throws Exception {
		FluentList<Mock> list = list(new Mock("a mock"));
		list.map("getPrivateName");
	}


	private int squareOfInt(int i) {
		return i * i;
	}
}
