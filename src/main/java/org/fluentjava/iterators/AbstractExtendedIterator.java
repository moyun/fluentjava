package org.fluentjava.iterators;

/**
 * ExtendedIterator that does the default behaviour for iterator() and method: returns
 * itself.
 */
abstract public class AbstractExtendedIterator<T> implements ExtendedIterator<T> {

	public ExtendedIterator<T> iterator() {
		return this;
	}
}
