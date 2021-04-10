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
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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

public class InterfaceMetricsStatistics extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	
	private JList packageJList;
	private JList classJList= new JList<String>();
	private JList methodsJList= new JList<String>();
	private JList metricsClassJlist= new JList<String>();
	private JList metricsMethodsJlist= new JList<String>();
	private JList statisticsJlist;
	
	private String packageString;
	private String classString;
	private String methodsString;

	/**
	 * Launch the application. */
/*	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				 final DefaultListModel<String> l1 = new DefaultListModel<>();  
		          l1.addElement("C");  
		          l1.addElement("C++");  
		          l1.addElement("Java");  
		          l1.addElement("PHP");  
		          final JList<String> packageJList = new JList<>(l1); 
		          final DefaultListModel<String> l2 = new DefaultListModel<>();  
		          l2.addElement("C1");  
		          l2.addElement("C++1");  
		          l2.addElement("Java1");  
		          l2.addElement("PHP1");  
		          final JList<String> classJList = new JList<>(l2); 
		          final DefaultListModel<String> l3 = new DefaultListModel<>();  
		          l3.addElement("C2");  
		          l3.addElement("C++2");  
		          l3.addElement("Java2");  
		          l3.addElement("PHP2");  
		          final JList<String> methodsJList = new JList<>(l3); 
		          final DefaultListModel<String> l4 = new DefaultListModel<>();  
		          l4.addElement("C3");  
		          l4.addElement("C++3");  
		          l4.addElement("Java3");  
		          l4.addElement("PHP3");  
		          final JList<String> statisticsJlist = new JList<>(l4); 
				try {
					InterfaceMetricsStatistics frame = new InterfaceMetricsStatistics(packageJList,classJList,methodsJList,statisticsJlist);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	
	
	
	public InterfaceMetricsStatistics(JList packageJList, JList classJList, JList statisticsJlist) {
		this.packageJList = packageJList;
		this.classJList = classJList;
		this.methodsJList = ListsToInterface.getListsToInterfaceInstance().getMethodsJList();
		this.statisticsJlist = statisticsJlist;
		
		setTitle("Statistics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 622, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 5, 0, 0));
		
//package JList		
		JPanel contentPane_Package = new JPanel();
		contentPane_Package.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(contentPane_Package);
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
		contentPane_Classes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(contentPane_Classes);
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
		contentPane_Methods.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(contentPane_Methods);
		contentPane_Methods.setLayout(new BoxLayout(contentPane_Methods, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_3 = new JLabel("Methods");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_3.setBackground(Color.WHITE);
		contentPane_Methods.add(lblNewLabel_3);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		contentPane_Methods.add(scrollPane_3);
		
		JButton buttonShowMethods = new JButton("Show Methods");
		contentPane_Methods.add(buttonShowMethods);
		
//metrics JList	
		JPanel contentPane_Metrics = new JPanel();
		contentPane.add(contentPane_Metrics);
		contentPane_Metrics.setLayout(new BoxLayout(contentPane_Metrics, BoxLayout.Y_AXIS));
		
	//class metrics
		JLabel lblNewLabel_4 = new JLabel("Class Metrics");
		lblNewLabel_4.setBackground(Color.WHITE);
		lblNewLabel_4.setAlignmentX(0.5f);
		contentPane_Metrics.add(lblNewLabel_4);
			
		JScrollPane scrollPane = new JScrollPane();
		contentPane_Metrics.add(scrollPane);
		scrollPane.setViewportView(metricsClassJlist);
		
	//methods metrics	
		JLabel lblNewLabel_41 = new JLabel("Method Metrics");
		lblNewLabel_41.setBackground(Color.WHITE);
		lblNewLabel_41.setAlignmentX(0.5f);
		contentPane_Metrics.add(lblNewLabel_41);
		
		JScrollPane scrollPane_methodsMetrics = new JScrollPane();
		contentPane_Metrics.add(scrollPane_methodsMetrics);
		scrollPane_methodsMetrics.setViewportView(metricsMethodsJlist);
		
		JButton buttonShowMetrics = new JButton("Show Metrics");
		contentPane_Metrics.add(buttonShowMetrics);
		
//statistics JList		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_4_1 = new JLabel("Statistics");
		lblNewLabel_4_1.setBackground(Color.WHITE);
		lblNewLabel_4_1.setAlignmentX(0.5f);
		panel_1.add(lblNewLabel_4_1);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		panel_1.add(scrollPane_4);
		
		scrollPane_4.setViewportView(statisticsJlist);
		
		JButton goBackButton = new JButton("Go Back");
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
	                    metricsMethodsJlist = ListsToInterface.getListsToInterfaceInstance().showMethodMetrics(classString,methodsString);
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
						JList<String> methodsJList_Temp= ListsToInterface.getListsToInterfaceInstance().showMethods(classString);
						setMethodsJList(methodsJList_Temp);
	                    scrollPane_3.setViewportView(methodsJList_Temp);
	                 }
			}
		}); 
		
		
		
	}

	public void setMethodsJList(JList methodsJList) {
		this.methodsJList = methodsJList;
	}
}
