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

import projecto_es.ConditionJPanel;
import projecto_es.Rule;

public class IDetetionParameters {

	private JFrame frame;
	private JPanel panel;
	private JPanel panelSOUTH;
	private JScrollPane scrollPane;
	private List<ConditionJPanel> conditionPanels = new ArrayList<ConditionJPanel>();
	private JPanel panelsouthLEFT;
	private JPanel panelsouthRIGHT;
	private JButton insertCond = new JButton("Insert New Condition");
	private JButton removeCond = new JButton("Remove Last Condition");
	private JButton cancelButton = new JButton("Cancel");
	private JButton saveButton = new JButton("Save");
	private String code_smell;
	private boolean newRule = false;
	private Rule rule;

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

	public IDetetionParameters(String code_smell) {
		this(new Rule(":" + code_smell + ":false::::"), code_smell);
		newRule = true;
		this.code_smell = code_smell;
	}

	public IDetetionParameters(Rule rule_arg, String code_smell) {
		this.rule = rule_arg;
		this.code_smell = code_smell;
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Detetion Parameters");
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

	private void exitWindow() {
		frame.dispose();
		try {
			ICodeSmellsRules newFrame = new ICodeSmellsRules();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

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

	private String getRuleString() {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append(rule.getHeader());

		for (int i = 0; i < conditionPanels.size(); i++) {
			stringBuffer.append(":" + conditionPanels.get(i).getConditionAsString());
			System.out.println(conditionPanels.get(i).getConditionAsString().length());
		}

		return stringBuffer.toString();
	}

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

	private String verifyLastLogicalOperator(String rule) {
//		12:God_class:false:NOM_CLASS:EQ:5:OR
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

	private boolean validateValues() {

		boolean isValid = true;

		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("Please fill in the required values:\n");

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