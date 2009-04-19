package org.fluentjava.collections;

import org.fluentjava.iterators.ExtendedIterable;

/**
 * Implements a enumeration protocol. A classthat implement this interface allow its elements
 * to be operated on with closures.
 * 
 * @param <E>
 * Type of Elements.
 */
public interface Enumerable<E> extends ExtendedIterable<E> {
	/**
	 * Extended Collection Method. Checks if there exists an object such that the closure
	 * returns true. The closure must only return boolean values. 
	 * 
	 * @param closure
	 * Predicate
	 * @return
	 * The existence
	 * @throws EnumeratingException
	 * In case anything happens while enumerating.
	 */
	boolean exists(Object closure) throws EnumeratingException;
	
	boolean anySatisfy(Object closure) throws EnumeratingException;

	boolean allSatisfy(Object closure) throws EnumeratingException;
	
	boolean noneSatisfy(Object closure) throws EnumeratingException;

	int count(Object closure) throws EnumeratingException;

	FluentList<E> select(Object closure) throws EnumeratingException;
	
	FluentList<E> reject(Object closure) throws EnumeratingException;

	E detect(Object closure) throws EnumeratingException;

	E detectIfNone(Object closure, E ifNone) throws EnumeratingException;
	
	void foreach(Object closure) throws EnumeratingException;

	<T> FluentList<T> map(Object closure) throws EnumeratingException;

	<T> FluentList<T> collect(Object closure) throws EnumeratingException;

	FluentList<E> sort(Object closure) throws EnumeratingException;

	FluentList<E> toList();

	FluentList<E> sort();

	E reduce(Object closure) throws EnumeratingException;

	E reduce(E initial, Object closure) throws EnumeratingException;
	
	E inject(Object closure) throws EnumeratingException;

	E inject(E initial, Object closure) throws EnumeratingException;
}
