package org.fluentjava.collections;

import java.util.Comparator;

/**
 * Simple comparator that takes any type that is comparable and compares using its natural
 * order.
 * 
 * @param <T>
 * Comparable type.
 * 
 * @see ComparableComparator
 */
public class CheckedComparableComparator<T extends Comparable<? super T>> implements Comparator<T> {

	public int compare(T o1, T o2) {
		return o1.compareTo(o2);
	}

}
