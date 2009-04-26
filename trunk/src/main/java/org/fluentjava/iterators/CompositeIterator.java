package org.fluentjava.iterators;

import java.util.Iterator;

import org.fluentjava.collections.Pair;

/**
 * Iterator that takes two Iterators, Iterables, or Arrays (not necessarily both of the
 * same type), and iterates through both, preserving the order. Cannot remove items.
 * Iterates while both the inner iterables have next elements.
 */
public class CompositeIterator<F, S> extends AbstractExtendedIterator<Pair<F, S>> {
	/*
	 * Variables
	 */
	private Iterator<F> firstIterable;
	private Iterator<S> secondIterable;

	/*
	 * Constructors
	 */
	public CompositeIterator(Iterator<F> firstIterable, Iterator<S> secondIterable) {
		this.firstIterable = firstIterable;
		this.secondIterable = secondIterable;
	}

	/*
	 * Public Methods
	 */
	public boolean hasNext() {
		return firstIterable.hasNext() && secondIterable.hasNext();
	}

	public Pair<F, S> next() {
		return new Pair<F, S>(firstIterable.next(), secondIterable.next());
	}

	public void remove() {
		throw new UnsupportedOperationException("CompositeIterators cannot remove items.");
	}
}
