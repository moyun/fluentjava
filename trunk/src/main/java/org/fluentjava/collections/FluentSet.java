package org.fluentjava.collections;

import java.util.Set;

/**
 * Set that has type safe toArray, and has a fluent interface on methods.
 * 
 * Also, has more convenient methods to include and remove elements.
 * 
 * All Enumerable methods are also implemented.
 * 
 * @param <E>
 * Type of elements
 */
public interface FluentSet<E> extends Set<E>, Enumerable<E> {
	/**
	 * Similar to the method toArray, but receives no args and is type safe. Also, the
	 * returned array is just a copy. Note that the class is necessary, as Gererics by
	 * themselves cannot solve this problem alone. For more info:
	 * http://www.ibm.com/developerworks/java/library/j-jtp01255.html
	 * 
	 * @return A copy of the set as an array.
	 */
	<T> T[] array(Class<T> clazz);

	/**
	 * Fluent method (returns self). Adds all elements on the list.
	 * 
	 * @param set
	 * The elements to be inserted.
	 * @return self
	 */
	FluentSet<E> insert(E... set);

	/**
	 * Fluent method (returns self). Add one element
	 * 
	 * @param element
	 * The element to be inserted.
	 * @return
	 */
	FluentSet<E> insert(E element);

	/**
	 * Fluent method (returns self). Adds all elements of the iterable.
	 * 
	 * @param iterable
	 * The elements returned by it are inserted.
	 * @return self
	 */
	FluentSet<E> insert(Iterable<E> iterable);

	/**
	 * Fluent method (returns self). Removes all elements on the set.
	 * 
	 * @param set
	 * The elements to be removed.
	 * @return self
	 */
	FluentSet<E> delete(E... set);

	/**
	 * Fluent method (returns self). Removes all elements on the iterable.
	 * 
	 * @param iterable
	 * The elements returned by it are removed.
	 * @return self
	 */
	FluentSet<E> delete(Iterable<E> iterable);

}
