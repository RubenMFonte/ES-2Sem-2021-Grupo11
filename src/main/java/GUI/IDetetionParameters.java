package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import projecto_es.Rule;

public class IDetetionParameters {

	private JFrame frame;
	private JPanel panel;
	private JPanel panelSOUTH;
	private JScrollPane scrollPane;
	/**
	 * List of {@link ConditionJPanel}
	 */
	private List<ConditionJPanel> conditionPanels = new ArrayList<ConditionJPanel>();
	private JPanel panelsouthLEFT;
	private JPanel panelsouthRIGHT;
	private JButton insertCond = new JButton("Inserir nova condição");
	private JButton removeCond = new JButton("Remove última condição");
	private JButton cancelButton = new JButton("Cancelar");
	private JButton saveButton = new JButton("Salvar");
	/**
	 *  String with the name of the code smell
	 */
	
	private String code_smell;
	/**
	 * Boolean that indicates if it is a new Rule or one that is going to be edited
	 */
	private boolean newRule = false;
	/**
	 * The rule that is going to be created or edited
	 */
	private Rule rule;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IDetetionParameters window = new IDetetionParameters("God_class");

					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Constructor that initialize the attribute rule and code_smell. Used when the user creates a new Rule
	 * @param code_smell name of the code smell
	 */

	public IDetetionParameters(String code_smell) {
		this(new Rule(":" + code_smell + ":false::::"), code_smell);
		newRule = true;
		this.code_smell = code_smell;
	}
	
	/**
	 * Constructor that initialize the attribute rule and code_smell. Used when the user edits a Rule
	 * @param rule_arg parameter 
	 * @param code_smell name of the code smell
	 */

	public IDetetionParameters(Rule rule_arg, String code_smell) {
		this.rule = rule_arg;
		this.code_smell = code_smell;
		initialize();
	}
	
	/**
	 * Initialize the content of the frame
	 */
	
	private void initialize() {
		frame = new JFrame("Criar Regra");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		panelSOUTH = new JPanel();
		panelSOUTH.setLayout(new BorderLayout(0, 0));

		panelsouthLEFT = new JPanel();
		panelsouthLEFT.add(insertCond);
		panelsouthLEFT.add(removeCond);
		panelSOUTH.add(panelsouthLEFT, BorderLayout.WEST);

		panelsouthRIGHT = new JPanel();
		panelsouthRIGHT.add(cancelButton);
		panelsouthRIGHT.add(saveButton);
		panelSOUTH.add(panelsouthRIGHT, BorderLayout.EAST);

		scrollPane = new JScrollPane(panel);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		List<String> conditionList = rule.getConditionsArray();

		int numOfConditions = conditionList.size();

		if (numOfConditions == 0)
			addConditionPanel("::"); // Empty condition
		else {
			for (int i = 0; i < numOfConditions; i++) {
				System.out.println("aloooo: " + numOfConditions);
				addConditionPanel(conditionList.get(i));
			}
		}

		checkForNewCond();
		checkForRemCond();

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitWindow();
			}
		});

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (!validateValues())
					return;

				if (newRule)
					saveNewRule();
				else
					updateRule();

				exitWindow();
			}
		});

		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(panelSOUTH, BorderLayout.SOUTH);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

	}
	
	/**
	 *Method to close the class frame and return to the ICodeSmellsRule frame.  
	 */

	private void exitWindow() {
		frame.dispose();
		try {
			ICodeSmellsRules newFrame = new ICodeSmellsRules();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that write to the saveRule.txt file the new version of the Rule
	 */
	
	private void updateRule() {
		File rulesFile = new File("saveRule.txt");

		Scanner myReader;
		StringBuffer stringBuffer = new StringBuffer();

		try {
			myReader = new Scanner(rulesFile);

			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				Rule ruleInFile = new Rule(line);

				if (rule.getID().equals(ruleInFile.getID())) {
					line = changeString(getRuleString());
				}

				stringBuffer.append(line + "\n");
			}

			myReader.close();

			FileWriter writer;

			try {

				writer = new FileWriter(rulesFile);
				writer.write(stringBuffer.toString());
				writer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that write to the saveRule.txt the new Rule created
	 */

	private void saveNewRule() {
		File rulesFile = new File("saveRule.txt");

		Scanner myReader;
		StringBuffer stringBuffer = new StringBuffer();

		try {
			myReader = new Scanner(rulesFile);

			int newId = -1;
			String line = "";

			while (myReader.hasNextLine()) {
				line = myReader.nextLine();
				stringBuffer.append(line + "\n");
			}

			myReader.close();
			if (line.equals("")) {
				newId = 1;
			} else {
				Rule ruleInFile = new Rule(line);

				newId = Integer.parseInt(ruleInFile.getID()) + 1;
			}
			stringBuffer.append(newId + changeString(getRuleString()));

			FileWriter writer;

			try {

				writer = new FileWriter(rulesFile);
				writer.write(stringBuffer.toString());
				writer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Method to filter the String added to the file saveRule.txt. It is responsible to remove ":" character.
	 * @param beforeChange String before being filtered
	 * @return String after being filtered
	 */

	private String changeString(String beforeChange) {
		int lengthOfBeforeChange = beforeChange.length();
		String afterChange;
		if (beforeChange.substring(lengthOfBeforeChange - 1, lengthOfBeforeChange).equals(":")) {
			afterChange = beforeChange.substring(0, lengthOfBeforeChange - 1);
		} else {
			afterChange = beforeChange;
		}
		afterChange = verifyLastLogicalOperator(afterChange);
		return afterChange;
	}
	
	/**
	 * Method responsible to write the String that is going to be written in the saveRule.txt file
	 * @return
	 */

	private String getRuleString() {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append(rule.getHeader());

		for (int i = 0; i < conditionPanels.size(); i++) {
			stringBuffer.append(":" + conditionPanels.get(i).getConditionAsString());
			System.out.println(conditionPanels.get(i).getConditionAsString().length());
		}

		return stringBuffer.toString();
	}
	/**
	 * Method responsible to add  a new ConditionJPanel to the list conditionPanel}. The new  ConditionJPanel will be added to the
	 * frame and the user will be able to add a new condition to the Rule being created or edited.
	 * @param condition appended previously
	 */

	private void addConditionPanel(String condition) {
		if (conditionPanels.size() >= 1) {
			JComboBox comobox = conditionPanels.get(conditionPanels.size() - 1).getLoperatorsAvailable();
			comobox.setEnabled(true);
		}
		String conditionNumber = "Condition " + (conditionPanels.size() + 1);
		String conditionElements[] = condition.split(":");

		ConditionJPanel iic;

		if (conditionElements.length > 1) {
			iic = new ConditionJPanel(conditionNumber, conditionElements[0], conditionElements[1], conditionElements[2],
					conditionElements.length > 3 ? conditionElements[3] : "", code_smell);
		} else {
			iic = new ConditionJPanel(conditionNumber, code_smell);
		}

		panel.add(iic);
		conditionPanels.add(iic);
	}
	
	/**
	 * Method to add an action to the button {@link insertCond}. The action on the Button will validate if all the fields have been filled and then will add
	 * a new {@link ConditionJPanel}
	 */

	private void checkForNewCond() {
		insertCond.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (validateValues())
					addConditionPanel("");

				panel.revalidate();
			}
		});
	}
	
	/**
	 * Method to add an action to the button {@link removeCond}. The action on the button is responsible for removing the last {@link ConditionJPanel} 
	 * inserted. It will also verify if the Condition can be remove, considering a Rule must have at least one condition
	 */

	private void checkForRemCond() {
		removeCond.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (conditionPanels.size() == 1) {
					System.out.println("Cannot remove anymore!");
				} else {
					Component lastComp = conditionPanels.get(conditionPanels.size() - 1);
					panel.remove(lastComp);
					conditionPanels.remove(conditionPanels.size() - 1);
					JComboBox comobox = conditionPanels.get(conditionPanels.size() - 1).getLoperatorsAvailable();
					comobox.setSelectedIndex(0);
					comobox.setEnabled(false);
				}

				panel.repaint();
				panel.revalidate();

			}
		});
	}
	/**
	 * Method that verifies if the last element of the String is a Logical Operator
	 * @param rule String with the content of the Rule
	 * @return final String that is going to be added in the saveRule.txt 
	 */

	private String verifyLastLogicalOperator(String rule) {
		String[] split = rule.split(":");
		switch (split[split.length - 1]) {
		case "OR":
			return removeLastLogicalOperator(rule);
		case "AND":
			return removeLastLogicalOperator(rule);
		default:
			return rule;
		}
	}
	/**
	 * Method that filters the String that is going to be added in the saveRule.txt. The method is responsible to remove the Logical Operator at the end
	 * the String
	 * @param rule String with the content of the Rule
	 * @return Rule filtered
	 */

	private String removeLastLogicalOperator(String rule) {
		String[] split = rule.split(":");
		String return_rule = "";
		for (int i = 0; i < split.length - 1; i++) {
			if (i != 0) {
				return_rule = return_rule + ":" + split[i];
			} else {
				return_rule += split[0];

			}
		}
		return return_rule;
	}
	
	/**
	 * This Method will verify if all the Fields of a {@link ConditionJPanel} have been filled
	 * @return
	 */

	private boolean validateValues() {

		boolean isValid = true;

		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("Por favor preencha os campos necesarios:\n");

		for (int i = 0; i < conditionPanels.size(); i++) {
			boolean validateLogicOperator = true;

			if (i == conditionPanels.size() - 1)
				validateLogicOperator = false;

			if (!conditionPanels.get(i).validatePanel(validateLogicOperator, errorMessage, i + 1))
				isValid = false;
		}

		if (!isValid)
			JOptionPane.showMessageDialog(frame, errorMessage.toString(), "Missing Values", JOptionPane.ERROR_MESSAGE);

		return isValid;
	}

}