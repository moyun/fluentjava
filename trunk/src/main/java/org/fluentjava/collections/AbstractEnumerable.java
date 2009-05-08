package org.fluentjava.collections;

import static org.fluentjava.FluentUtils.as;
import static org.fluentjava.closures.ClosureCoercion.toClosure;
import static org.fluentjava.closures.ClosureCoercion.toPredicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.fluentjava.Closures;
import org.fluentjava.closures.Closure;
import org.fluentjava.closures.Predicate;
import org.fluentjava.iterators.ExtendedIterator;

/**
 * Default implementation of {@link Enumerable}. The only method left for subclasses to
 * implement is iterator.
 * 
 * @param <E>
 */
public abstract class AbstractEnumerable<E> implements Enumerable<E> {
	public abstract ExtendedIterator<E> iterator();

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

	@Override
	public FluentList<E> findAll(Object closure) throws EnumeratingException {
		return select(closure);
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

	@Override
	public FluentList<E> sortBy(Object closure) throws EnumeratingException {
		Closure keyGenerator = toClosure(closure);
		try {
			List<Pair<Object, E>> middle = new ArrayList<Pair<Object, E>>();
			for (E e : this) {
				Pair<Object, E> pair = new Pair<Object, E>(keyGenerator.call(e), e);
				middle.add(pair);
			}
			Comparator<Pair<Object, E>> compartor = pairComparator();
			Collections.sort(middle, compartor);
			FluentList<E> ret = new Sequence<E>();
			for (Pair<Object, E> pair : middle) {
				ret.add(pair.second);
			}
			return ret;
		} catch (Exception e) {
			throw new EnumeratingException(e);
		}
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

	public FluentList<E> take(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Cannot take negative ammount of elements:" + n);
		}
		FluentList<E> ret = new Sequence<E>();
		for (E e : iterator()) {
			if (n-- == 0) {
				break;
			}
			ret.add(e);
		}
		return ret;
	}

	@Override
	public E any() {
		ExtendedIterator<E> i = iterator();
		if (!i.hasNext()) {
			return null;
		}
		return i.next();
	}

	@Override
	public E max() {
		return doMax(new ComparableComparator<E>());
	}

	@Override
	public E max(Object closure) {
		Comparator<E> comparator = toClosure(closure).as(Comparator.class);
		return doMax(comparator);
	}

	@Override
	public E maxBy(Object closure) {
		return getMax(closure, new ComparableComparator<Object>());
	}

	@Override
	public E min() {
		return doMin(new ComparableComparator<E>());
	}

	@Override
	public E min(Object closure) {
		Comparator<E> comparator = toClosure(closure).as(Comparator.class);
		return doMin(comparator);
	}

	@Override
	public E minBy(Object closure) {
		return getMax(closure, reverse(new ComparableComparator<Object>()));
	}

	/*
	 * Other Methods
	 */
	private E getMax(Object valueFunction, Comparator<?> comparatorOfValue) {
		ExtendedIterator<E> it = iterator();
		if (!it.hasNext()) {
			return null;
		}
		try {
			Comparator<Object> comp = as(comparatorOfValue);
			Closure function = toClosure(valueFunction);
			E ret = it.next();
			Object retValue = function.call(ret);
			for (E cur : it) {
				Object curValue = function.call(cur);
				if (comp.compare(curValue, retValue) > 0) {
					ret = cur;
					retValue = curValue;
				}
			}
			return ret;
		} catch (Exception e) {
			throw new EnumeratingException(e);
		}
	}

	private E doMax(Comparator<E> comparator) {
		return getMax(Closures.identity(), comparator);
	}

	private E doMin(Comparator<E> comparator) {
		return doMax(reverse(comparator));
	}

	private <T> Comparator<T> reverse(Comparator<T> comparator) {
		return new ReversedComparator<T>(comparator);
	}

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

	private Comparator<Pair<Object, E>> pairComparator() {
		final Comparator<Object> c = new ComparableComparator<Object>();
		return new Comparator<Pair<Object, E>>() {
			@Override
			public int compare(Pair<Object, E> o1, Pair<Object, E> o2) {
				return c.compare(o1.first, o2.first);
			}
		};
	}
}
