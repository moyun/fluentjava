package org.fluentjava.collections;

import static java.util.Arrays.asList;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.fluentjava.closures.Closure;
import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

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

	public boolean exists(Object closure) throws EnumeratingException {
		if (closure instanceof Closure) {
			Closure function = (Closure) closure;
			try {
				for (E e : iterator()) {
					boolean ret = function.invoke(e);
					if (ret) {
						return true;
					}
				}
				return false;
			} catch (Exception e) {
				throw new EnumeratingException(e);
			}
		}
		throw new IllegalArgumentException("Argument does not coerce to closure: " + closure);
	}

	public boolean allSatisfy(Object closure) throws EnumeratingException {
		if (closure instanceof Closure) {
			Closure function = (Closure) closure;
			try {
				for (E e : iterator()) {
					boolean ret = function.invoke(e);
					if (!ret) {
						return false;
					}
				}
				return true;
			} catch (Exception e) {
				throw new EnumeratingException(e);
			}
		}
		throw new IllegalArgumentException("Argument does not coerce to closure: " + closure);
	}

	public int count(Object closure) throws EnumeratingException {
		if (closure instanceof Closure) {
			Closure function = (Closure) closure;
			try {
				int total = 0;
				for (E e : iterator()) {
					boolean ret = function.invoke(e);
					if (ret) {
						total++;
					}
				}
				return total;
			} catch (Exception e) {
				throw new EnumeratingException(e);
			}
		}
		throw new IllegalArgumentException("Argument does not coerce to closure: " + closure);
	}

	public FluentList<E> select(Object closure) throws EnumeratingException {
		if (closure instanceof Closure) {
			Closure function = (Closure) closure;
			try {
				FluentList<E> list = new Sequence<E>();
				for (E e : iterator()) {
					boolean ret = function.invoke(e);
					if (ret) {
						list.add(e);
					}
				}
				return list;
			} catch (Exception e) {
				throw new EnumeratingException(e);
			}
		}
		throw new IllegalArgumentException("Argument does not coerce to closure: " + closure);
	}
}
