package org.fluentjava.collections;

import java.util.Set;

/**
 * {@link Set} that has type safe toArray, and has a fluent interface on methods.
 * 
 * Also, has more convenient methods to include and remove elements.
 * 
 * All {@link Enumerable} methods are also implemented.
 * 
 * @param <E>
 * Type of elements
 */
public interface FluentSet<E> extends Set<E>, Enumerable<E>, ExtendedCollection<E> {
	/**
	 * Similar to the method toArray, but receives no args and is type safe. Also, the
	 * returned array is just a copy. Note that the class is necessary, as Generics by
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
	FluentSet<E> insert(Iterable<? extends E> iterable);

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
	FluentSet<E> delete(Iterable<? extends E> iterable);

	/**
	 * Returns a new Fluent Set with the intersection of this and the set of elements from
	 * the iterable.
	 * 
	 * @param iterable
	 * @return
	 */
	FluentSet<E> intersect(Iterable<? extends E> iterable);

	/**
	 * Alias to {@link #intersect(Iterable)}.
	 * 
	 * @param iterable
	 * @return
	 */
	FluentSet<E> and(Iterable<? extends E> iterable);

	/**
	 * Returns a new Fluent Set with the union of this and the set of elements from the
	 * iterable.
	 * 
	 * @param iterable
	 * @return
	 */
	FluentSet<E> union(Iterable<? extends E> iterable);

	/**
	 * Alias to {@link #union(Iterable)}.
	 * 
	 * @param iterable
	 * @return
	 */
	FluentSet<E> or(Iterable<? extends E> iterable);

	/**
	 * Returns a new Fluent Set with the elements ofthis minus the ones from the set of
	 * elements from the iterable.
	 * 
	 * @param iterable
	 * @return
	 */
	FluentSet<E> difference(Iterable<? extends E> iterable);

	/**
	 * Alias to {@link #difference(Iterable)}.
	 * 
	 * @param iterable
	 * @return
	 */
	FluentSet<E> minus(Iterable<? extends E> iterable);

	/**
	 * Returns a new Fluent Set with the symmetric difference of this and the set of
	 * elements from the iterable. That is: the union of this and iterable minus the
	 * intersection of this and iterable. In other words: a set with what is exclusive to
	 * this and the set of elements of iterable.
	 * 
	 * @param iterable
	 * @return
	 */
	FluentSet<E> symmetricDifference(Iterable<? extends E> iterable);

	/**
	 * Alias to {@link #symmetricDifference(Iterable)}.
	 * 
	 * @param iterable
	 * @return
	 */
	FluentSet<E> xor(Iterable<? extends E> iterable);

}
