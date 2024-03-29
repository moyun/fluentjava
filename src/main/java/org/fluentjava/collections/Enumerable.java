package org.fluentjava.collections;

import java.util.Map;
import java.util.Map.Entry;

import org.fluentjava.closures.Closure;
import org.fluentjava.closures.ClosureCoercion;
import org.fluentjava.closures.ClosureCoercionException;
import org.fluentjava.iterators.ExtendedIterable;

/**
 * Implements an enumeration protocol. A class that implement this interface allow its
 * elements to be operated on with closures. All exceptions caught while iterating are
 * wrapped around the Runtime Exception {@link EnumeratingException}.
 * 
 * Important: all arguments named closures will be coerced into a {@link Closure} using
 * {@link ClosureCoercion} methods, and therefore, can throw
 * {@link ClosureCoercionException}.
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
	 * Count how many objects are on the enumerator. Same as {@link #count(Object)} for a
	 * closure that returns true.
	 * 
	 * @return
	 * @throws EnumeratingException
	 */
	int count() throws EnumeratingException;

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
	 * Lazy version of {@link #select(Object)}. Since the elements are generated as
	 * requested, beware of non immutable data, even more when it is shared by multiple
	 * threads.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	Enumerable<E> iselect(Object closure) throws EnumeratingException;

	/**
	 * Alias to {@link #select(Object)}.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	FluentList<E> findAll(Object closure) throws EnumeratingException;

	/**
	 * Alias to {@link #iselect(Object)}.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	Enumerable<E> ifindAll(Object closure) throws EnumeratingException;

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
	 * Lazy version of {@link #reject(Object)}. Since the elements are generated as
	 * requested, beware of non immutable data, even more when it is shared by multiple
	 * threads.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	Enumerable<E> ireject(Object closure) throws EnumeratingException;

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
	 * Lazy version of {@link #map(Object)}. Since the elements are generated as
	 * requested, beware of non immutable data, even more when it is shared by multiple
	 * threads.
	 * 
	 * @param <T>
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	<T> Enumerable<T> imap(Object closure) throws EnumeratingException;

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
	 * Alias to {@link #imap(Object)}.
	 * 
	 * @param <T>
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	<T> Enumerable<T> icollect(Object closure) throws EnumeratingException;

	/**
	 * Similar to {@link #map(Object)}, but returns a list where the elements are pairs
	 * whose first element is the element from the Enumerable, and the second one is the
	 * result of the closure applied to such element. If you want a {@link Map}, just use
	 * {@link #toMap()} afterwards, or just use the convenience method
	 * {@link #toMapBy(Object)} instead.
	 * 
	 * @param <V>
	 * @param closure
	 * @return
	 */
	<V> FluentList<Entry<E, V>> mapWithKeys(Object closure);

	/**
	 * Lazy version of {@link #mapWithKeys(Object)}. Since the elements are generated as
	 * requested, beware of non immutable data, even more when it is shared by multiple
	 * threads.
	 * 
	 * @param <V>
	 * @param closure
	 * @return
	 */
	<V> Enumerable<Entry<E, V>> imapWithKeys(Object closure);

	/**
	 * Returns a list containing the sorted elements of the Enumerator, according to its
	 * natural order.
	 * 
	 * @return
	 */
	FluentList<E> sort();

	/**
	 * Sort the objects using closure as a comparator. Always returns a new list.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	FluentList<E> sort(Object closure) throws EnumeratingException;

	/**
	 * Sort the objects e according to the natural order results of closure.call(e), for
	 * every e in the enumerable. Always returns a new list.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	FluentList<E> sortBy(Object closure) throws EnumeratingException;

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
	 * Returns a map with the elements of the Enumerator, <b>provided</b> they are all a
	 * type of {@link Entry}.
	 * 
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	<K, V> FluentMap<K, V> toMap() throws EnumeratingException;

	/**
	 * Convenience method to cascading calls of {@link #mapWithKeys(Object)} and
	 * {@link #toMap()}.
	 * 
	 * @param closure
	 * @param <V>
	 * @return
	 * @throws EnumeratingException
	 */
	<V> FluentMap<E, V> toMapBy(Object closure) throws EnumeratingException;

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
	 * Alias to {@link #reduce(Object)}.
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
	 * @throws EnumeratingException
	 */
	FluentList<E> take(int n) throws EnumeratingException;

	/**
	 * Lazy version of {@link #take(int)}. Since the elements are generated as requested,
	 * beware of non immutable data, even more when it is shared by multiple threads.
	 * 
	 * @param n
	 * @return
	 * @throws EnumeratingException
	 */
	Enumerable<E> itake(int n) throws EnumeratingException;

	/**
	 * Return any element of the iterable. If there are no elements, return null.
	 * 
	 * @return
	 * @throws EnumeratingException
	 */
	E any() throws EnumeratingException;

	/**
	 * Returns the maximum element according to the element's natural order (if any).
	 * Returns null if there are no elements.
	 * 
	 * @return
	 * @throws EnumeratingException
	 */
	E max() throws EnumeratingException;

	/**
	 * Returns the maximum element according to the order imposed by the Closure as a
	 * comparator. Returns null if there are no elements.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	E max(Object closure) throws EnumeratingException;

	/**
	 * Returns the maximum element e such that closure.call(e) is the minimum. Returns
	 * null if there are no elements.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	E maxBy(Object closure) throws EnumeratingException;

	/**
	 * Returns the minimum element according to the element's natural order (if any).
	 * Returns null if there are no elements.
	 * 
	 * @return
	 * @throws EnumeratingException
	 */
	E min() throws EnumeratingException;

	/**
	 * Returns the minimum element according to the order imposed by the Closure as a
	 * comparator. Returns null if there are no elements.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	E min(Object closure) throws EnumeratingException;

	/**
	 * Returns the minimum element e such that closure.call(e) is the minimum. Returns
	 * null if there are no elements.
	 * 
	 * @param closure
	 * @return
	 * @throws EnumeratingException
	 */
	E minBy(Object closure) throws EnumeratingException;

}
