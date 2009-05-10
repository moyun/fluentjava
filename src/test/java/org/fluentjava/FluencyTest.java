package org.fluentjava;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fluentjava.collections.FluentList;
import org.junit.Test;

public class FluencyTest extends Fluency {

	@Test
	public void testFluencyHasNoAttributes() {
		assertEquals(0, Fluency.class.getFields().length);
	}

	@Test
	public void testClosureFromPrivateMethod() throws Exception {
		FluentList<Integer> list = list(1, 2, 3);
		List<Integer> esperado = new ArrayList<Integer>();
		for (Integer integer : list) {
			esperado.add(squareOfInt(integer));
		}
		assertEquals(esperado, list.map(my("squareOfInt")));
		assertEquals(list(1, 4, 9), list.map(my("squareOfInt")));
	}

	private int squareOfInt(int i) {
		return i * i;
	}
}
