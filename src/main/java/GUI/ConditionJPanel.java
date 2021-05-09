package GUI;


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



/**
* <h1>Condition Panel</h1>
* The ConditionJPanel class allows the creation of a JPanel (extending JPanel class) with specific features regarding the graphic definition by the user of a condition within a rule: 
* 		Metric metric that will be used; 
* 		Numeric operator : EQ(==); NE(!=); GT(>); GE(>=); LT(<); LE(<=);
* 		Threshold: Value to compare with.
* 		Logical Operator: AND(&&); OR(||) to join between conditions.

* 
*
* @author  Engenharia de Software - Grupo 11
*/

public class ConditionJPanel extends JPanel {
	
	/**
	 * Metric Label
	 */
	
	private JLabel metric = new JLabel("Metric");
	
	/**
	 * Metrics available for user selection
	 */
	
	private JComboBox metricsAvailable;	
	
	/**
	 * Numeric Operator Label
	 */
	
	private JLabel noperator = new JLabel("Numeric Operator");
	
	/**
	 * Numeric Operators available for user selection
	 */
	
	private JComboBox noperatorsAvailable = new JComboBox(new String[] {"", "EQ","NE","GT","GE","LT","LE"});
	
	/**
	 * Threshold Label
	 */
	
	private JLabel threshold = new JLabel("Threshold");
	
	/**
	 * Value input regarding threshold label (value to compare metric value with)
	 */
	
	private JTextField limit = new JTextField(3);
	
	/**
	 * Logical Operator Label
	 */
	
	private JLabel logicalOperator = new JLabel("Logical Operator");;
	
	/**
	 * Logical Operators available for user selection
	 */
	
	private JComboBox loperatorsAvailable = new JComboBox(new String[] {"","AND","OR"});
	
	/**
	 * Code Smell Name
	 */
	
	private String code_smell;
	
	/**
	 * Color of ConditionJPanel objects
	 */
	private static final Color DEFAULT_COLOR = new Color(0,0,0,0);
	
	/**
	 * Create the panel.
	 */
	
	/**
	 * This constructor is directed to the introduction of a new condition creating a ConditionJPanel
	 *
	 *  
	 * @param conditionNumber The number of the condition within the rule
	 * @param code_smell The code smell associated to the rule
	 */
	
	public ConditionJPanel(String conditionNumber, String code_smell)
	{
		this(conditionNumber, "", "", "", "", code_smell);
	}
	
	/**
	 * This constructor is directed to the graphic generation of conditions already existing in the rule creating a ConditionJPanel
	 * 
	 * @param conditionNumber The number of the condition within the rule
	 * @param s_metric The metric used to evaluate the result of the code smell detention
	 * @param s_numericOperator The numeric operator that will serve to compare the value of the metric extract form class or method with the value from condition's threshold
	 * @param s_threshold The integer value for comparation
	 * @param s_logicalOperator The logical operator registered between conditions
	 * @param code_smell The code smell associated to the rule
	 */
	
	public ConditionJPanel(String conditionNumber, String s_metric, String s_numericOperator, String s_threshold, String s_logicalOperator, String code_smell) {
		this.code_smell = code_smell;
		if("God_class".equals(code_smell)) {
			metricsAvailable = new JComboBox(new String[] {"", "NOM_CLASS", "LOC_CLASS", "WMC_CLASS"});
		}else {
			metricsAvailable = new JComboBox(new String[] {"", "LOC_METHOD", "CYCLO_METHOD"});
		}
		
		setBorder(BorderFactory.createTitledBorder(conditionNumber));
		GroupLayout l = new GroupLayout(this);          
        this.setLayout(l);
        l.setAutoCreateGaps(true);
        l.setAutoCreateContainerGaps(true);
        
        metricsAvailable.setSelectedIndex(getMetricComboBoxIndex(s_metric));
        noperatorsAvailable.setSelectedIndex(getNumericOperatorComboBoxIndex(s_numericOperator));
        limit.setText(s_threshold);
        loperatorsAvailable.setSelectedIndex(getLogicalOperatorComboBoxIndex(s_logicalOperator));
        loperatorsAvailable.setEnabled(false);//ta aqui
        
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
        				.addComponent(logicalOperator)
        				.addComponent(loperatorsAvailable))  
        		);
        
        l.setVerticalGroup(l.createSequentialGroup()
        	    .addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	    		.addComponent(metric)
        	    		.addComponent(noperator)
        	    		.addComponent(threshold)
        	    		.addComponent(logicalOperator))
        	    .addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	    		.addComponent(metricsAvailable)
        	    		.addComponent(noperatorsAvailable)
        	    		.addComponent(limit)
        	    		.addComponent(loperatorsAvailable))
        	);	
	}
	
	/**This method is used to verify if the panel has all of its components:
	 * <ul>
  	 *	<li>Metric;</li>
  	 *	<li>Numeric Operator;</li>
  	 *	<li>Threshold;</li>
  	 *	<li>Logical Operator</li>
	 *</ul>
	 * filled.
	 * 
	 * In case not, it doesn't allow user to save rule conditions 
	 * and also displays a error message warning the user to complete the incompleted spaces. 
	 *
	 * 
	 * @param validateLogicalOperator True when rule has more than one condition in order to verify if user filled the space related to Logical Operator; otherwise false
	 * @param errorMessage This is StringBuilder instance to display an error to user advertising for unfilled boxes
	 * @param conditionIndex This is the reference to the condition position in the rule where there are fields incompleted
	 * @return boolean This returns true if all components are filled; otherwise, it displays an error message.
	 */
	
	public boolean validatePanel(boolean validateLogicalOperator, StringBuilder errorMessage, int conditionIndex)
	{
		boolean validation = true;
		String missingValues = "";
		
		if(metricsAvailable.getSelectedIndex() == 0)
		{
			validation = false;
			missingValues += "- Metric\n";
		}
		
		if(noperatorsAvailable.getSelectedIndex() == 0)
		{
			validation = false;
			missingValues += "- Numeric Operator\n";
		}
		
		if(limit.getText().length() == 0 || !limit.getText().matches("[0-9]+"))
		{
			validation = false;
			missingValues += "- Threshold\n";
		}
		
		if(validateLogicalOperator)
		{
			if(loperatorsAvailable.getSelectedIndex() == 0)
			{
				validation = false;
				missingValues += "- Logic Operator\n";
			}
		}
		
		if(validation == false) errorMessage.append("Condition " + conditionIndex + "\n" + missingValues);
		
		return validation;
	}
	
	/**This method is responsible for formatting the fields filled by the user into a custom String with ":" between the different camps.
	 * 
	 * @return This returns a custom String containing the metric choosen; the numeric operator choosen; the threshold input and the Logical Operator
	 */

	public String getConditionAsString()
	{
		return (String)metricsAvailable.getSelectedItem() + ":" +
				(String)noperatorsAvailable.getSelectedItem() + ":" +
				(String)limit.getText() + ":" +
				(String)loperatorsAvailable.getSelectedItem();
	}
	
	/**
	 * Returns {@link metric}
	 * @return {@link metric}
	 */
	
	public JLabel getMetric() {
		return metric;
	}
	
	/**
	 * Returns {@link metricsAvailable}
	 * @return {@link metricsAvailable}
	 */

	public JComboBox getMetricsAvailable() {
		return metricsAvailable;
	}
	
	/**
	 * Returns {@link noperator}
	 * @return {@link noperator}
	 */

	public JLabel getNoperator() {
		return noperator;
	}
	
	/**
	 * Returns {@link noperatorsAvailable}
	 * @return {@link noperatorsAvailable}
	 */

	public JComboBox getNoperatorsAvailable() {
		return noperatorsAvailable;
	}
	
	/**
	 * Returns {@link threshold}
	 * @return {@link threshold}
	 */

	public JLabel getThreshold() {
		return threshold;
	}
	
	/**
	 * Returns {@link limit}
	 * @return {@link limit}
	 */

	public JTextField getLimit() {
		return limit;
	}
	
	/**
	 * Returns {@link lo}
	 * @return {@link lo}
	 */

	public JLabel getLo() {
		return logicalOperator;
	}
	
	/**
	 * Returns {@link loperatorsAvailable}
	 * @return {@link loperatorsAvailable}
	 */

	public JComboBox getLoperatorsAvailable() {
		return loperatorsAvailable;
	}
	
	/**This method is responsible for setting the metric presented on the condition to the graphic interface (ConditionJPanel)
	 * in order to let the user see what metric was selected in that condition in that rule. 
	 * 
	 * 
	 * @param metric The String cast of the metric name presented on condition
	 * @return This returns a integer to identify the position on JComboBox of the Metric presented on condition
	 */
	
	private int getMetricComboBoxIndex(String metric){
		switch(metric){
		case "NOM_CLASS": return 1;
		case "LOC_CLASS": return 2;
		case "WMC_CLASS": return 1;
		case "LOC_METHOD": return 2;
		case "CYCLO_METHOD": return 3;
		default: return 0;
		}
	}
	
	/**This method is responsible for setting the numeric operator presented on the condition to the graphic interface (ConditionJPanel)
	 * in order to let the user see what numeric operator was selected in that condition in that rule. 
	 * 
	 * 
	 * @param operator The String cast of the numeric operator presented on condition
	 * @return This returns a integer to identify the position on JComboBox of the numeric operator presented on condition
	 */
	
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
	
	/**This method is responsible for setting the logical operator presented on the condition to the graphic interface (ConditionJPanel)
	 * in order to let the user see what logical operator was selected in that condition in that rule. 
	 * 
	 * 
	 * 
	 * @param operator The String cast of the logical operator presented on condition
	 * @return This returns a integer to identify the position on JComboBox of the logical operator presented on condition
	 */
	
	private int getLogicalOperatorComboBoxIndex(String operator){
		switch(operator){
		case "AND": return 1;
		case "OR": return 2;
		default: return 0;
		}
	}
	
}