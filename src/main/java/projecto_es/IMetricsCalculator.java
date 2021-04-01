package projecto_es;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class IMetricsCalculator {

	private JFrame frame;
	private JTextField text_path;
	private JButton select_file;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IMetricsCalculator window = new IMetricsCalculator();
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
	 public IMetricsCalculator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 776, 287);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		select_file = new JButton("Selecionar Projeto");

		select_file.setBounds(91, 105, 147, 43);
		frame.getContentPane().add(select_file);

		text_path = new JTextField();
		text_path.setBounds(269, 109, 390, 35);
		frame.getContentPane().add(text_path);
		text_path.setColumns(10);
		frame.setVisible(true);
		
		select_fileAction();
	}

	public void select_fileAction() {
		select_file.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser choose_file = new JFileChooser();
				choose_file.setDialogTitle("Selecionar Projeto");
				choose_file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = choose_file.showSaveDialog(null);
				String path = choose_file.getSelectedFile().getAbsolutePath();
				text_path.setText(path);
				System.out.println("path = " + path);

			}
		});
	}
}
