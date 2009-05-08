package org.fluentjava.collections;

import java.util.List;

/**
 * {@link List} that has type safe toArray, and has a fluent interface on methods. Also,
 * has more convenient methods to include and remove elements.
 * 
 * All {@link Enumerable} methods are also implemented.
 * 
 * @param <E>
 * Type of elements
 */
public interface FluentList<E> extends List<E>, Enumerable<E>, ExtendedCollection<E> {

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
	FluentList<E> insert(Iterable<? extends E> iterable);

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
	FluentList<E> delete(Iterable<? extends E> iterable);

	/**
	 * Returns a new list that is a one-dimensional flattening of this list (recursively).
	 * This means: for every element that is a list, extract its elements into the new
	 * array.
	 * 
	 * @return
	 */
	FluentList<Object> flatten();
}
