package projecto_es;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.SwingConstants;

public class IDetetionParameters {

	private JFrame frame;
	private JPanel panel;
	private JPanel panelSOUTH;
	private JScrollPane scrollPane;
	private List<JPanel> conditionPanels = new ArrayList<JPanel>();
	private JPanel panelsouthLEFT;
	private JPanel panelsouthRIGHT;
	private JButton insertCond = new JButton("Insert New Condition");
	private JButton removeCond = new JButton("Remove Last Condition");
	private JButton saveAlt = new JButton("Save Alterations");

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IDetetionParameters window = new IDetetionParameters();
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
	public IDetetionParameters() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Detetion Parameters");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        
        panel = new JPanel();
        panelSOUTH = new JPanel();
        panelSOUTH.setLayout(new BorderLayout(0, 0));
        
        panelsouthLEFT = new JPanel();
        panelsouthLEFT.add(insertCond);       
        panelsouthLEFT.add(removeCond);  
        panelSOUTH.add(panelsouthLEFT, BorderLayout.WEST);
        
        panelsouthRIGHT = new JPanel();
        panelsouthRIGHT.add(saveAlt);
        panelSOUTH.add(panelsouthRIGHT, BorderLayout.EAST); 
       
        scrollPane = new JScrollPane(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        ConditionJPanel iic = new ConditionJPanel("Condition 1");
        panel.add(iic);
        conditionPanels.add(iic);

        
        checkForNewCond();
        checkForRemCond();
        
        frame.getContentPane().add(scrollPane);
        frame.getContentPane().add(panelSOUTH, BorderLayout.SOUTH);
        
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	
	private void checkForNewCond() {
		insertCond.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String conditionNumber = "Condition " + (conditionPanels.size() + 1);
    		    ConditionJPanel iic = new ConditionJPanel(conditionNumber);    		   
    		    panel.add(iic);
    		    conditionPanels.add(iic);
    		        
    		panel.revalidate();
    			 			
        	}
        });
	}
	
	private void checkForRemCond() {
		removeCond.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(conditionPanels.size() == 1) {
					System.out.println("Cannot remove anymore!");
				} else {
					Component lastComp = conditionPanels.get(conditionPanels.size()-1);
		        	panel.remove(lastComp);
		        	conditionPanels.remove(conditionPanels.size()-1);
				}
				
	        panel.repaint();
	        panel.revalidate();
    			 			
        	}
        });
	}
	


}
