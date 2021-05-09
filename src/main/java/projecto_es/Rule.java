package projecto_es;

import java.util.ArrayList;
import java.util.List;

public class Rule {

	private ArrayList<String> list;
	private ArrayList<LogicalOperator> logicalOperators;
	private ConditionsList conditionsList = new ConditionsList();

	// String that is received follows the format:
	// "id:codeSmell:active:Metrics:NumericOperator:threshold:LogicalOperator:Metrics:NumericOperator:threshold:LogicalOperator:..."
	private static final int INDEX_ID = 0;
	private static final int INDEX_CODESMELL = 1;
	private static final int INDEX_IS_ACTIVATED = 2;
	private static final int INDEX_CONDITIONS_BEGIN = 3;
	
	private static final int OFFSET_NUMERIC_OPERATOR = 1;
	private static final int OFFSET_THRESHOLD = 2;
	private static final int OFFSET_LOGIC_OPERATOR = 3;
	
	private static final int CONDITION_LENGTH = 3;
	
	public static void main(String[] args) {
		Rule teste = new Rule("1:God Class:active:LOC_CLASS:==:5");
		System.out.println(teste.getCodeSmell());
	}
	
	public Rule(String rule) {
		list = new ArrayList<>();
		String[] values = rule.split(":");
		
		list.add(values[Rule.INDEX_ID]);
		list.add(values[Rule.INDEX_CODESMELL]);
		list.add(values[Rule.INDEX_IS_ACTIVATED]);
		
		Condition condition;
		conditionsList.setConditions(new ArrayList<>());
		logicalOperators = new ArrayList<>();
		
		for (int i = Rule.INDEX_CONDITIONS_BEGIN; i < values.length; i += Rule.CONDITION_LENGTH) {
			
			condition = new Condition(values[i] + ":" + values[i + Rule.OFFSET_NUMERIC_OPERATOR] + ":" + values[i + Rule.OFFSET_THRESHOLD]);
			
			addCondition(condition);
			
			if(values.length <= i + Rule.OFFSET_LOGIC_OPERATOR) break;
			
			else {
				
				if( values[i + Rule.OFFSET_LOGIC_OPERATOR].equals("AND") ) addOperator(LogicalOperator.AND);
				else if( values[i + Rule.OFFSET_LOGIC_OPERATOR].equals("OR") ) addOperator(LogicalOperator.OR);
			}
		}
	}

	public void addCondition(Condition condition) {
		conditionsList.getConditions().add(condition);
		list.add(condition.toString());
	}

	public void addOperator(LogicalOperator operator) {
		list.add(operator.toString());
		logicalOperators.add(operator);
	}

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

	public void changeID(int id) {
		list.set(0, Integer.toString(id));
	}

	public String getID() {
		return list.get(0);
	}

	public boolean isActive() {
		return Boolean.parseBoolean(list.get(2));
	}

	public void switchActive() {
		list.set(INDEX_IS_ACTIVATED, list.get(Rule.INDEX_IS_ACTIVATED).equals("true") ? "false" : "true");
	}

	public ArrayList<String> getList() {
		return list;
	}

	public int numberOfConditions() {
		return conditionsList.numberOfConditions();
	}

	public Condition getCondition(int index) {
		return conditionsList.getCondition(index);
	}
	
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

	public LogicalOperator getLogicalOperator(int index) {
		if (index < logicalOperators.size())
			return logicalOperators.get(index);
		else {
			System.out.println("Erro: Index maior do que o tamanho da lista de condições");
			return null;
		}
	}
	
	public String getCodeSmell() {
		return list.get(1);
	}
	
	public String getHeader()
	{
		return list.get(0) + ":" + list.get(1) + ":" + list.get(2);
	}
	
	public List<Condition> getConditions(){
		return conditionsList.getConditions();
	}
	
	public List<LogicalOperator> getLogicalOperators(){
		return logicalOperators;
	}
	
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

}
