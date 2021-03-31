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
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class InterfaceMetricsEstatistics extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceMetricsEstatistics frame = new InterfaceMetricsEstatistics();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfaceMetricsEstatistics() {
		setTitle("Statistics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 622, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 5, 0, 0));
		
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
		
		JList list_1 = new JList();
		scrollPane_1.setViewportView(list_1);
		
		
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
		
		JList list_2 = new JList();
		scrollPane_2.setViewportView(list_2);
		
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
		
		JList list_3 = new JList();
		scrollPane_3.setViewportView(list_3);
		
		JPanel contentPane_Metrics = new JPanel();
		contentPane.add(contentPane_Metrics);
		contentPane_Metrics.setLayout(new BoxLayout(contentPane_Metrics, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_4 = new JLabel("Metrics");
		lblNewLabel_4.setBackground(Color.WHITE);
		lblNewLabel_4.setAlignmentX(0.5f);
		contentPane_Metrics.add(lblNewLabel_4);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane_Metrics.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_4_1 = new JLabel("Statistics");
		lblNewLabel_4_1.setBackground(Color.WHITE);
		lblNewLabel_4_1.setAlignmentX(0.5f);
		panel_1.add(lblNewLabel_4_1);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		panel_1.add(scrollPane_4);
		
		table_1 = new JTable();
		scrollPane_4.setViewportView(table_1);
	}

}
