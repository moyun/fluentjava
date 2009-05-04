package org.fluentjava.closures;

/**
 * A predicate is a closure that always returns a boolean (primitive).
 */
public abstract class Predicate extends Closure {

	public Object call(Object... args) throws Exception {
		return eval(args);
	}

	/**
	 * Generic evaluation.
	 * 
	 * @param args
	 * Function Arguments
	 * 
	 * @return The result of Call. Must be a boolean.
	 * 
	 * @throws Exception
	 */
	public abstract boolean eval(Object... args) throws Exception;

	/**
	 * Returns the opposite of eval.
	 * 
	 * @param args
	 * Function Arguments
	 * 
	 * @return The result of Call. Always a boolean.
	 * 
	 * @throws Exception
	 */
	public boolean notEval(Object... args) throws Exception {
		return !eval(args);
	}

	public Predicate negated() {
		return new NegatedPredicate(this);
	}
}
