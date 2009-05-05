package org.fluentjava.collections;

import static org.fluentjava.closures.ClosureCoercion.toClosure;
import static org.fluentjava.closures.ClosureCoercion.toPredicate;

import java.util.Collections;
import java.util.Comparator;

import org.fluentjava.closures.Closure;
import org.fluentjava.closures.Predicate;
import org.fluentjava.iterators.ExtendedIterable;
import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

/**
 * Class that takes an iterable, and implements all {@link Enumerable} methods using only its
 * iterator.
 * 
 * @param <E>
 * Type of elements to be iterated on.
 */
public class Enumerator<E> extends AbstractEnumerable<E> implements Enumerable<E> {
	/*
	 * Variables
	 */
	protected final ExtendedIterable<E> iterable;

	/*
	 * Constructors
	 */
	@SuppressWarnings("unchecked")
	public Enumerator(Iterable<? extends E> iterable) {
		if (iterable instanceof ExtendedIterable) {
			this.iterable = (ExtendedIterable<E>) iterable;
		}
		else {
			this.iterable = new ExtendedIteratorAdapter<E>(iterable);
		}

	}

	/*
	 * Public Methods
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
				T element = function.<T>invoke(e);
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

	public FluentSet<E> toSet() {
		return new ExtendedSet<E>(this);
	}

	public E reduce(Object closure) throws EnumeratingException {
		ExtendedIterator<E> it = iterator();
		if (!it.hasNext()) {
			return null;
		}
		return doReduce(it.next(), closure, it);
	}

	public E reduce(E initial, Object closure) throws EnumeratingException {
		return doReduce(initial, closure, iterator());
	}

	public E inject(Object closure) throws EnumeratingException {
		return reduce(closure);
	}

	public E inject(E initial, Object closure) throws EnumeratingException {
		return reduce(initial, closure);
	}

	public ExtendedIterator<E> iterator() {
		return iterable.iterator();
	}

	/*
	 * Other Methods
	 */
	private E doReduce(E initial, Object closure, ExtendedIterator<E> it) {
		Closure function = toClosure(closure);
		if (!it.hasNext()) {
			return initial;
		}
		try {
			E result = initial;
			while (it.hasNext()) {
				result = function.<E>invoke(result, it.next());
			}
			return result;
		} catch (Exception e) {
			throw new EnumeratingException(e);
		}
	}

}
