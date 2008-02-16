package org.fluentjava.iterators;

import java.util.Iterator;


/**
 * An iterator that is itself iterable.
 */
public interface ExtendedIterator<T> extends Iterator<T>, ExtendedIterable<T> {
	
}
