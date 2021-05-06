package projecto_es;

import java.util.ArrayList;
import java.util.List;

public class Rule {

	// Este array é do formato ArrayList[(id),(code
	// smell),(active),(condition1),(logicalOperator),(condition2),...]
	private ArrayList<String> list;
	private ArrayList<Condition> conditions;
	private ArrayList<LogicalOperator> logicalOperator;

	// Assume-se que a string que entra nesta função +e do formato
	// "id:codeSmell:active:Metrics:NumericOperator:threshold:LogicalOperator:Metrics:NumericOperator:threshold:LogicalOperator:..."
	public Rule(String rule) {
		System.out.println("aqui:" + rule);
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

	public void addCondition(LogicalOperator operator, Condition condition) {
		list.add(operator.toString());
		list.add(condition.toString());
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
		if(list.get(2).equals("true")) {
			list.set(2, "false");
		}else list.set(2, "true");
	}

	public ArrayList<String> getList() {
		return list;
	}

	public int numberOfConditions() {
		return conditions.size();
	}

	public Condition getCondition(int index) {
		if (index < conditions.size())
			return conditions.get(index);
		else {
			System.out.println("Erro: Index maior do que o tamanho da lista de condições");
			return null;
		}
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
		if (index < logicalOperator.size())
			return logicalOperator.get(index);
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
	
	// Necessárias
	public List<Condition> getConditions(){
		return conditions;
	}
	
	public List<LogicalOperator> getLogicalOperators(){
		return logicalOperator;
	}
	//
	
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
