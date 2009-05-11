package org.fluentjava.iterators;

/**
 * ExtendedIterator that does the default behavior for iterator() and method: returns
 * itself.
 */
public abstract class AbstractExtendedIterator<T> implements ExtendedIterator<T> {

	public ExtendedIterator<T> iterator() {
		return this;
	}

	public void remove() {
		String message = "Extended Iterators have removal as optional.";
		throw new UnsupportedOperationException(message);
	}
}
