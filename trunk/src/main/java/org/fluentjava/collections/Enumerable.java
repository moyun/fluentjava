package org.fluentjava.collections;

import org.fluentjava.iterators.ExtendedIterable;

import com.sun.org.apache.bcel.internal.generic.Select;

/**
 * Implements a enumeration protocol. A class that implement this interface allow its
 * elements to be operated on with closures. All exceptions caught while iterating are
 * wrapped around the Runtime Exception {@link EnumeratingException}.
 * 
 * @param <E>
 * Type of Elements.
 */
public interface Enumerable<E> extends ExtendedIterable<E> {
	/**
	 * Checks if there exists an object such that the closure returns true. The closure
	 * must only return boolean values.
	 * 
	 * @param closure
	 * Predicate
	 * @return The existence
	 * @throws EnumeratingException
	 * In case anything happens while enumerating.
	 */
	boolean exists(Object closure) throws EnumeratingException;

	/**
	 * Alias to {@link #exists(Object)}.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	boolean anySatisfy(Object closure) throws EnumeratingException;

	/**
	 * Checks if all objects are such that the closure returns true. The closure must only
	 * return boolean values.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	boolean allSatisfy(Object closure) throws EnumeratingException;

	/**
	 * Checks that none of objects are such that the closure returns true. The closure
	 * must only return boolean values.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	boolean noneSatisfy(Object closure) throws EnumeratingException;

	/**
	 * Count how many objects are such that the closure returns true. The closure must
	 * only return boolean values.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	int count(Object closure) throws EnumeratingException;

	/**
	 * Filter returning a FluentList with the elements such that the closure returns true.
	 * The closure must only return boolean values.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	FluentList<E> select(Object closure) throws EnumeratingException;

	/**
	 * Alias to {@link Select}.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	FluentList<E> findAll(Object closure) throws EnumeratingException;

	/**
	 * Filter returning a FluentList with the elements such but the ones that the closure
	 * returns false. The closure must only return boolean values.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	FluentList<E> reject(Object closure) throws EnumeratingException;

	/**
	 * Returns the first element such hat the closure returns true. The closure must only
	 * return boolean values. Returns null if no such element is found.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	E detect(Object closure) throws EnumeratingException;

	/**
	 * As detect, but returns ifNone instead of null, if no element is found.
	 * 
	 * @param closure
	 * @param ifNone
	 * @return
	 * @throws EnumeratingException
	 */
	E detectIfNone(Object closure, E ifNone) throws EnumeratingException;

	/**
	 * Calls the closure for each element iterated.
	 * 
	 * @param closure
	 * @throws EnumeratingException
	 */
	void foreach(Object closure) throws EnumeratingException;

	/**
	 * Applies the closure to each object and returns a list of the result.
	 * 
	 * @param <T>
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	<T> FluentList<T> map(Object closure) throws EnumeratingException;

	/**
	 * Alias to {@link #map}.
	 * 
	 * @param <T>
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	<T> FluentList<T> collect(Object closure) throws EnumeratingException;

	/**
	 * Sort the objects using closure as a comparator. Always returns a new list.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	FluentList<E> sort(Object closure) throws EnumeratingException;

	/**
	 * Returns a list containing the elements of the Enumerator.
	 * 
	 * @return
	 */
	FluentList<E> toList();

	/**
	 * Returns a set containing the elements of the Enumerator.
	 * 
	 * @return
	 */
	FluentSet<E> toSet();

	/**
	 * Returns a list containing the sorted elements of the Enumerator, according to its
	 * natural order.
	 * 
	 * @return
	 */
	FluentList<E> sort();

	/**
	 * As reduce(E initial, Object closure), but uses the first element of the collection
	 * as a the initial value (and skips that element while iterating).
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	E reduce(Object closure) throws EnumeratingException;

	/**
	 * 
	 * Combines the elements of Enumerator by applying the closure to an accumulator value
	 * (memo) and each element in turn. At each step, memo is set to the value returned by
	 * the closure. The first form lets you supply an initial value for memo
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	E reduce(E initial, Object closure) throws EnumeratingException;

	/**
	 * Alias to {@link #reduce(Object))}.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	E inject(Object closure) throws EnumeratingException;

	/**
	 * Alias to {@link #reduce(Object, Object)}.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	E inject(E initial, Object closure) throws EnumeratingException;

	/**
	 * Takes the first n elements. In case the iterable has less than n elements, returns
	 * as much as possible.
	 * 
	 * @param n
	 * @return
	 */
	FluentList<E> take(int n);
	
	/**
	 * Return any element of the iterable. If there are no elements, return null.
	 * @return
	 */
	E any();
}
