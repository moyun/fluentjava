/**
 * 
 */
package org.fluentjava.collections;

import java.util.Comparator;

/**
 * {@link Comparator} that takes any type that is comparable, and uses its natural order. The
 * check is made at runtime though, so that this comparator can be used on Generic
 * classes, as you cannot cast a type E to a Comparable<? super E>
 * 
 * @param <T>
 * Types to be compared using the natural order of T.
 */
public class ComparableComparator<T> implements Comparator<T> {

	public ComparableComparator() {
	}

	@SuppressWarnings("unchecked")
	public int compare(T o1, T o2) {
		if (o1 instanceof Comparable) {
			Comparable<T> c1 = (Comparable<T>) o1;
			return c1.compareTo(o2);
		}
		throw new IllegalArgumentException(o1 + " does not have a natural order");
	}
}