package org.fluentjava;

import static org.junit.Assert.assertEquals;

import org.fluentjava.closures.Closure;
import org.fluentjava.reflection.RuntimeReflectionException;
import org.junit.Test;

public class ClosuresTest {

	@Test
	public void testFieldGetter() throws Exception {
		BookWithPublicAttributes book = new BookWithPublicAttributes();
		book.name = "Extreme Programming Explained";
		Closure gettter = Closures.fieldGetter("name");
		String name = gettter.invoke(book);
		assertEquals("Extreme Programming Explained", name);
	}
	
	@Test(expected = RuntimeReflectionException.class)
	public void testFieldGetterCannotGetNonPublicFields() throws Exception {
		BookWithNoPublicAttributes book = new BookWithNoPublicAttributes();
		Closure gettter = Closures.fieldGetter("name");
		gettter.call(book);
	}
	
	@Test
	public void testGetterMethods() throws Exception {
		BookWithGetters book = new BookWithGetters();
		book.setName("Extreme Programming Explained");
		Closure gettter = Closures.getter("name");
		String name = gettter.invoke(book);
		assertEquals("Extreme Programming Explained", name);
	}

	@Test
	public void testAnyGetter() throws Exception {
		BookWithGetters bookWithGetters = new BookWithGetters();
		bookWithGetters.setName("Extreme Programming Explained");
		BookWithPublicAttributes book = new BookWithPublicAttributes();
		book.name = "Extreme Programming Explained";
		
		final Closure gettter = Closures.get("name");
		String nameFromGetter = gettter.invoke(bookWithGetters);
		assertEquals("Extreme Programming Explained", nameFromGetter);
		String name = gettter.invoke(book);
		assertEquals("Extreme Programming Explained", name);
	}
	

	@Test(expected = RuntimeReflectionException.class)
	public void testVoidGetterMethodsAreNotGetters() throws Exception {
		BookWithNoGettersOrFields book = new BookWithNoGettersOrFields();
		Closure closure = Closures.getter("name");
		closure.call(book);
	}
	
	
	protected static class BookWithPublicAttributes {
		public String name;
	}
	
	protected static class BookWithNoPublicAttributes {
		protected String name;
	}

	protected static class BookWithGetters {
		private String name;

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	
	protected static class BookWithNoGettersOrFields {
		public void getName() {
		}
	}
}
