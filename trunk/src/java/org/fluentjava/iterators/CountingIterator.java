package org.fluentjava.iterators;

/**
 * Abstract Extended Iterator that counts how many times next was invoked, mainly used to
 * count the number of the iteration. The number of the iteration starts at 1, though it
 * is 0 before any iteration.
 */
abstract public class CountingIterator<T> extends AbstractExtendedIterator<T> {
	/*
	 * Variables
	 */
	private int iterationNumber = 0;

	/*
	 * Public Methods
	 */
	public T next() {
		iterationNumber++;
		return doNext();
	}

	protected abstract T doNext();

	public int iterationNumber() {
		return iterationNumber;
	}
}
