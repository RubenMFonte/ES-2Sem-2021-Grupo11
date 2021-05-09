package projecto_es;

public class Condition {
	/**
	 * {@link Metrics} being used in the condition
	 */
	private Metrics metric;
	/**
	 * {@link NumericOperator} being used in the condition
	 */
	private NumericOperator numericOperator;
	/**
	 * Limit of the metric
	 */
	private int threshold;
	/**
	 * Creates a Condition with the arguments
	 * @param metric {@link metric}
	 * @param numericOperator {@link numericOperator}
	 * @param threshold {@link threshold}
	 */
	public Condition(Metrics metric, NumericOperator numericOperator, int threshold) {
		this.metric = metric;
		this.numericOperator = numericOperator;
		this.threshold = threshold;
	}
	/**
	 * Creates a Condition from a formatted string
	 * @param condition Formatted string with the Condition variables
	 */
	public Condition(String condition) {
		String[] values = condition.split(":");

		if (values[0].equals("LOC_CLASS")) {
			metric = Metrics.LOC_CLASS;
		} else if (values[0].equals("NOM_CLASS")) {
			metric = Metrics.NOM_CLASS;
		} else if (values[0].equals("WMC_CLASS")) {
			metric = Metrics.WMC_CLASS;
		} else if (values[0].equals("LOC_METHOD")) {
			metric = Metrics.LOC_METHOD;
		} else if (values[0].equals("CYCLO_METHOD")) {
			metric = Metrics.CYCLO_METHOD;
		} else
			System.out.println("Erro no metrics");

		if (values[1].equals("==") || values[1].equals("EQ")) {
			numericOperator = NumericOperator.EQ;
		} else if (values[1].equals(">") || values[1].equals("GT")) {
			numericOperator = NumericOperator.GT;
		} else if (values[1].equals("<") || values[1].equals("LT")) {
			numericOperator = NumericOperator.LT;
		} else if (values[1].equals(">=") || values[1].equals("GE")) {
			numericOperator = NumericOperator.GE;
		} else if (values[1].equals("<=") || values[1].equals("LE")) {
			numericOperator = NumericOperator.LE;
		} else if (values[1].equals("!=") || values[1].equals("NE")) {
			numericOperator = NumericOperator.NE;
		} else
			System.out.println("Erro no Numeric Operator");

		threshold = Integer.parseInt(values[2]);
	}
	/**
	 * Returns {@link metric}
	 * @return {@link metric}
	 */
	public Metrics getMetric() {
		return metric;
	}
	/**
	 * Returns {@link numericOperator}
	 * @return {@link numericOperator}
	 */
	public NumericOperator getNumericOperator() {
		return numericOperator;
	}
	/**
	 * Returns {@link threshold}
	 * @return {@link threshold}
	 */
	public int getThreshold() {
		return threshold;
	}
	/**
	 * Overrides the default method toString() to return a formatted string
	 */
	@Override
	public String toString() {
		return metric + ":" + numericOperator + ":" + threshold;
	}
	/**
	 * Returns a string formatted to be read by the user
	 * @return Formatted string
	 */
	public String toStringFormatted() {
		return metric + " " + numericOperator.getComparator() + " " + threshold;
	}
}
