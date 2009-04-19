package org.fluentjava.collections;

import java.util.Set;

import org.fluentjava.iterators.ExtendedIterable;


/**
 * 
 * TODO: add javadoc.
 * @param <E>
 */
public interface FluentSet<E> extends Set<E>, ExtendedIterable<E> {

	FluentSet<E> insert(E element);

	FluentSet<E> insert(E... element);

	FluentSet<E> insert(Iterable<E> iterable);

	FluentSet<E> delete(E...elements);

	<T> T[] array(Class<T> clazz);
}
