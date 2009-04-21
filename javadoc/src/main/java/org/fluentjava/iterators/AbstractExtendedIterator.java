package org.fluentjava.iterators;

/**
 * ExtendedIterator that does the default behaviour for iterator() and method: returns
 * itself.
 */
public abstract class AbstractExtendedIterator<T> implements ExtendedIterator<T> {

	public ExtendedIterator<T> iterator() {
		return this;
	}
	
	public void remove() {
		throw new UnsupportedOperationException("Extended Iterators have remotion as optional");
	}
}
