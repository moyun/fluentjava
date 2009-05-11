package org.fluentjava.collections;

import static org.fluentjava.FluentUtils.as;
import static org.fluentjava.closures.ClosureCoercion.toClosure;
import static org.fluentjava.closures.ClosureCoercion.toPredicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import org.fluentjava.Closures;
import org.fluentjava.closures.Closure;
import org.fluentjava.closures.Predicate;
import org.fluentjava.iterators.AbstractExtendedIterator;
import org.fluentjava.iterators.ExtendedIterable;
import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.LimitedIterator;

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

	public Enumerable<E> iselect(Object closure) throws EnumeratingException {
		Predicate predicate = toPredicate(closure);
		return asEnum(new LazySelect<E>(this, predicate));
	}

	public Enumerable<E> ireject(Object closure) throws EnumeratingException {
		Predicate predicate = toPredicate(closure).negated();
		return asEnum(new LazySelect<E>(this, predicate));
	}

	public FluentList<E> findAll(Object closure) throws EnumeratingException {
		return select(closure);
	}

	public Enumerable<E> ifindAll(Object closure) throws EnumeratingException {
		return iselect(closure);
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

	@Override
	public <T> Enumerable<T> imap(Object closure) throws EnumeratingException {
		Closure function = toClosure(closure);
		return asEnum(new LazyMap<E, T>(this, function));
	}

	public <T> FluentList<T> collect(Object closure) throws EnumeratingException {
		return map(closure);
	}
	
	@Override
	public <T> Enumerable<T> icollect(Object closure) throws EnumeratingException {
		return imap(closure);
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

	public <K, V> FluentMap<K, V> toMap() {
		FluentMap<K, V> ret = new Dictionary<K, V>();
		for (E e : iterator()) {
			Entry<K, V> pair = as(e);
			ret.put(pair.getKey(), pair.getValue());
		}
		return ret;
	}

	public <V> FluentMap<E, V> toMapBy(Object closure) throws EnumeratingException {
		return mapWithKeys(closure).toMap();
	}

	public <V> FluentList<Entry<E, V>> mapWithKeys(Object closure)
			throws EnumeratingException {
		FluentList<Entry<E, V>> ret = this.<V>imapWithKeys(closure).toList();
		return ret;
	}
	
	@Override
	public <V> Enumerable<Entry<E, V>> imapWithKeys(Object closure) {
		Closure function = toClosure(closure);
		LazyMapWithKeys<E, V> iterable = new LazyMapWithKeys<E, V>(this, function);
		return new Enumerator<Entry<E, V>>(iterable);
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

	public FluentList<E> take(int n) throws EnumeratingException {
		return itake(n).toList();
	}
	
	@Override
	public Enumerable<E> itake(int n) throws EnumeratingException {
		if (n < 0) {
			throw new IllegalArgumentException("Cannot take negative ammount of elements:"
					+ n);
		}
		return asEnum(new LimitedIterator<E>(n, this));
	}

	public E any() throws EnumeratingException {
		ExtendedIterator<E> i = iterator();
		if (!i.hasNext()) {
			return null;
		}
		return i.next();
	}

	public E max() throws EnumeratingException {
		return doMax(new ComparableComparator<E>());
	}

	public E max(Object closure) throws EnumeratingException {
		Comparator<E> comparator = toClosure(closure).as(Comparator.class);
		return doMax(comparator);
	}

	public E maxBy(Object closure) throws EnumeratingException {
		return getMax(closure, new ComparableComparator<Object>());
	}

	public E min() throws EnumeratingException {
		return doMin(new ComparableComparator<E>());
	}

	public E min(Object closure) throws EnumeratingException {
		Comparator<E> comparator = toClosure(closure).as(Comparator.class);
		return doMin(comparator);
	}

	public E minBy(Object closure) throws EnumeratingException {
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

	private <T> Enumerable<T> asEnum(Iterable<T> iterable) {
		return new Enumerator<T>(iterable);
	}

	
	/*
	 * Helper Classes 
	 */
	/**
	 * Utility class to implement {@link Enumerable#iselect(Object)} and
	 * {@link Enumerable#ireject(Object)}.
	 * 
	 * @param <E>
	 */
	private static class LazySelect<E> implements ExtendedIterable<E> {
		private Iterable<E> iterable;
		private Predicate predicate;

		public LazySelect(Iterable<E> iterable, Predicate predicate) {
			this.iterable = iterable;
			this.predicate = predicate;
		}

		private boolean eval(Object arg) {
			try {
				return predicate.eval(arg);
			} catch (Exception e) {
				throw new EnumeratingException(e);
			}
		}

		@Override
		public ExtendedIterator<E> iterator() {
			return new LazySelectIterator();
		}

		private class LazySelectIterator extends AbstractExtendedIterator<E> {
			private Iterator<E> it;
			private E lookAhead;

			public LazySelectIterator() {
				it = iterable.iterator();
				lookAhead = findNextThatEvalsTrue();
			}

			private E findNextThatEvalsTrue() {
				while (it.hasNext()) {
					E ret = it.next();
					if (eval(ret)) {
						return ret;
					}
				}
				return null;
			}

			@Override
			public boolean hasNext() {
				return lookAhead != null;
			}

			@Override
			public E next() {
				if (lookAhead == null) {
					throw new NoSuchElementException();
				}
				E ret = lookAhead;
				lookAhead = findNextThatEvalsTrue();
				return ret;
			}

		}

	}

	/**
	 * Utility class for {@link Enumerable#map(Object)}.
	 * @param <E>
	 * @param <R>
	 */
	private static class LazyMap<E, R> implements ExtendedIterable<R> {
		private Iterable<E> iterable;
		private Closure function;

		public LazyMap(Iterable<E> iterable, Closure function) {
			this.iterable = iterable;
			this.function = function;
		}

		private R call(Object arg) {
			try {
				return function.<R>invoke(arg);
			} catch (Exception e) {
				throw new EnumeratingException(e);
			}
		}

		@Override
		public ExtendedIterator<R> iterator() {
			return new LazyMapIterator();
		}

		private class LazyMapIterator extends AbstractExtendedIterator<R> {
			private Iterator<E> it;

			public LazyMapIterator() {
				it = iterable.iterator();
			}

			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public R next() {
				return call(it.next());

			}

		}

	}
	
	/**
	 * Utility class for {@link Enumerable#mapWithKeys(Object)}.
	 * @param <E>
	 * @param <R>
	 */
	private static class LazyMapWithKeys<E, R> implements ExtendedIterable<Pair<E, R>> {
		private Iterable<E> iterable;
		private Closure function;

		public LazyMapWithKeys(Iterable<E> iterable, Closure function) {
			this.iterable = iterable;
			this.function = function;
		}

		@SuppressWarnings("unchecked")
		private Pair<E, R> call(Object arg) {
			try {
				E realArg = (E) arg;
				R result = function.<R>invoke(realArg);
				return new Pair<E, R>(realArg, result);
			} catch (Exception e) {
				throw new EnumeratingException(e);
			}
		}

		@Override
		public ExtendedIterator<Pair<E, R>> iterator() {
			return new LazyMapIterator();
		}

		private class LazyMapIterator extends AbstractExtendedIterator<Pair<E, R>> {
			private Iterator<E> it;

			public LazyMapIterator() {
				it = iterable.iterator();
			}

			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public Pair<E, R> next() {
				return call(it.next());

			}

		}

	}

}
