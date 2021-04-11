package projecto_es;

public enum NumericOperator {
	EQ ("="),
	GT (">"),
	LT ("<"),
	GE (">="),
	LE ("<=")
	;
	
	private final String comparator;

	NumericOperator(String comparator) {
		this.comparator=comparator;
	}

	public String getComparator() {
		return comparator;
	}
	
	
}
