package org.fluentjava.closures;

/**
 * Returns the oposite of the predicate on eval calls.
 */
class NegatedPredicate extends Predicate {

	private final Predicate adaptedPredicate;

	/**
	 * Recieves the predicate to be adapted.
	 * @param predicate
	 */
	public NegatedPredicate(Predicate predicate) {
		this.adaptedPredicate = predicate;
	}

	@Override
	public boolean eval(Object... args) throws Exception {
		return !this.adaptedPredicate.eval(args);
	}
	
	@Override
	public Predicate negated() {
		return adaptedPredicate;
	}
	
}