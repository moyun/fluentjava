package org.fluentjava.iterators;

import java.util.Iterator;

/**
 * Adapter that takes a simple old fashioned java Iterator, and turns it into an
 * ExtendedIterator. Also adapts for iterables' iterators. Note that iteration index is
 * kept as well, as this is a CountingIterator.
 * 
 * @see ExtendedIterator
 */
public class ExtendedIteratorAdapter<T> extends CountingIterator<T> {
	/*
	 * Variables
	 */
	private Iterator<T> iterator;

	/*
	 * Constructors
	 */
	public ExtendedIteratorAdapter(Iterator<T> iterator) {
		this.iterator = iterator;
	}

	/*
	 * Public Methods
	 */
	public ExtendedIteratorAdapter(Iterable<T> iterable) {
		this.iterator = iterable.iterator();
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public T doNext() {
		return iterator.next();
	}

	public void remove() {
		iterator.remove();
	}

}
