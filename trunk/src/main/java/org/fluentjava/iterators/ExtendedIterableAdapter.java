package org.fluentjava.iterators;

/**
 * Adapts an Iterable<T> to a ExtendedIterable<T>.
 * @param <T>
 * Type of elements that are to be iterated.
 */
public class ExtendedIterableAdapter<T> implements ExtendedIterable<T> {
	
	private Iterable<T> iterable;
	
	public ExtendedIterableAdapter(Iterable<T> iterable) {
		super();
		this.iterable = iterable;
	}

	public ExtendedIterator<T> iterator() {
		return new ExtendedIteratorAdapter<T>(iterable);
	}

}