package org.fluentjava.closures;


/**
 * Converts Objects do Closures, or throws ClosureCoercionException if can't.
 */
public class ClosureCoercion {
	public static Closure toClosure(Object closure) {
		if (!(closure instanceof Closure)) {
			throw new ClosureCoercionException("Argument does not coerce to closure: " + closure);
		}
		Closure function = (Closure) closure;
		return function;
	}
	
	public static Predicate toPredicate(Object predicate) {
		if (predicate instanceof Predicate) {
			return (Predicate) predicate;
		}
		return new PredicateAdapter(toClosure(predicate));
	}
}
