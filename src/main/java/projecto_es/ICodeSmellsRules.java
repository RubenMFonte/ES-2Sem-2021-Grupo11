package projecto_es;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JList;
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

public class ICodeSmellsRules {

	private JFrame frmCodeSmells;
	private JTable rules;
	private JTable activatedRule;
	private JButton newRule;
	private JButton editRule;
	private JButton export;
	private JButton activateRule;
	private JButton filter;
	private JList codeSmells;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
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

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1 = new JScrollPane();

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
		
		setFilterButton();
		
//		draw();
		
		createTable(allRules);
		String[] columnNames = { "Regra", "Condição" };
		String[][] activeRule = new String[1][2];
		for (int i = 0; i < allRules.size(); i++) {
			if (allRules.get(i).isActive())
				activeRule[0][0] = allRules.get(i).getID();
			activeRule[0][1] = allRules.get(i).onlyConditions();
		}
		activatedRule = new JTable(activeRule, columnNames);
		activatedRule.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(activatedRule);
		scrollPane.setViewportView(rules);

		selectAction();
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
		rules.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rules.setFillsViewportHeight(true);
		printRows();;
		frmCodeSmells.add(rules);
		System.out.println("add");
		draw();
//		frmCodeSmells.add(rules);
	}
	
	public void printRows() {
		for(int i = 0; i < rules.getRowCount(); i++) {
			System.out.println("linha " + i + " = " + rules.getValueAt(i, 0) + ":" + rules.getValueAt(i, 1));
		}
	}
	public void selectAction() {
		codeSmells.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				System.out.println("to aqui");
				int index = codeSmells.getSelectedIndex();
				String cs = allCodeSmells.get(index);
				createTable(filterRule(cs));
			}
		});
		;
	}
 
	public void setFilterButton() {
		filter = new JButton("filtrar");
		
//		filter.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("to aqui");
//				int index = codeSmells.getSelectedIndex();
//				String cs = allCodeSmells.get(index);
//				updateTable(filterRule(cs));
//				System.out.println(filterRule(cs));
//				
//			}
//		});
	}
	
	public void draw() {
		GroupLayout groupLayout = new GroupLayout(frmCodeSmells.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(23)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(labelCodeSmellsDisp)
										.addComponent(codeSmells, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE))
									.addGap(18))
								.addComponent(activeRule, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(34)
							.addComponent(filter)))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(newRule, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addComponent(editRule, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addComponent(export, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addComponent(activateRule, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(592, Short.MAX_VALUE)
					.addComponent(ruleHistory, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addGap(345))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(ruleHistory)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(labelCodeSmellsDisp, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(codeSmells, GroupLayout.PREFERRED_SIZE, 496, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(filter)
							.addGap(72)
							.addComponent(activeRule)
							.addGap(10))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(newRule)
									.addGap(18)
									.addComponent(editRule, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
									.addGap(500)
									.addComponent(export, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
								.addComponent(activateRule, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(45))
		);
		
		frmCodeSmells.getContentPane().setLayout(groupLayout);
		frmCodeSmells.repaint();
		System.out.println("drawed");
	}
}
