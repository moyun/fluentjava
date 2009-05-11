package org.fluentjava.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator that takes at most n elements of an adapted iterator. 
 * @param <T>
 */
public class LimitedIterator<T> extends ExtendedIteratorAdapter<T> {

	private int count;

	public LimitedIterator(int count, Iterable<? extends T> iterable) {
		super(iterable);
		this.count = count;
	}

	public LimitedIterator(int count, Iterator<? extends T> iterator) {
		super(iterator);
		this.count = count;
	}

	public LimitedIterator(int count, T... iterator) {
		super(iterator);
		this.count = count;
	}
	
	@Override
	public boolean hasNext() {
		return super.hasNext() && count > 0;
	}
	
	@Override
	public T next() {
		if (count == 0) {
			throw new NoSuchElementException();
		}
		count--;
		return super.next();
	}

}
