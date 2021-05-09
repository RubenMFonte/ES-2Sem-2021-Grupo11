package projecto_es;

import java.util.ArrayList;
import java.util.List;

public class Rule {

	/**
	 * List with the rule in the format ArrayList[(id),(code smell),(active),(condition1),(logicalOperator),(condition2),...]
	 */ 
	private ArrayList<String> list;
	/**
	 * List of the conditions of the rule
	 */
	private ArrayList<Condition> conditions;
	/**
	 * List of the {@link LogicalOperator} of the rule
	 */
	private ArrayList<LogicalOperator> logicalOperator;

	// Assume-se que a string que entra nesta função é do formato
	// "id:codeSmell:active:Metrics:NumericOperator:threshold:LogicalOperator:Metrics:NumericOperator:threshold:LogicalOperator:..."
	/**
	 * Creates a Rule with a formatted string
	 * @param rule formatted string (ex.:"id:codeSmell:active:Metrics:NumericOperator:threshold:LogicalOperator:Metrics:NumericOperator:threshold:LogicalOperator:...")
	 */
	public Rule(String rule) {
		list = new ArrayList<>();
		String[] values = rule.split(":");
		list.add(values[0]);
		list.add(values[1]);
		list.add(values[2]);
		LogicalOperator operator;
		Condition condition;
		conditions = new ArrayList<>();
		logicalOperator = new ArrayList<>();
		for (int i = 3; i < values.length; i++) {
			if (i == 5) {
				condition = new Condition(values[i - 2] + ":" + values[i - 1] + ":" + values[i]);
				conditions.add(condition);
				list.add(condition.toString());
			} else if ((i - 1) % 4 == 0) {
				if (values[i - 3].equals("AND")) {
					operator = LogicalOperator.AND;
				} else {
					operator = LogicalOperator.OR;
				}
				condition = new Condition(values[i - 2] + ":" + values[i - 1] + ":" + values[i]);
				logicalOperator.add(operator);
				conditions.add(condition);
				addCondition(operator, condition);

			}
		}
	}
	/**
	 * Adds a {@link LogicalOperator} and a {@link Condition} to the {@link list}
	 * @param operator Logical operator connecting the new condition
	 * @param condition New condition to be added
	 */
	public void addCondition(LogicalOperator operator, Condition condition) {
		list.add(operator.toString());
		list.add(condition.toString());
	}
	/**
	 * Returns all the conditions of the Rule
	 * @return String
	 */
	public String onlyConditions() {
		String string = "";
		for (int i = 3; i < list.size(); i++) {
			if (i % 2 != 0) {
				string = string + list.get(i);
			} else {
				string = string + ":" + list.get(i) + ":";
			}
		}
		return string;
	}
	/**
	 * Change the ID of the Rule with the given id
	 * @param id New ID
	 */
	public void changeID(int id) {
		list.set(0, Integer.toString(id));
	}
	/**
	 * Returns the ID
	 * @return String ID
	 */
	public String getID() {
		return list.get(0);
	}
	/**
	 * Returns a boolean indicating if the rule is active
	 * @return
	 */
	public boolean isActive() {
		return Boolean.parseBoolean(list.get(2));
	}
	/**
	 * Switch the rule to active if it's inactive, or vice-versa
	 */
	public void switchActive() {
		if(list.get(2).equals("true")) {
			list.set(2, "false");
		}else list.set(2, "true");
	}
	/**
	 * Returns {@link list}
	 * @return {@link list}
	 */
	public ArrayList<String> getList() {
		return list;
	}
	/**
	 * Returns the number of conditions
	 * @return Number of conditions
	 */
	public int numberOfConditions() {
		return conditions.size();
	}
	/**
	 * Get a {@link Condition} by giving it's position in the array
	 * @param index Position of the condition
	 * @return Condition, null if the index is bigger than the number of conditions
	 */
	public Condition getCondition(int index) {
		if (index < conditions.size())
			return conditions.get(index);
		else {
			System.out.println("Erro: Index maior do que o tamanho da lista de condições");
			return null;
		}
	}
	/**
	 * Returns a list with just the conditions
	 * @return List of Strings
	 */
	public List<String> getConditionsArray()
	{		
		ArrayList<String> conditions = new ArrayList<>();
		
		for(int i = 3; i < list.size(); i += 2)
		{
			String condition = "" + list.get(i);
			
			if(i < list.size()-1) condition += ":" + list.get(i+1);
			
			conditions.add(condition);
		}
		
		return conditions;
	}
	/**
	 * Get a {@link LogicalOperator} by giving it's position in the array
	 * @param index Position of the LogicalOperator
	 * @return LogicalOperator, null if the index is bigger than the number of LogicalOperators
	 */
	public LogicalOperator getLogicalOperator(int index) {
		if (index < logicalOperator.size())
			return logicalOperator.get(index);
		else {
			System.out.println("Erro: Index maior do que o tamanho da lista de condições");
			return null;
		}
	}
	/**
	 * Return the code smell the rule is for
	 * @return String code smell
	 */
	public String getCodeSmell() {
		return list.get(1);
	}
	/**
	 * Returns the ID, Code smell and if it's active
	 * @return String
	 */
	public String getHeader()
	{
		return list.get(0) + ":" + list.get(1) + ":" + list.get(2);
	}
	
	/**
	 * Returns {@link conditions}
	 * @return {@link conditions}
	 */
	public List<Condition> getConditions(){
		return conditions;
	}
	/**
	 * Returns {@link logicalOperator}
	 * @return {@link logicalOperator}
	 */
	public List<LogicalOperator> getLogicalOperators(){
		return logicalOperator;
	}
	/**
	 * Overrides the default method toString() to return a formatted string
	 */
	@Override
	public String toString() {
		String string;
		string = list.get(0) + ":" + list.get(1) + ":" + list.get(2) + ":";
		for (int i = 3; i < list.size(); i++) {
			if (i % 2 != 0) {
				string = string + list.get(i);
			} else {
				string = string + ":" + list.get(i) + ":";
			}
		}
		return string;
	}
	
	// teste de exemplo
	public static void main(String[] args) {
		String a = "0:God_Class:false:WMC_CLASS:LT:4";
		String b = "LOC_METHOD:EQ:6";
		String c = "CYCLO_METHOD:GT:7";
		String d = a + ":" + "AND" + ":" + b + ":" + "OR" + ":" + c;
		Rule rule = new Rule(d);
		System.out.println(d);
		System.out.println(rule.toString());
		System.out.println(rule.onlyConditions());
		System.out.println(rule.getCondition(2));
		System.out.println("Numero de conditions: " + rule.numberOfConditions());
		rule.changeID(2);
		System.out.println(rule.getCodeSmell());
		System.out.println(rule.toString());
		System.out.println("condition index: " + rule.getCondition(0));

	}

}
