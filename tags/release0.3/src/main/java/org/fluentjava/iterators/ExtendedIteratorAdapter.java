package org.fluentjava.iterators;

import static java.util.Arrays.asList;

import java.util.Iterator;

/**
 * Adapter that adapts iterable's iterators, iterators (including extended iterators,
 * making this a wrapper as well) and arrays.
 * 
 * @see ExtendedIterator
 */
public class ExtendedIteratorAdapter<T> extends AbstractExtendedIterator<T> {
	/*
	 * Variables
	 */
	private Iterator<? extends T> iterator;

	/*
	 * Constructors
	 */
	public ExtendedIteratorAdapter(Iterator<? extends T> iterator) {
		this.iterator = iterator;
	}

	public ExtendedIteratorAdapter(Iterable<? extends T> iterable) {
		this(iterable.iterator());
	}

	public ExtendedIteratorAdapter(T... iterator) {
		this(asList(iterator));
	}

	/*
	 * Public Methods
	 */
	public boolean hasNext() {
		return iterator.hasNext();
	}

	public T next() {
		return iterator.next();
	}

	@Override
	public void remove() {
		iterator.remove();
	}

}
