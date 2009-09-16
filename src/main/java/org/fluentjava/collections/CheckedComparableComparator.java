package org.fluentjava.collections;

import java.io.Serializable;
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
public class CheckedComparableComparator<T extends Comparable<? super T>>
		implements
			Comparator<T>, Serializable {

	private static final long serialVersionUID = 1L;

	public int compare(T o1, T o2) {
		return o1.compareTo(o2);
	}

}
