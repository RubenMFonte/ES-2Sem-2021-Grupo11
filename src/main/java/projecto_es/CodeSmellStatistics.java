package projecto_es;

public class CodeSmellStatistics {
	/**
	 * Name of the code smell
	 */
	private String codeSmell;
	/**
	 * When the user rule gets a true on the code smell but the specialist have at false
	 */
	private int false_positive;
	/**
	 * When the user rule gets a true on the code smell but the specialist have at true
	 */
	private int true_positive;
	/**
	 * When the user rule gets a false on the code smell but the specialist have at true
	 */
	private int false_negative;
	/**
	 * When the user rule gets a false on the code smell but the specialist have at false
	 */
	private int true_negative;
	/**
	 * Creates a CodeSmellsStatistics using the arguments
	 * @param codeSmell {@link codeSmell}
	 * @param false_positive {@link false_positive}
	 * @param true_positive {@link true_positive}
	 * @param false_negative {@link false_negative}
	 * @param true_negative {@link true_negative}
	 */
	public CodeSmellStatistics(String codeSmell, int false_positive, int true_positive, int false_negative,
			int true_negative) {
		this.codeSmell = codeSmell;
		this.false_positive = false_positive;
		this.true_positive = true_positive;
		this.false_negative = false_negative;
		this.true_negative = true_negative;
	}
	/**
	 * Returns {@link codeSmell}
	 * @return {@link codeSmell}
	 */
	public String getCodeSmell() {
		return codeSmell;
	}
	/**
	 * Returns {@link false_positive}
	 * @return {@link false_positive}
	 */
	public int getFalse_positive() {
		return false_positive;
	}
	/**
	 * Returns {@link true_positive}
	 * @return {@link true_positive}
	 */
	public int getTrue_positive() {
		return true_positive;
	}
	/**
	 * Returns {@link false_negative}
	 * @return {@link false_negative}
	 */
	public int getFalse_negative() {
		return false_negative;
	}
	/**
	 * Returns {@link true_negative}
	 * @return	{@link true_negative}
	 */
	public int getTrue_negative() {
		return true_negative;
	}
	
	/**
	 * Increases the {@link false_positive}
	 */
	public void increase_falsePositive() {
		this.false_positive++;
	}
	/**
	 * Increases the {@link true_positive}
	 */
	public void increase_truePositive() {
		this.true_positive++;
	}
	/**
	 * Increases the {@link false_negative}
	 */
	public void increase_falseNegative() {
		this.false_negative++;
	}
	/**
	 * Increases the {@link true_negative}
	 */
	public void increase_trueNegative() {
		this.true_negative++;
	}

}
