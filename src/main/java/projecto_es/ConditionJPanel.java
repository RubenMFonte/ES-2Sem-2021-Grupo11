package projecto_es;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ConditionJPanel extends JPanel {

	private JLabel metric = new JLabel("Metric");
	private JComboBox metricsAvailable = new JComboBox();
	private JLabel noperator = new JLabel("Numeric Operator");
	private JComboBox noperatorsAvailable = new JComboBox(new String[] {"==","!=",">",">=","<","<="});
	private JLabel threshold = new JLabel("Threshold");
	private JTextField limit = new JTextField(3);
	private JLabel lo = new JLabel("Logical Operator");;
	private JComboBox loperatorsAvailable = new JComboBox(new String[] {"NULL","AND","OR"});
	
	
	/**
	 * Create the panel.
	 */
	public ConditionJPanel(String conditionNumber) {
		setBorder(BorderFactory.createTitledBorder(conditionNumber));
		GroupLayout l = new GroupLayout(this);          
        this.setLayout(l);
        l.setAutoCreateGaps(true);
        l.setAutoCreateContainerGaps(true);
        l.setHorizontalGroup(l.createSequentialGroup()
        		.addGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
        				.addComponent(metric)
        				.addComponent(metricsAvailable)
        				)
        		.addGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
        				.addComponent(noperator)
        				.addComponent(noperatorsAvailable))
        		.addGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
        				.addComponent(threshold)
        				.addComponent(limit))
        		.addGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
        				.addComponent(lo)
        				.addComponent(loperatorsAvailable))  
        		);
        
        l.setVerticalGroup(l.createSequentialGroup()
        	    .addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	    		.addComponent(metric)
        	    		.addComponent(noperator)
        	    		.addComponent(threshold)
        	    		.addComponent(lo))
        	    .addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	    		.addComponent(metricsAvailable)
        	    		.addComponent(noperatorsAvailable)
        	    		.addComponent(limit)
        	    		.addComponent(loperatorsAvailable))
        	);
        
        /*l.setHorizontalGroup(l.createSequentialGroup()
            .addGroup(l.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(metric)
                .addComponent(noperator)
                .addComponent(threshold)
            	.addComponent(lo))
            .addGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(metricsAvailable)
                .addComponent(noperatorsAvailable)
                .addComponent(limit)
                .addComponent(loperatorsAvailable))
        );

       l.setVerticalGroup(l.createSequentialGroup()
            .addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(metric)
                .addComponent(metricsAvailable))
            .addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(noperator)
                .addComponent(noperatorsAvailable))
            .addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(threshold)
                .addComponent(limit))
            .addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lo)
                    .addComponent(loperatorsAvailable))
        );*/

		
	}

	public JLabel getMetric() {
		return metric;
	}

	public JComboBox getMetricsAvailable() {
		return metricsAvailable;
	}

	public JLabel getNoperator() {
		return noperator;
	}

	public JComboBox getNoperatorsAvailable() {
		return noperatorsAvailable;
	}

	public JLabel getThreshold() {
		return threshold;
	}

	public JTextField getLimit() {
		return limit;
	}

	public JLabel getLo() {
		return lo;
	}

	public JComboBox getLoperatorsAvailable() {
		return loperatorsAvailable;
	}	
	
}