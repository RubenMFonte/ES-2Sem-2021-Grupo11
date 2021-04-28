package projecto_es;

public class CodeSmellStatistics {

	private String codeSmell;
	private int false_positive;
	private int true_positive;
	private int false_negative;
	private int true_negative;

	public CodeSmellStatistics(String codeSmell, int false_positive, int true_positive, int false_negative,
			int true_negative) {
		this.codeSmell = codeSmell;
		this.false_positive = false_positive;
		this.true_positive = true_positive;
		this.false_negative = false_negative;
		this.true_negative = true_negative;
	}

}
