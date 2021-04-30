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

	public String getCodeSmell() {
		return codeSmell;
	}

	public void setCodeSmell(String codeSmell) {
		this.codeSmell = codeSmell;
	}

	public int getFalse_positive() {
		return false_positive;
	}

	public void setFalse_positive(int false_positive) {
		this.false_positive = false_positive;
	}

	public int getTrue_positive() {
		return true_positive;
	}

	public void setTrue_positive(int true_positive) {
		this.true_positive = true_positive;
	}

	public int getFalse_negative() {
		return false_negative;
	}

	public void setFalse_negative(int false_negative) {
		this.false_negative = false_negative;
	}

	public int getTrue_negative() {
		return true_negative;
	}

	public void setTrue_negative(int true_negative) {
		this.true_negative = true_negative;
	}

	public void increase_falsePositive() {
		this.false_positive++;
	}
	
	public void increase_truePositive() {
		this.true_positive++;
	}
	
	public void increase_falseNegative() {
		this.false_positive++;
	}
	
	public void increase_trueNegative() {
		this.true_negative++;
	}
	
	public void printTest() {
		System.out.println("false_positive: " + false_positive);
		System.out.println("true_positive: " + true_positive);
		System.out.println("false_negative: " + false_negative);
		System.out.println("true_negative: " + true_negative);
	}

}
