package projecto_es;

import java.awt.EventQueue;
import java.awt.desktop.AboutEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.github.javaparser.ast.CompilationUnit;
import javax.swing.JLabel;
import java.awt.Font;

public class IMetricsCalculator {

	private JFrame frame;
	private JTextField path_Java;
	private JButton select_file;
	private JButton b_executar;
	private JTextField path_Exel;
	/**
	 * @wbp.nonvisual location=-41,384
	 */
	private JButton show_Statistics;
	private JButton goBack;

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
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 10));
		frame.setBounds(100, 100, 684, 219);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Criar Exel com métricas");

		path_Java = new JTextField();
		path_Java.setBounds(116, 38, 390, 35);
		frame.getContentPane().add(path_Java);
		path_Java.setColumns(10);

		path_Exel = new JTextField();
		path_Exel.setBounds(116, 83, 390, 35);
		frame.getContentPane().add(path_Exel);
		path_Exel.setColumns(10);

		JLabel lblNewLabel = new JLabel("Ficheiro Excel");
		lblNewLabel.setBounds(23, 85, 96, 30);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Projeto Java");
		lblNewLabel_1.setBounds(34, 43, 72, 24);
		frame.getContentPane().add(lblNewLabel_1);

		frame.setVisible(true);

		setSelecionarProjeto_Button();
		setExecutar_Button();
		setShowStatisitcs_Button();
		set_GoBackButton();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

	public void setSelecionarProjeto_Button() {
		select_file = new JButton("Selecionar Projeto");
		select_file.setFont(new Font("Tahoma", Font.PLAIN, 10));
		select_file.setBounds(516, 38, 119, 35);
		frame.getContentPane().add(select_file);
		select_file.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser choose_file = new JFileChooser();
				choose_file.setDialogTitle("Selecionar Projeto");
				choose_file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = choose_file.showSaveDialog(null);
				if (choose_file.getSelectedFile() != null) {
					String path = choose_file.getSelectedFile().getAbsolutePath();
					path_Java.setText(path);
				}

			}
		});
	}

	public void setExecutar_Button() {
		b_executar = new JButton("Salvar Excel");
		b_executar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		b_executar.setBounds(516, 83, 119, 35);
		frame.getContentPane().add(b_executar);

		b_executar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				javaToExcel jte = new javaToExcel(path_Java.getText());
				if (!path_Java.getText().equals("")) {
					try {
						jte.run();
					} catch (IOException e1) {
						System.out.println("IOException");
					}
					if (jte.getMetricsCalculator().getCompilationUnits().size() == 0) {
						popUp("O projeto selecionado não é um projeto Java. Por favor selecione um projeto Java");
					} else {
						saveFile(jte);
						popUp("Excel salvo com sucesso!");
					}
				} else {
					popUp("Escolha um projeto java antes de executar");
				}
			}
		});

	}

	public void setShowStatisitcs_Button() {
		show_Statistics.setFont(new Font("Tahoma", Font.PLAIN, 10));
		show_Statistics.setBounds(516, 128, 119, 35);
		frame.getContentPane().add(show_Statistics);
		show_Statistics.setEnabled(false);

		show_Statistics.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/* Correr interface do SA */
				System.out.println("Minha interface: " + path_Exel.getText());
				InterfaceMetricsStatistics ims = new InterfaceMetricsStatistics(path_Exel.getText());
				ims.getButtonShowStatistics().doClick();
				;
				frame.dispose();
			}
		});
	}

	public void popUp(String popUp) {
		JFrame parent = new JFrame();
		JOptionPane.showMessageDialog(parent, popUp);
	}

	public void saveFile(javaToExcel jte) {
		JFileChooser save_exel = new JFileChooser();
		save_exel.setDialogTitle("Salvar ficheiro excel");
		save_exel.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = save_exel.showSaveDialog(null);
		if (save_exel.getSelectedFile() == null) {
			int dialogResult = JOptionPane.showConfirmDialog(null,
					"Tem certeza que deseja cancelar a Execucao? O ficheiro Exel não será gravado!");
			if (dialogResult == 0) {
				return;
			} else {
				saveFile(jte);
			}
		} else {
			String exel_file = save_exel.getSelectedFile().getAbsolutePath();
			exel_file += generate_nameFile(path_Java.getText());
			path_Exel.setText(exel_file);
			show_Statistics.setEnabled(true);
			jte.setPath_exel(exel_file);
			path_Exel.setText(jte.getPath_exel());
			try {
				jte.writeToExcel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void set_GoBackButton() {
		goBack = new JButton("<");
		goBack.setBounds(13, 128, 119, 35);
		frame.getContentPane().add(goBack);

		goBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				IMenu window = new IMenu();
				frame.dispose();

			}
		});
	}

	public String generate_nameFile(String project) {
		boolean stop = false;
		int i;
		for (i = project.length() - 1; !stop; i--) {
			if (project.charAt(i) == '\\') {
				stop = true;
				i++;
			}
		}
		String name_exel = "";
		for (int j = i; j < project.length(); j++) {
			name_exel += project.charAt(j);
		}
		name_exel += "_metrics.xlsx";
		return name_exel;
	}

}
