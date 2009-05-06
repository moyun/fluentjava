package org.fluentjava.collections;

import static org.fluentjava.FluentUtils.as;

import org.fluentjava.iterators.ExtendedIterable;
import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

/**
 * Class that takes an iterable, and implements all {@link Enumerable} methods using only
 * its iterator.
 * 
 * @param <E>
 * Type of elements to be iterated on.
 */
public class Enumerator<E> extends AbstractEnumerable<E> implements Enumerable<E> {
	/*
	 * Variables
	 */
	protected final ExtendedIterable<E> iterable;

	/*
	 * Constructors
	 */
	public Enumerator(Iterable<? extends E> iterable) {
		if (iterable instanceof ExtendedIterable) {
			this.iterable = as(iterable);
		}
		else {
			this.iterable = new ExtendedIteratorAdapter<E>(iterable);
		}

	}

	/*
	 * Public Methods
	 */
	public ExtendedIterator<E> iterator() {
		return iterable.iterator();
	}
}
