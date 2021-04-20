package projecto_es;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;

public class Teste {

	private JFrame frame;
	private JTable rules;
	private List<Rule> allRules;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Teste window = new Teste();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Teste() throws FileNotFoundException {
		allRules = new ArrayList<>();
		File saveRule = new File("saveRule.txt");
		Scanner myReader = new Scanner(saveRule);
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			allRules.add(new Rule(data));
		}
		myReader.close();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 806, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		createTable(allRules,0);
		setButton();
	}

	public void createTable(List<Rule> rulesList, int a) {
		String[] columnNames = { "Regra", "Condição" };
		String[][] allRulesJT = new String[rulesList.size()][2];
		for (int i = 0; i < rulesList.size() - a; i++) {
			allRulesJT[i][0] = rulesList.get(i).getID();
			allRulesJT[i][1] = rulesList.get(i).onlyConditions();
		}
		rules = new JTable(allRulesJT, columnNames);
		rules.setBounds(223, 86, 393, 197);
		frame.getContentPane().add(rules);
		printRows();
	}

	public void setButton() {
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(75, 295, 85, 21);
		frame.getContentPane().add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createTable(allRules, 1);
			}
		});

	}
	
	public void printRows() {
		for(int i = 0; i < rules.getRowCount(); i++) {
			System.out.println("linha " + i + " = " + rules.getValueAt(i, 0) + ":" + rules.getValueAt(i, 1));
		}
	}

}
