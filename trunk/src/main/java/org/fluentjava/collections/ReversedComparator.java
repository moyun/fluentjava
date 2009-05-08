package org.fluentjava.collections;

import java.util.Comparator;

/**
 * Comparator that takes a reversed comparator and returns the oposite values. For
 * instance, sorting with a reversed comparator returns the elements in reversed order.
 * 
 * @param <T>
 */
public class ReversedComparator<T> implements Comparator<T> {

	protected final Comparator<T> comparator;

	public ReversedComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public int compare(T o1, T o2) {
		return comparator.compare(o2, o1);
	}

}
