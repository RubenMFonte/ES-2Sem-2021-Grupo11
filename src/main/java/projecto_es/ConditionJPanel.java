package projecto_es;

import java.awt.Color;
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
	
	private JComboBox metricsAvailable =
			new JComboBox(new String[] {"", "NOM_CLASS", "LOC_CLASS", "WMC_CLASS", "LOC_METHOD", "CYCLO_METHOD"});
	
	private JLabel noperator = new JLabel("Numeric Operator");
	private JComboBox noperatorsAvailable = new JComboBox(new String[] {"", "==","!=",">",">=","<","<="});
	private JLabel threshold = new JLabel("Threshold");
	private JTextField limit = new JTextField(3);
	private JLabel lo = new JLabel("Logical Operator");;
	private JComboBox loperatorsAvailable = new JComboBox(new String[] {"","AND","OR"});
	
	private static final Color DEFAULT_COLOR = new Color(0,0,0,0);
	
	/**
	 * Create the panel.
	 */
	
	public ConditionJPanel(String conditionNumber)
	{
		this(conditionNumber, "", "", "", "");
	}
	
	public ConditionJPanel(String conditionNumber, String s_metric, String s_numericOperator, String s_threshold, String s_logicalOperator) {
		setBorder(BorderFactory.createTitledBorder(conditionNumber));
		GroupLayout l = new GroupLayout(this);          
        this.setLayout(l);
        l.setAutoCreateGaps(true);
        l.setAutoCreateContainerGaps(true);
        
        metricsAvailable.setSelectedIndex(getMetricComboBoxIndex(s_metric));
        noperatorsAvailable.setSelectedIndex(getNumericOperatorComboBoxIndex(s_numericOperator));
        limit.setText(s_threshold);
        loperatorsAvailable.setSelectedIndex(getLogicalOperatorComboBoxIndex(s_logicalOperator));
        
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
	
	public boolean validatePanel(boolean validateLogicalOperator)
	{
		boolean validation = true;
		
		if(metricsAvailable.getSelectedIndex() == 0)
		{
			validation = false;
		}
		
		if(noperatorsAvailable.getSelectedIndex() == 0)
		{
			validation = false;
		}
		
		if(limit.getText().length() == 0 || !limit.getText().matches("[0-9]+"))
		{
			validation = false;
		}
		
		if(validateLogicalOperator)
		{
			if(loperatorsAvailable.getSelectedIndex() == 0)
			{
				validation = false;
			}
		}
		
		return validation;
	}

	public String getConditionAsString()
	{
		return (String)metricsAvailable.getSelectedItem() + ":" +
				(String)noperatorsAvailable.getSelectedItem() + ":" +
				(String)limit.getText() + ":" +
				(String)loperatorsAvailable.getSelectedItem();
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
	
	private int getMetricComboBoxIndex(String metric){
		switch(metric){
		case "NOM_CLASS": return 1;
		case "LOC_CLASS": return 2;
		case "WMC_CLASS": return 3;
		case "LOC_METHOD": return 4;
		case "CYCLO_METHOD": return 5;
		default: return 0;
		}
	}
	
	private int getNumericOperatorComboBoxIndex(String operator){
		switch(operator){
		case "EQ": return 1;
		case "NE": return 2;
		case "GT": return 3;
		case "GE": return 4;
		case "LT": return 5;
		case "LE": return 6;
		default: return 0;
		}
	}
	
	private int getLogicalOperatorComboBoxIndex(String operator){
		switch(operator){
		case "AND": return 1;
		case "OR": return 2;
		default: return 0;
		}
	}
	
}