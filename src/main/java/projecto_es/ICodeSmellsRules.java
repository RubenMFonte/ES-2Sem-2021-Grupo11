package projecto_es;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextPane;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;

public class ICodeSmellsRules {

	private JFrame frmCodeSmells;
	private JTable rules;
	private JTable activatedRule;
	private JButton newRule;
	private JButton editRule;
	private JButton export;
	private JButton activateRule;
	private JList codeSmells;
	private JScrollPane rulesScrollPane;
	private JScrollPane activatedRuleScrollPane;
	private JLabel labelCodeSmellsDisp;
	private JLabel ruleHistory;
	private JLabel activeRule;
	private List<Rule> allRules;
	private List<String> allCodeSmells;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ICodeSmellsRules window = new ICodeSmellsRules();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws FileNotFoundException
	 */
	public ICodeSmellsRules() throws FileNotFoundException {
		allRules = new ArrayList<>();
		File saveRule = new File("saveRule.txt");
		Scanner myReader = new Scanner(saveRule);
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			allRules.add(new Rule(data));
		}
		myReader.close();
		initialize();
		frmCodeSmells.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCodeSmells = new JFrame();
		frmCodeSmells.setResizable(false);
		frmCodeSmells.setTitle("Code Smells & Rules");
		frmCodeSmells.setBounds(100, 100, 1100, 800);
		frmCodeSmells.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		newRule = new JButton("Criar Regra");
		newRule.setFont(new Font("Arial", Font.PLAIN, 12));

		boolean isUniq;
		allCodeSmells = new ArrayList<>();
		allCodeSmells.add("All");
		for (int i = 0; i < allRules.size(); i++) {
			isUniq = true;
			for (int j = 0; j < allCodeSmells.size(); j++) {
				if (allCodeSmells.get(j).equals(allRules.get(i).getCodeSmell())) {
					isUniq = false;
					break;
				}
			}
			if (isUniq) {
				allCodeSmells.add(allRules.get(i).getCodeSmell());
			}

		}
		String[] allCodeSmellsJL = new String[allCodeSmells.size()];
		for (int i = 0; i < allCodeSmells.size(); i++) {
			allCodeSmellsJL[i] = allCodeSmells.get(i);
		}
		codeSmells = new JList(allCodeSmellsJL);
		codeSmells.setBorder(new LineBorder(new Color(0, 0, 0)));
		codeSmells.setSelectedIndex(0);

		rulesScrollPane = new JScrollPane();
		rulesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		activatedRuleScrollPane = new JScrollPane();

		labelCodeSmellsDisp = new JLabel("Code Smells disponiveis");
		labelCodeSmellsDisp.setFont(new Font("Arial", Font.PLAIN, 17));

		editRule = new JButton("Editar Regra");
		editRule.setFont(new Font("Arial", Font.PLAIN, 12));

		export = new JButton("Exportar");
		export.setFont(new Font("Arial", Font.PLAIN, 12));

		activateRule = new JButton("Activar regra");
		activateRule.setFont(new Font("Arial", Font.PLAIN, 12));

		ruleHistory = new JLabel("Hist\u00F3rico de Regras ");
		ruleHistory.setFont(new Font("Arial", Font.PLAIN, 17));

		activeRule = new JLabel("Regra Activa:");
		activeRule.setFont(new Font("Arial", Font.PLAIN, 24));

		draw();

		createTable(allRules);
		createActivedRule(null);

		String[] columnNames = { "Regra", "Condição" };
		String[][] activeRule = new String[1][2];
		for (int i = 0; i < allRules.size(); i++) {
			if (allRules.get(i).isActive())
				activeRule[0][0] = allRules.get(i).getID();
			activeRule[0][1] = allRules.get(i).onlyConditions();
		}
		activatedRule = new JTable(activeRule, columnNames);
		activatedRule.setFillsViewportHeight(true);
		activatedRuleScrollPane.setViewportView(activatedRule);
		rulesScrollPane.setViewportView(rules);


		selectAction();
		createRule();
		editRule();
	}

//	public List<Integer> filterRule(String codeSmell) {
//		List<Integer> listFiltered = new ArrayList<>();
//		for (int i = 0; i < allRules.size(); i++) {
//			if (allRules.get(i).getCodeSmell().equals(codeSmell))
//				listFiltered.add(Integer.parseInt(allRules.get(i).getID()));
//		}
//		return listFiltered;
//	}

	public List<Rule> filterRule(String codeSmell) {
		List<Rule> listFiltered = new ArrayList<>();
		for (int i = 0; i < allRules.size(); i++) {
			if (allRules.get(i).getCodeSmell().equals(codeSmell))
				listFiltered.add(allRules.get(i));
		}
		return listFiltered;
	}

	public void createTable(List<Rule> rulesList) {
		String[] columnNames = { "Regra", "Condição" };
		String[][] allRulesJT = new String[rulesList.size()][2];
		for (int i = 0; i < rulesList.size(); i++) {
			allRulesJT[i][0] = rulesList.get(i).getID();
			allRulesJT[i][1] = rulesList.get(i).onlyConditions();
		}
		rules = new JTable(allRulesJT, columnNames);
		rules.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rules.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rules.setFillsViewportHeight(true);
		rules.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//		printRows();
		rulesScrollPane.setViewportView(rules);

	}

	public void createActivedRule(Rule rule) {
		String[] columnNames = { "Regra", "Condição" };
		String[][] activeRule = new String[1][2];
		if (rule == null) {
			activeRule[0][0] = "Selecione um CodeSmell";
			activeRule[0][1] = "";
		} else {
			activeRule[0][0] = rule.getID();
			activeRule[0][1] = rule.onlyConditions();
		}
		activatedRule = new JTable(activeRule,columnNames);
		activatedRule.setEnabled(false);
		activatedRule.setFillsViewportHeight(true);
		activatedRuleScrollPane.setViewportView(activatedRule);
	}

	public void printRows() {
		for (int i = 0; i < rules.getRowCount(); i++) {
			System.out.println("linha " + i + " = " + rules.getValueAt(i, 0) + ":" + rules.getValueAt(i, 1));
		}
	}

	public void selectAction() {
		codeSmells.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int index = codeSmells.getSelectedIndex();
				String cs = allCodeSmells.get(index);
				List<Rule> list_CS = filterRule(cs);
				if (cs.equals("All")) {
					createTable(allRules);
					createActivedRule(null);
				} else {
					createTable(list_CS);
					createActivedRule(findActiveCodeSmell(list_CS));
				}

			}
		});
		;
	}

	public Rule findActiveCodeSmell(List<Rule> filterRule) {
		for (int i = 0; i < filterRule.size(); i++) {
			if (filterRule.get(i).isActive() == true)
				return filterRule.get(i);
		}
		return null;
	}

	public void draw() {
		GroupLayout groupLayout = new GroupLayout(frmCodeSmells.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addGap(23)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
						.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(labelCodeSmellsDisp)
								.addComponent(codeSmells, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE))
						.addGap(18))
						.addComponent(activeRule, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(activatedRuleScrollPane, GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
						.addComponent(rulesScrollPane, GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(newRule, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addComponent(editRule, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addComponent(export, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addComponent(activateRule, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
				.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup().addContainerGap(592, Short.MAX_VALUE)
						.addComponent(ruleHistory, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
						.addGap(345)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addGap(6).addComponent(ruleHistory).addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
						.createSequentialGroup()
						.addComponent(labelCodeSmellsDisp, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(codeSmells, GroupLayout.PREFERRED_SIZE, 496, GroupLayout.PREFERRED_SIZE)
						.addGap(121).addComponent(activeRule).addGap(10))
						.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
								.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup().addComponent(newRule).addGap(18)
										.addComponent(editRule, GroupLayout.PREFERRED_SIZE, 23,
												GroupLayout.PREFERRED_SIZE)
										.addGap(500).addComponent(export, GroupLayout.PREFERRED_SIZE, 23,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(rulesScrollPane, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)).addGap(18)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(activatedRuleScrollPane, GroupLayout.PREFERRED_SIZE, 48,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(activateRule, GroupLayout.PREFERRED_SIZE, 23,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)))
				.addGap(45)));

		frmCodeSmells.getContentPane().setLayout(groupLayout);
	}

	public void popUp(String popUp) {
		JFrame parent = new JFrame();
		JOptionPane.showMessageDialog(parent, popUp);
	}
	
	public void createRule() {
		newRule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(codeSmells.getSelectedIndex()>0) {
					String codeSmell = allCodeSmells.get(codeSmells.getSelectedIndex());
					frmCodeSmells.dispose();
//					IDetetionParameters frame = new IDetetionParameters(codeSmell);
				}else popUp("Escolha um Code Smell antes de criar uma regra.");
			}
		});
		
	}
	
	public void editRule() {
		editRule.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rules.getSelectedRow()>-1) {
					Rule rule = allRules.get(rules.getSelectedRow());
					frmCodeSmells.dispose();
//					IDetetionParameters frame = new IDetetionParameters(rule);
				}else popUp("Escolha a regra que pretende editar.");
				
			}
			
		});
	}	
}
