package org.fluentjava.closures;

import org.fluentjava.FluentUtils;

/**
 * Adapts a {@link Closure} to a {@link Predicate}. If the closure returns a Boolean, nulls are treated as
 * false.
 */
public class PredicateAdapter extends Predicate {

	protected final Closure adaptedClosure;

	public PredicateAdapter(Closure closure) {
		this.adaptedClosure = closure;
	}

	@Override
	public boolean eval(Object... args) throws Exception {
		Object result = adaptedClosure.call(args);
		if (!(result instanceof Boolean)) {
			throw new IllegalArgumentException("The apdated closure " + adaptedClosure
					+ " does not return Booleans, so is can't be adapted to Predicate");
		}
		Boolean bol = FluentUtils.as(result);
		if (bol == null) {
			return false;
		}
		return bol;

	}

}
