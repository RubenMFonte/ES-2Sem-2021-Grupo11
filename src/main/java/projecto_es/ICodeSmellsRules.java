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
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

public class ICodeSmellsRules {

	private JFrame frmCodeSmells;
	private JTable rules;
	private JTable activatedRule;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ICodeSmellsRules window = new ICodeSmellsRules();
					window.frmCodeSmells.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ICodeSmellsRules() {
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
		
		JButton newRule = new JButton("Criar Regra");
		newRule.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JList codeSmells = new JList();
		codeSmells.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lblNewLabel = new JLabel("Code Smells disponiveis");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 17));
		
		JButton btnEditarRegra = new JButton("Editar Regra");
		btnEditarRegra.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton newRule_1_1 = new JButton("Exportar");
		newRule_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton newRule_1_2 = new JButton("Activar regra");
		newRule_1_2.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1 = new JLabel("Hist\u00F3rico de Regras ");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 17));
		
		JLabel lblNewLabel_2 = new JLabel("Regra Activa:");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 24));
		GroupLayout groupLayout = new GroupLayout(frmCodeSmells.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(codeSmells, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE))
							.addGap(18))
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(newRule, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnEditarRegra, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addComponent(newRule_1_1, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addComponent(newRule_1_2, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(592, Short.MAX_VALUE)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addGap(345))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(codeSmells, GroupLayout.PREFERRED_SIZE, 496, GroupLayout.PREFERRED_SIZE)
							.addGap(121)
							.addComponent(lblNewLabel_2)
							.addGap(10))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(newRule)
									.addGap(18)
									.addComponent(btnEditarRegra, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
									.addGap(500)
									.addComponent(newRule_1_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
								.addComponent(newRule_1_2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(45))
		);
		
		activatedRule = new JTable();
		activatedRule.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(activatedRule);
		
		rules = new JTable();
		rules.setFillsViewportHeight(true);
		scrollPane.setViewportView(rules);
		frmCodeSmells.getContentPane().setLayout(groupLayout);
	}
}
