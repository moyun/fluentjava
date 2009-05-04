package org.fluentjava.collections;

import static java.util.Arrays.asList;

import java.lang.reflect.Array;
import java.util.HashSet;

import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

/**
 * Standard implementation of FluentSet.
 * 
 * @param <E>
 * Type of elements
 */
public class ExtendedSet<E> extends HashSet<E> implements FluentSet<E> {
	private static final long serialVersionUID = 1L;

	/*
	 * Constructors
	 */
	/**
	 * Creates an empty ExtendedSet.
	 */
	public ExtendedSet() {
	}

	/**
	 * Creates an ExtendedSet with elements args.
	 * 
	 * @param args
	 */
	public ExtendedSet(E... args) {
		insert(args);
	}

	/**
	 * Creates an ExtendedSet the with the iterable elements.
	 * 
	 * @param iterable
	 */
	public ExtendedSet(Iterable<? extends E> iterable) {
		insert(iterable);
	}

	/*
	 * Public Methods
	 */

	@Override
	public ExtendedIterator<E> iterator() {
		return new ExtendedIteratorAdapter<E>(super.iterator());
	}

	public FluentSet<E> insert(E element) {
		add(element);
		return this;
	}

	public FluentSet<E> insert(E... set) {
		return insert(asList(set));
	}

	public FluentSet<E> insert(Iterable<? extends E> iterable) {
		addAll(new Sequence<E>(iterable));
		return this;
	}

	public FluentSet<E> delete(E... elements) {
		removeAll(asList(elements));
		return this;
	}

	public FluentSet<E> delete(Iterable<? extends E> iterable) {
		removeAll(new Sequence<E>(iterable));
		return this;
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

	public FluentSet<E> toSet() {
		return enumerator().toSet();
	}

	/*
	 * Other Methods
	 */
	private Enumerator<E> enumerator() {
		return new Enumerator<E>(this);
	}

}
