package projecto_es;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.List;

public class InterfaceMetricsStatistics extends JFrame {

	public JList getPackageJList() {
		return packageJList;
	}

	public void setPackageJList(JList packageJList) {
		this.packageJList = packageJList;
	}

	public JList getClassJList() {
		return classJList;
	}

	public void setClassJList(JList classJList) {
		this.classJList = classJList;
	}

	public JList getMetricsClassJlist() {
		return metricsClassJlist;
	}

	public void setMetricsClassJlist(JList metricsClassJlist) {
		this.metricsClassJlist = metricsClassJlist;
	}

	public JList getStatisticsJlist() {
		return statisticsJlist;
	}

	public void setStatisticsJlist(JList statisticsJlist) {
		this.statisticsJlist = statisticsJlist;
	}

	public JList getMethodsJList() {
		return methodsJList;
	}

	public List<ClassDataStructure> getAllClass() {
		return allClass;
	}

	public void setAllClass(List<ClassDataStructure> allClass) {
		this.allClass = allClass;
	}

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;

	private JList packageJList;
	private JList classJList = new JList<String>();
	private JList methodsJList = new JList<String>();
	private JList metricsClassJlist = new JList<String>();
	private JList metricsMethodsJlist = new JList<String>();
	private JList statisticsJlist;

	private String packageString;
	private String classString;
	private String methodsString;
	private JTextField textField;

	private String excelPath;
	
	List<ClassDataStructure> allClass;
	private GeneralStatistics statisticsGeneral;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	
	public InterfaceMetricsStatistics() {
		this.packageJList = ListsToInterface.getListsToInterfaceInstance().getPackageJList();
		this.classJList = ListsToInterface.getListsToInterfaceInstance().getClassJList();
		this.methodsJList = ListsToInterface.getListsToInterfaceInstance().getMethodsJList();
//		this.statisticsJlist = ListsToInterface.getListsToInterfaceInstance().showGeneralMetrics(statisticsGeneral);
		
		setTitle("Statistics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 668, 438);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(50);
		panel_4.add(textField);
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		
		JButton buttonSelectFile = new JButton("Selecionar Excel");
		buttonSelectFile.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_3.add(buttonSelectFile);
		
		JButton buttonShowStatistics = new JButton("Mostrar Caracter\u00EDsticas");
		buttonShowStatistics.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_3.add(buttonShowStatistics);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 5, 5, 5));

//package JList		
		JPanel contentPane_Package = new JPanel();
		panel.add(contentPane_Package);
		contentPane_Package.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane_Package.setLayout(new BoxLayout(contentPane_Package, BoxLayout.Y_AXIS));

		JLabel lblNewLabel_1 = new JLabel("Packages");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_1.setBackground(Color.WHITE);
		contentPane_Package.add(lblNewLabel_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		contentPane_Package.add(scrollPane_1);

		scrollPane_1.setViewportView(packageJList);

//class JList		
		JPanel contentPane_Classes = new JPanel();
		panel.add(contentPane_Classes);
		contentPane_Classes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane_Classes.setLayout(new BoxLayout(contentPane_Classes, BoxLayout.Y_AXIS));

		JLabel lblNewLabel_2 = new JLabel("Classes");
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_2.setBackground(Color.WHITE);
		contentPane_Classes.add(lblNewLabel_2);

		JScrollPane scrollPane_2 = new JScrollPane();
		contentPane_Classes.add(scrollPane_2);

		scrollPane_2.setViewportView(classJList);

//methods JList
		JPanel contentPane_Methods = new JPanel();
		panel.add(contentPane_Methods);
		contentPane_Methods.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane_Methods.setLayout(new BoxLayout(contentPane_Methods, BoxLayout.Y_AXIS));

		JLabel lblNewLabel_3 = new JLabel("Methods");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_3.setBackground(Color.WHITE);
		contentPane_Methods.add(lblNewLabel_3);

		JScrollPane scrollPane_3 = new JScrollPane();
		contentPane_Methods.add(scrollPane_3);

		JButton buttonShowMethods = new JButton("Show Methods");
		buttonShowMethods.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane_Methods.add(buttonShowMethods);

//metrics JList	
		JPanel contentPane_Metrics = new JPanel();
		panel.add(contentPane_Metrics);
		contentPane_Metrics.setLayout(new BoxLayout(contentPane_Metrics, BoxLayout.Y_AXIS));

		// class metrics
		JLabel lblNewLabel_4 = new JLabel("Class Metrics");
		lblNewLabel_4.setBackground(Color.WHITE);
		lblNewLabel_4.setAlignmentX(0.5f);
		contentPane_Metrics.add(lblNewLabel_4);
    
		JScrollPane scrollPane = new JScrollPane();
		contentPane_Metrics.add(scrollPane);
		scrollPane.setViewportView(metricsClassJlist);

		// methods metrics
		JLabel lblNewLabel_41 = new JLabel("Method Metrics");
		lblNewLabel_41.setBackground(Color.WHITE);
		lblNewLabel_41.setAlignmentX(0.5f);
		contentPane_Metrics.add(lblNewLabel_41);

		JScrollPane scrollPane_methodsMetrics = new JScrollPane();
		contentPane_Metrics.add(scrollPane_methodsMetrics);
		scrollPane_methodsMetrics.setViewportView(metricsMethodsJlist);

		JButton buttonShowMetrics = new JButton("Show Metrics");
		buttonShowMetrics.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane_Metrics.add(buttonShowMetrics);

//statistics JList		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JLabel lblNewLabel_4_1 = new JLabel("Statistics");
		lblNewLabel_4_1.setBackground(Color.WHITE);
		lblNewLabel_4_1.setAlignmentX(0.5f);
		panel_1.add(lblNewLabel_4_1);

		JScrollPane scrollPane_4 = new JScrollPane();
		panel_1.add(scrollPane_4);

		scrollPane_4.setViewportView(statisticsJlist);

		JButton goBackButton = new JButton("Go Back");
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(goBackButton);

//buttonShowMetrics actionListener
		buttonShowMetrics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("show class metrics");
				classString = (String) classJList.getSelectedValue();
				methodsString = (String) methodsJList.getSelectedValue();
				System.out.println("methodString: " + methodsString);
				metricsClassJlist = ListsToInterface.getListsToInterfaceInstance().showClassMetrics(classString);
				scrollPane.setViewportView(metricsClassJlist);
				metricsMethodsJlist = ListsToInterface.getListsToInterfaceInstance().showMethodMetrics(classString,
						methodsString);
				scrollPane_methodsMetrics.setViewportView(metricsMethodsJlist);
//	                 }
				System.out.println("botão showMetrics premido");
			}
		});

//buttonShowMethods actionListener
		buttonShowMethods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (packageJList.getSelectedIndex() != -1 && classJList.getSelectedIndex() != -1) {
					classString = (String) classJList.getSelectedValue();
					JList<String> methodsJList_Temp = ListsToInterface.getListsToInterfaceInstance()
							.showMethods(classString);
					setMethodsJList(methodsJList_Temp);
					scrollPane_3.setViewportView(methodsJList_Temp);
				}
			}
		}); 
		
//buttonSelectExcel
		buttonSelectFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//JFileChooser choose_file = new JFileChooser();
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Selecionar Excel");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("XLS files", "xls", "xlsx");
					fileChooser.setFileFilter(filter);
					//choose_file.setFileSelectionMode(fileChooser);
					int result = fileChooser.showSaveDialog(null);
					if (fileChooser.getSelectedFile() != null) {
						excelPath = fileChooser.getSelectedFile().getAbsolutePath();
						textField.setText(excelPath);
					}

				}
			});
		
//buttonShowCarateristics
		buttonShowStatistics.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setAllClass(ExcelToData.getallClass(excelPath));
					ListsToInterface.getListsToInterfaceInstance().setDataList(allClass);
					setPackageJList(ListsToInterface.getListsToInterfaceInstance().getPackageJList());
					setClassJList(ListsToInterface.getListsToInterfaceInstance().getClassJList());
					
					scrollPane_1.setViewportView(packageJList);		
					scrollPane_2.setViewportView(classJList);
					statisticsGeneral = new GeneralStatistics(allClass);
					scrollPane_4.setViewportView(ListsToInterface.getListsToInterfaceInstance().showGeneralMetrics(statisticsGeneral));
					
				//	InterfaceMetricsStatistics i = new InterfaceMetricsStatistics(ListsToInterface.getListsToInterfaceInstance().getPackageJList(), ListsToInterface.getListsToInterfaceInstance().getClassJList(), ListsToInterface.getListsToInterfaceInstance().showGeneralMetrics(statisticsGeneral) );
					setVisible(true);
				}
			});
	
	}

	public void setMethodsJList(JList methodsJList) {
		this.methodsJList = methodsJList;
	}
}
