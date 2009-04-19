package org.fluentjava.collections;

import static java.util.Arrays.asList;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

/**
 * Standard implementation of FluentList.
 * @param <E>
 * Type of elements
 */
public class Sequence<E> extends ArrayList<E> implements FluentList<E> {
	private static final long serialVersionUID = 1L;
	
	/*
	 * Factory Methods
	 */
	public static <T> FluentList<T> list(T... list) {
		return new Sequence<T>(list);
	}

	public static <T> FluentList<T> list(Iterable<T> iterable) {
		return new Sequence<T>(iterable);
	}

	/*
	 * Constructors
	 */
	public Sequence() {
	}

	public Sequence(E... list) {
		insert(list);
	}

	public Sequence(Iterable<E> iterable) {
		insert(iterable);
	}

	/*
	 * Public Methods
	 */

	@Override
	public ExtendedIterator<E> iterator() {
		return new ExtendedIteratorAdapter<E>(super.iterator());
	}

	public FluentList<E> insert(E e) {
		add(e);
		return this;
	}

	public FluentList<E> insert(E... list) {
		return insert(asList(list));
	}

	public FluentList<E> insert(Iterable<E> iterable) {
		for (E e : iterable) {
			add(e);
		}
		return this;
	}

	public FluentList<E> delete(E... list) {
		removeAll(asList(list));
		return this;
	}

	public FluentList<E> delete(Iterable<E> iterable) {
		return delete(new Sequence<E>(iterable));
	}

	@SuppressWarnings("unchecked")
	public <T> T[] array(Class<T> clazz) {
		return toArray((T[]) Array.newInstance(clazz, size()));
	}

	public boolean allSatisfy(Object closure) throws EnumeratingException {
		return enumerator().allSatisfy(closure);
	}

	public boolean anySatisfy(Object closure) throws EnumeratingException {
		return enumerator().anySatisfy(closure);
	}

	public <T> FluentList<T> collect(Object closure) throws EnumeratingException {
		return enumerator().collect(closure);
	}

	public int count(Object closure) throws EnumeratingException {
		return enumerator().count(closure);
	}

	public E detect(Object closure) throws EnumeratingException {
		return enumerator().detect(closure);
	}

	public E detectIfNone(Object closure, E ifNone) throws EnumeratingException {
		return enumerator().detectIfNone(closure, ifNone);
	}

	public boolean exists(Object closure) throws EnumeratingException {
		return enumerator().exists(closure);
	}

	public void foreach(Object closure) throws EnumeratingException {
		enumerator().foreach(closure);
	}

	public E inject(E initial, Object closure) throws EnumeratingException {
		return enumerator().inject(initial, closure);
	}

	public E inject(Object closure) throws EnumeratingException {
		return enumerator().inject(closure);
	}

	public <T> FluentList<T> map(Object closure) throws EnumeratingException {
		return enumerator().map(closure);
	}

	public boolean noneSatisfy(Object closure) throws EnumeratingException {
		return enumerator().noneSatisfy(closure);
	}

	public E reduce(E initial, Object closure) throws EnumeratingException {
		return enumerator().reduce(initial, closure);
	}

	public E reduce(Object closure) throws EnumeratingException {
		return enumerator().reduce(closure);
	}

	public FluentList<E> reject(Object closure) throws EnumeratingException {
		return enumerator().reject(closure);
	}

	public FluentList<E> select(Object closure) throws EnumeratingException {
		return enumerator().select(closure);
	}

	public FluentList<E> sort() {
		return enumerator().sort();
	}

	public FluentList<E> sort(Object closure) throws EnumeratingException {
		return enumerator().sort(closure);
	}

	public FluentList<E> toList() {
		return enumerator().toList();
	}


	private Enumerator<E> enumerator() {
		return new Enumerator<E>(this);
	}


}
