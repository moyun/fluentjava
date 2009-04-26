package org.fluentjava.iterators;

import java.util.Iterator;

/**
 * An iterator that is itself iterable. Useful for classes with several iteration schemes
 * (such as sortedIteration, keyValueIteration, and so on) or when iterators themselves
 * can store information about the iteration process (such as iteration count).
 */
public interface ExtendedIterator<T> extends Iterator<T>, ExtendedIterable<T> {

}
