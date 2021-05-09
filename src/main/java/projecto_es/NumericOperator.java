package projecto_es;

public enum NumericOperator {
	EQ ("=="),
	NE ("!="),
	GT (">"),
	LT ("<"),
	GE (">="),
	LE ("<=")
	;
	/**
	 * The mathematical symbol of the enum
	 */
	private final String comparator;
	/**
	 * Creates a NumericOperator given it's symbol
	 * @param comparator Mathematical symbol equivalent
	 */
	NumericOperator(String comparator) {
		this.comparator=comparator;
	}
	/**
	 * Returns {@link comparator}
	 * @return {@link comparator}
	 */
	public String getComparator() {
		return comparator;
	}
	
	
}
