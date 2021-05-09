package projecto_es;


import java.util.ArrayList;

public class ConditionsList {
	private ArrayList<Condition> conditions;

	public ArrayList<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(ArrayList<Condition> conditions) {
		this.conditions = conditions;
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
}