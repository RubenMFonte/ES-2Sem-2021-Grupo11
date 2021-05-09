package GUI;
import java.awt.EventQueue;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

	import javax.swing.JButton;
	import javax.swing.JFileChooser;
	import javax.swing.JFrame;
	import javax.swing.JOptionPane;
	import javax.swing.JTextField;
	import javax.swing.JLabel;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

import projecto_es.Rule;
import projecto_es.StaticFunctions;

import java.awt.Font;
	
	
	
public class IMenu {


		public JFrame frame;
		private JButton create_excel;
		private JButton show_statistics;
		private JButton code_rules;
		private JButton quality_codesmells;
		private JFrame frameMetrics;
		
		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						File f = new File("saveRule.txt");
						if(!f.exists()) {
							f.createNewFile();
							Rule defaultGodClass = new Rule("1:God_class:true:LOC_CLASS:EQ:5:AND:NOM_CLASS:EQ:10:AND:WMC_CLASS:EQ:3");
							Rule defaultLongMethod = new Rule("6:Long_method:false:LOC_METHOD:GT:7");
							StaticFunctions.saveRule(defaultGodClass, f);
							StaticFunctions.saveRule(defaultLongMethod, f);
						}
						IMenu window = new IMenu();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		
		public IMenu() {
			initialize();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			frame = new JFrame();
			frame.setBounds(100, 100, 557, 287);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			frame.setResizable(false);
			
			JLabel lblNewLabel = new JLabel("M\u00E9tricas");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblNewLabel.setBounds(106, 39, 67, 34);
			frame.getContentPane().add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel("CodeSmells");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblNewLabel_1.setBounds(345, 34, 125, 45);
			frame.getContentPane().add(lblNewLabel_1);
			frame.setTitle("CodeSmellsAPP");
			frame.setVisible(true);
			JLabel lblNewLabel_2 = new JLabel("MENU");
			lblNewLabel_2.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2.setBounds(220, 10, 80, 45);
			frame.getContentPane().add(lblNewLabel_2);
			setShowStatisitcs();
			setCreateExcel();
			setCodeRules();
			setQualityCodeSmells();

			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
		}
	
		/**
		 * Initiates the {@link InterfaceMetricsStatistics}
		 */
		public void setShowStatisitcs() {
			show_statistics = new JButton("Visualizar Estat\u00EDsticas");
			show_statistics.setBounds(39, 150, 209, 57);
			frame.getContentPane().add(show_statistics);
			show_statistics.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
				
					frame.dispose();
					InterfaceMetricsStatistics windowStatistics = new InterfaceMetricsStatistics();
					
					
				
				}
			});
		}
		/**
		 * Initiates the {@link ICodeSmellsRules}, it needs a text file with the rules to exist or it won't open the new interface
		 */
		public void setCodeRules() {
			code_rules = new JButton("CodeSmells e regras");
			code_rules.setBounds(285, 83, 209, 57);
			frame.getContentPane().add(code_rules);
			code_rules.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					try {
						ICodeSmellsRules windowCodeRules= new ICodeSmellsRules();
						frame.dispose();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					
					
				}
			});
		}
		/**
		 * Initiates the {@link IMetricsCalculator}
		 */
		public void setCreateExcel() {
			create_excel = new JButton("Criar Excel com métricas");
			create_excel.setBounds(39, 83, 209, 57);
			frame.getContentPane().add(create_excel);
			create_excel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					frame.dispose();
					IMetricsCalculator windowMetrics = new IMetricsCalculator();
					
				}
			});
		}
		/**
		 * Initiates the {@link ICodeSmellsQuality}
		 */
		public void setQualityCodeSmells() {
			quality_codesmells= new JButton("Deten\u00E7\u00E3o e Qualidade dos CodeSmells");
			quality_codesmells.setFont(new Font("Tahoma", Font.BOLD, 9));
			quality_codesmells.setHorizontalAlignment(SwingConstants.LEFT);
			quality_codesmells.setBounds(285, 150, 209, 57);
			frame.getContentPane().add(quality_codesmells);
			quality_codesmells.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
					ICodeSmellsQuality windowQuality = new ICodeSmellsQuality();
				}
			});
		}
}
