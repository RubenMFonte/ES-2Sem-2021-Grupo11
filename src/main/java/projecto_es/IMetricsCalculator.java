package projecto_es;

import java.awt.EventQueue;
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

public class IMetricsCalculator {

	private JFrame frame;
	private JTextField text_path;
	private JButton select_file;
	private JButton b_executar;

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
		frame.setBounds(100, 100, 557, 287);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		select_file = new JButton("Selecionar Projeto");

		select_file.setBounds(77, 152, 147, 43);
		frame.getContentPane().add(select_file);

		text_path = new JTextField();
		text_path.setBounds(77, 112, 390, 35);
		frame.getContentPane().add(text_path);
		text_path.setColumns(10);

		b_executar = new JButton("Executar");
		b_executar.setBounds(234, 152, 136, 43);
		frame.getContentPane().add(b_executar);
		frame.setTitle("Selecionar Projeto");
		frame.setVisible(true);

		select_fileAction();
		b_executarAction();

	}

	public void select_fileAction() {
		select_file.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser choose_file = new JFileChooser();
				choose_file.setDialogTitle("Selecionar Projeto");
				choose_file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = choose_file.showSaveDialog(null);
				if (choose_file.getSelectedFile() != null) {
					String path = choose_file.getSelectedFile().getAbsolutePath();
					text_path.setText(path);
				}

			}
		});
	}

	public void b_executarAction() {
		b_executar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				javaToExcel jte = new javaToExcel(text_path.getText());
				if (!text_path.getText().equals("")) {
					try {
						jte.run();
					} catch (IOException e1) {
						System.out.println("IOException");
					}
					if (jte.getMetricsCalculator().getCompilationUnits().size() == 0) {
						popUp("O projeto selecionado não é um projeto Java. Por favor selecione um projeto Java");
					} else {
						JFileChooser save_exel = new JFileChooser();
						save_exel.setDialogTitle("Salvar ficheiro exel");
						int result = save_exel.showSaveDialog(null);
						if (save_exel.getSelectedFile() == null) {
							int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja cancelar a Execucao? O ficheiro Exel não será gravado!");
							if (dialogResult == 0) {
								return;
							} else {
								actionPerformed(e);;
							}
						} else {
							String exel_file = save_exel.getSelectedFile().getAbsolutePath();
							jte.setPath_exel(exel_file);
							try {
								jte.writeToExcel();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}

				} else {
					popUp("Escolha um projeto java antes de executar");
				}
			}
		});

	}

	public void popUp(String popUp) {
		JFrame parent = new JFrame();
		JOptionPane.showMessageDialog(parent, popUp);
	}
}
