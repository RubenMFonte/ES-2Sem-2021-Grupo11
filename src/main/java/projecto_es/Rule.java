package projecto_es;

import java.util.ArrayList;

import org.apache.poi.util.SystemOutLogger;

public class Rule {
	
	//Este array é do formato ArrayList[(id),(active),(condition1),(logicalOperator),(condition2),...]
	private ArrayList<String> list;
	
	
	//Assume-se que a string que entra nesta função +e do formato "id:active:Metrics:NumericOperator:threshold:LogicalOperator:Metrics:NumericOperator:threshold:LogicalOperator:..."
	public Rule(String rule) {
		list = new ArrayList<>();
		String[] values = rule.split(":");
		list.add(values[0]);
		list.add(values[1]);
		LogicalOperator operator;
		Condition condition;
		for(int i = 2; i<values.length;i++) {
			if(i==4) {
				condition = new Condition(values[i-2]+":"+values[i-1]+":"+values[i]);
				list.add(condition.toString());
			}else if(i%4==0) {
				if(values[i-3].equals("AND")) {
					operator = LogicalOperator.AND;
				}else {
					operator = LogicalOperator.OR;
				}
				condition = new Condition(values[i-2]+":"+values[i-1]+":"+values[i]);
				addCondition(operator, condition);
				
			}
		}
	}


	public void addCondition(LogicalOperator operator, Condition condition) {
		list.add(operator.toString());
		list.add(condition.toString());
	}
	
	public ArrayList<String> getList() {
		return list;
	}
	
	public String getCondition(int index) {
		return list.get(index*2);
	}
	
	public String getLogicalOperator(int index){
		return list.get(index*2+1);
	}
	
	public String onlyConditions() {
		String string = "";
		for(int i = 2; i<list.size();i++) {
			if(i%2==0) {
				string = string + list.get(i);
			}else{
				string = string + ":" + list.get(i)+":";				
			}
		}
		return string;
	}
	
	@Override
	public String toString() {
		String string;
		string = list.get(0) + ":"+list.get(1)+":";
		for(int i = 2; i<list.size();i++) {
			if(i%2==0) {
				string = string + list.get(i);
			}else{
				string = string + ":" + list.get(i)+":";				
			}
		}
		return string;
	}
	
	
	
	//teste
	public static void main(String[] args) {
		String a = "2:false:WMC_CLASS:LT:4";
		String b = "LOC_METHOD:EQ:6";
		String c = "CYCLO_METHOD:GT:7";
		String d = a+":"+"AND"+":"+b+":"+"OR"+":"+c;
		Rule rule = new Rule(d);
		System.out.println(rule.toString());
		System.out.println(rule.onlyConditions());

	}

}
