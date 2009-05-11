package org.fluentjava.iterators;

import java.util.Iterator;

/**
 * Extended Iterator Adapter that counts how many times next was invoked, mainly used to
 * count the number of the iteration. The number of the iteration starts at 1, though it
 * is 0 before any iteration.
 */
public class CountingIterator<T> extends ExtendedIteratorAdapter<T> {
	/*
	 * Constructors
	 */
	public CountingIterator(Iterable<? extends T> iterable) {
		super(iterable);
	}

	public CountingIterator(Iterator<? extends T> iterator) {
		super(iterator);
	}

	public CountingIterator(T... iterator) {
		super(iterator);
	}

	/*
	 * Variables
	 */
	private int iterationNumber = 0;

	/*
	 * Public Methods
	 */
	@Override
	public T next() {
		iterationNumber++;
		return super.next();
	}

	public int iterationNumber() {
		return iterationNumber;
	}
	
	/**
	 * iterationNumber - 1.
	 * @return
	 */
	public int iterationIndex() {
		return iterationNumber - 1;
	}
}
