package org.fluentjava.collections;

import static java.util.Arrays.asList;
import static org.fluentjava.closures.ClosureCoercion.toClosure;
import static org.fluentjava.closures.ClosureCoercion.toPredicate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.fluentjava.closures.Closure;
import org.fluentjava.closures.Predicate;
import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

/**
 * @param <E>
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

	/*
	 * Enum Methods
	 */
	public E detect(Object closure) throws EnumeratingException {
		return detectIfNone(closure, null);
	}

	public E detectIfNone(Object closure, E ifNone) throws EnumeratingException {
		Predicate predicate = toPredicate(closure);
		try {
			for (E e : iterator()) {
				if (predicate.eval(e)) {
					return e;
				}
			}
			return ifNone;
		} catch (Exception e) {
			throw new EnumeratingException(e);
		}
	}

	public boolean exists(Object closure) throws EnumeratingException {
		return anySatisfy(closure);
	}

	public boolean noneSatisfy(Object closure) throws EnumeratingException {
		return allSatisfy(toPredicate(closure).negated());
	}

	public boolean allSatisfy(Object closure) throws EnumeratingException {
		Predicate predicate = toPredicate(closure);
		try {
			for (E e : iterator()) {
				if (predicate.notEval(e)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			throw new EnumeratingException(e);
		}
	}

	public boolean anySatisfy(Object closure) throws EnumeratingException {
		Predicate predicate = toPredicate(closure);
		try {
			for (E e : iterator()) {
				if (predicate.eval(e)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			throw new EnumeratingException(e);
		}
	}

	public int count(Object closure) throws EnumeratingException {
		Predicate predicate = toPredicate(closure);
		try {
			int total = 0;
			for (E e : iterator()) {
				if (predicate.eval(e)) {
					total++;
				}
			}
			return total;
		} catch (Exception e) {
			throw new EnumeratingException(e);
		}
	}

	public FluentList<E> select(Object closure) throws EnumeratingException {
		Predicate predicate = toPredicate(closure);
		try {
			FluentList<E> list = new Sequence<E>();
			for (E e : iterator()) {
				if (predicate.eval(e)) {
					list.add(e);
				}
			}
			return list;
		} catch (Exception e) {
			throw new EnumeratingException(e);
		}

	}

	public FluentList<E> reject(Object closure) throws EnumeratingException {
		return select(toPredicate(closure).negated());

	}

	public void foreach(Object closure) throws EnumeratingException {
		Closure function = toClosure(closure);
		try {
			for (E e : iterator()) {
				function.call(e);
			}
		} catch (Exception e) {
			throw new EnumeratingException(e);
		}
	}

	public <T> FluentList<T> map(Object closure) throws EnumeratingException {
		Closure function = toClosure(closure);
		try {
			FluentList<T> list = new Sequence<T>();
			for (E e : iterator()) {
				T element = function.invoke(e);
				list.add(element);
			}
			return list;
		} catch (Exception e) {
			throw new EnumeratingException(e);
		}
	}

	public <T> FluentList<T> collect(Object closure) throws EnumeratingException {
		return map(closure);
	}

	public FluentList<E> sort(Object closure) throws EnumeratingException {
		Comparator<E> comparator = toClosure(closure).toInteface(Comparator.class);
		try {
			FluentList<E> list = toList();
			Collections.sort(list, comparator);
			return list;
		} catch (Exception e) {
			throw new EnumeratingException(e);
		}
	}

	public FluentList<E> sort() {
		FluentList<E> list = toList();
		Collections.sort(list, new ComparableComparator<E>());
		return list;

	}

	public FluentList<E> toList() {
		return new Sequence<E>(this);
	}

	@SuppressWarnings("unchecked")
	public <T> T reduce(Object closure) throws EnumeratingException {
		Closure function = toClosure(closure);
		try {
			if (size() == 0) {
				return null;
			}
			if (size() == 1) {
				return (T) get(0);
			}
			Object result = function.call(get(0), get(1));
			for (int i = 2; i < size(); i++) {
				result  = function.call(result, get(i));
			}
			return (T) result;
		} catch (Exception e) {
			throw new EnumeratingException(e);
		}
	}

}
