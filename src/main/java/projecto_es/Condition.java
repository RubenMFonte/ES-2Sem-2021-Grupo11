package projecto_es;

public class Condition {

	private Metrics metric;
	private NumericOperator no;
	private int threshold;

	public Condition(Metrics metric, NumericOperator no, int threshold) {
		this.metric = metric;
		this.no = no;
		this.threshold = threshold;
	}

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
			no = NumericOperator.EQ;
		} else if (values[1].equals(">") || values[1].equals("GT")) {
			no = NumericOperator.GT;
		} else if (values[1].equals("<") || values[1].equals("LT")) {
			no = NumericOperator.LT;
		} else if (values[1].equals(">=") || values[1].equals("GE")) {
			no = NumericOperator.GE;
		} else if (values[1].equals("<=") || values[1].equals("LE")) {
			no = NumericOperator.LE;
		} else if (values[1].equals("!=") || values[1].equals("NE")) {
			no = NumericOperator.NE;
		} else
			System.out.println("Erro no Numeric Operator");

		threshold = Integer.parseInt(values[2]);
	}

	public Metrics getMetric() {
		return metric;
	}

	public NumericOperator getNumericOperator() {
		return no;
	}

	public int getThreshold() {
		return threshold;
	}

	@Override
	public String toString() {
		return metric + ":" + no + ":" + threshold;
	}

	public String toStringFormatted() {
		return metric + " " + no.getComparator() + " " + threshold;
	}
}
