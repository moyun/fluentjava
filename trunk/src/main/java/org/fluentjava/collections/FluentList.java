package org.fluentjava.collections;

import java.util.List;

/**
 * List that has type safe toArray, and has a fluent interface on methods. Also, has more
 * convenient methods to include and remove elements.
 * 
 * All Enumerable methods are also implemented.
 * 
 * @param <E>
 * Type of elements
 */
public interface FluentList<E> extends List<E>, Enumerable<E> {

	/**
	 * Simliar to the method toArray, but recieves no args and is type safe. Also, the
	 * returned array is just a copy. Note that the class is necessary, as Gererics by
	 * themselves cannot solve this problem alone. For more info:
	 * http://www.ibm.com/developerworks/java/library/j-jtp01255.html
	 * 
	 * @return A copy of the list as an array.
	 */
	<T> T[] array(Class<T> clazz);

	/**
	 * Fluent method (returns self). Adds all elements on the list.
	 * 
	 * @param list
	 * The elements to be inserted.
	 * @return self
	 */
	FluentList<E> insert(E... list);

	/**
	 * Fluent method (returns self). Add one element
	 * 
	 * @param e
	 * The element to be inserted.
	 * @return
	 */
	FluentList<E> insert(E e);

	/**
	 * Fluent method (returns self). Adds all elements of the iterable.
	 * 
	 * @param iterable
	 * The elements returned by it are inserted.
	 * @return self
	 */
	FluentList<E> insert(Iterable<E> iterable);

	/**
	 * Fluent method (returns self). Removes all elements on the list.
	 * 
	 * @param list
	 * The elements to be removed.
	 * @return self
	 */
	FluentList<E> delete(E... list);

	/**
	 * Fluent method (returns self). Removes all elements on the iterable.
	 * 
	 * @param iterable
	 * The elements returned by it are removed.
	 * @return self
	 */
	FluentList<E> delete(Iterable<E> iterable);
}
