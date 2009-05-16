package org.fluentjava.iterators;

/**
 * An Iterable that provides an extendedIterator instead of an Iterable.
 * 
 * @param <T>
 */
public interface ExtendedIterable<T> extends Iterable<T> {
	ExtendedIterator<T> iterator();
}
