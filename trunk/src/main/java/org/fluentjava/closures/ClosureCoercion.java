package org.fluentjava.closures;

import java.lang.reflect.Method;
import java.util.Comparator;

import org.fluentjava.FluentUtils;
import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.Sequence;
import org.fluentjava.reflection.RuntimeReflectionException;

/**
 * Converts Objects do Closures, or throws {@link ClosureCoercionException} if can't.
 */
public class ClosureCoercion {
	/*
	 * Constants
	 */
	private static String HamcrestMatcher = "org.hamcrest.BaseMatcher";

	/*
	 * Public Class Methods
	 */
	public static Closure toClosure(Object closure) {
		if (closure instanceof Closure) {
			Closure function = (Closure) closure;
			return function;
		}
		if (closure instanceof String) {
			String nameOfAMethod = (String) closure;
			return new ClosureOfAString(nameOfAMethod);
		}
		Method method = getSingleAbstractMethod(closure);
		if (method != null) {
			return new ClosureOfAMethod(closure, method);
		}
		if (closure instanceof Comparator) {
			return comparatorToClosure(closure);
		}
		if (isFromHamcrest(closure)) {
			return FluentUtils.my(closure, "matches");
		}
		throw new ClosureCoercionException("Argument does not coerce to closure: " + closure);

	}

	public static Predicate toPredicate(Object predicate) {
		if (predicate instanceof Predicate) {
			return (Predicate) predicate;
		}
		return new PredicateAdapter(toClosure(predicate));
	}

	/*
	 * Private Class Methods
	 */
	private static Method getSingleAbstractMethod(Object closure) {
		FluentList<Class<?>> possibleInterfaces = new Sequence<Class<?>>();
		for (Class<?> inter : closure.getClass().getInterfaces()) {
			if (inter.getMethods().length == 1) {
				possibleInterfaces.add(inter);
			}
		}
		if (possibleInterfaces.size() == 1) {
			return possibleInterfaces.get(0).getMethods()[0];
		}
		return null;
	}

	private static Closure comparatorToClosure(Object closure) {
		try {
			return new ClosureOfAMethod(closure, Comparator.class.getMethod("compare", Object.class,
					Object.class));
		} catch (NoSuchMethodException e) {
			throw new RuntimeReflectionException(e);
		}
	}

	private static boolean isFromHamcrest(Object o) {
		return isFromHamcrest(o.getClass());
	}

	private static boolean isFromHamcrest(Class<?> c) {
		if (c == Object.class) {
			return false;
		}
		Class<?> superclass = c.getSuperclass();
		if (HamcrestMatcher.equals(superclass.getCanonicalName())) {
			return true;
		}
		return isFromHamcrest(superclass);
	}
}
