package org.fluentjava.closures;

import static org.fluentjava.FluentUtils.as;

import java.lang.reflect.Method;
import java.util.List;

import org.fluentjava.FluentUtils;
import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.Sequence;
import org.fluentjava.reflection.ObjectMethods;

/**
 * Converts Objects do Closures, or throws {@link ClosureCoercionException} if can't.
 */
public class ClosureCoercion {
	/*
	 * Constants
	 */
	private static String HamcrestMatcher = "org.hamcrest.BaseMatcher";
	private static final ObjectMethods objectMethods = new ObjectMethods();

	/*
	 * Public Class Methods
	 */
	public static Closure toClosure(Object closure) {
		if (closure == null) {
			throw new ClosureCoercionException("null does not Coerce to Closure");
		}
		if (closure instanceof Closure) {
			Closure function = as(closure);
			return function;
		}
		if (closure instanceof String) {
			String nameOfAMethod = as(closure);
			return new ClosureOfAMethodName(nameOfAMethod);
		}
		Method method = getSingleAbstractMethod(closure);
		if (method != null) {
			return new ClosureOfAMethod(closure, method);
		}
		if (isFromHamcrest(closure)) {
			return FluentUtils.my(closure, "matches");
		}
		throw new ClosureCoercionException("Argument does not coerce to closure: "
				+ closure);

	}

	public static Predicate toPredicate(Object predicate) {
		if (predicate instanceof Predicate) {
			return as(predicate);
		}
		return new PredicateAdapter(toClosure(predicate));
	}

	/*
	 * Private Class Methods
	 */
	private static Method getSingleAbstractMethod(Object closure) {
		FluentList<Class<?>> possibleInterfaces = new Sequence<Class<?>>();
		for (Class<?> inter : closure.getClass().getInterfaces()) {
			if (filterMethods(inter).size() == 1) {
				possibleInterfaces.add(inter);
			}
		}
		if (possibleInterfaces.size() == 1) {
			return filterMethods(possibleInterfaces.get(0)).get(0);
		}
		return null;
	}

	private static List<Method> filterMethods(Class<?> clazz) {
		return objectMethods.filterObjectMethods(
				clazz.getMethods());
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
