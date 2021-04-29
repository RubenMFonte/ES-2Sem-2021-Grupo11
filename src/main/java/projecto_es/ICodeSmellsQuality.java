package projecto_es;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Canvas;
import java.awt.Color;

public class ICodeSmellsQuality {

	private JFrame frame;
	
	//For Panel Left
	private JPanel panelDetentionTable;
	private JTable tableInfo;
	private JPanel panelCenterTable;
	private JScrollPane scrollPaneForJTable;
	private JPanel panelSouthInfo;
	private JLabel label = new JLabel("Excel:");
	private JTextField excelPath = new JTextField();
	private JButton selectExcel = new JButton(" Select Excel");
	private JButton update = new JButton("Update Table");
	
	//For Panel Right
	private JPanel panelDetentionQuality;
	private JPanel panelQualityLabels;
	private JPanel panelLayoutCodeSmellSelected;
	private JLabel codeS = new JLabel("Code Smell Selected:");
	private JComboBox codeSmellSelected = new JComboBox(new String[] {"is_God_class", "is_Long_method"});
	private JPanel panelLayoutQualityData;
	private JLabel tp = new JLabel("True Positives [VP]:");
	private JLabel tp_result = new JLabel("0");
	private JLabel fp = new JLabel("False Positives [TN]:");
	private JLabel fp_result = new JLabel("0");
	private JLabel tn = new JLabel("True Negatives [FP]:");
	private JLabel tn_result = new JLabel("0");
	private JLabel fn = new JLabel("False Negatives [FN]:");
	private JLabel fn_result = new JLabel("0");
	private JPanel panelQualityGraphic;
	private Canvas canvas;
	
	JTextField textField = new JTextField();
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ICodeSmellsQuality window = new ICodeSmellsQuality();
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
	public ICodeSmellsQuality() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Code Smells Detetion and Quality");
		frame.setBounds(100, 100, 1200, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(new FlowLayout()); //FlowLayout.CENTER, 5, 5
		
		definePanelDetentionTableLook();	
		definePanelDetentionQualityLook();
			
		frame.getContentPane().add(panelDetentionTable,BorderLayout.CENTER);
		frame.getContentPane().add(panelDetentionQuality, BorderLayout.EAST);
	
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    
	    selectExcelButton();
	}
	
	private void definePanelDetentionTableLook() {
		panelDetentionTable = new JPanel();
		panelDetentionTable.setBorder(BorderFactory.createTitledBorder("Code Smells Status"));
		panelDetentionTable.setLayout(new BorderLayout(0, 0));
		
		panelCenterTable = new JPanel();
		//panelCenterTable.setPreferredSize(new Dimension(700, 300));
		panelDetentionTable.add(panelCenterTable, BorderLayout.CENTER);
		
		defineJTableContent();
		
		scrollPaneForJTable = new JScrollPane(tableInfo);
		
		panelCenterTable.add(scrollPaneForJTable);
		
		definePanelSouthInfoContent();
		
		panelDetentionTable.add(panelSouthInfo, BorderLayout.SOUTH);
		
	}
	
	private void defineJTableContent() {
		//Só para testar -> Mai tarde inicializar as colunas e linhas com os dados carregados do exccel
		String[][] data = {
	            { "Kundan Kumar Jha", "4031", "CSE" },
	            { "Anand Jha", "6014", "IT" }
	        };
		 String[] columnNames = { "Name", "Roll Number"
				, "Department" };
		 tableInfo = new JTable(50, 10);
		 tableInfo.setPreferredScrollableViewportSize(new Dimension (720, 300));
		 tableInfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	private void definePanelSouthInfoContent() {
		panelSouthInfo = new JPanel();
		GroupLayout l = new GroupLayout(panelSouthInfo);          
        panelSouthInfo.setLayout(l);
        l.setAutoCreateGaps(true);
        l.setAutoCreateContainerGaps(true);
        
        //Configuration like the one in trello
        /*l.setHorizontalGroup(l.createSequentialGroup()
        	.addComponent(label)
        	.addGroup(l.createParallelGroup(GroupLayout.Alignment.TRAILING)
        			.addComponent(excelPath)
        			.addGroup(l.createParallelGroup(GroupLayout.Alignment.TRAILING)
        					.addComponent(selectExcel)
        					.addComponent(update)))
        );
        
        l.setVerticalGroup(l.createSequentialGroup()
        		.addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
        				.addComponent(label)
        				.addComponent(excelPath))
        		.addComponent(selectExcel)
        		.addComponent(update)
        );*/
        
        l.setHorizontalGroup(l.createSequentialGroup()
            	.addComponent(label)
            	.addGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
            			.addComponent(excelPath))
            	.addGroup(l.createParallelGroup(GroupLayout.Alignment.TRAILING)
            			.addComponent(selectExcel)
            			.addComponent(update))
            );
            
            l.setVerticalGroup(l.createSequentialGroup()
            		.addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
            				.addComponent(label)
            				.addComponent(excelPath)
            				.addComponent(selectExcel))
            		.addComponent(update)
            );
	}
	
	private void definePanelDetentionQualityLook() {
		panelDetentionQuality = new JPanel();
		panelDetentionQuality.setBorder((BorderFactory.createTitledBorder("Code Smells Quality")));
		panelDetentionQuality.setLayout(new BoxLayout(panelDetentionQuality, BoxLayout.Y_AXIS));
		
		definePanelQualityLabelsContent();
		definePanelQualityGraphicContent();
		
		panelDetentionQuality.add(panelQualityLabels);
		panelDetentionQuality.add(panelQualityGraphic);
		
		
		
	}
	
	private void definePanelQualityLabelsContent() {
		panelQualityLabels = new JPanel();
		panelQualityLabels.setLayout(new BoxLayout(panelQualityLabels, BoxLayout.Y_AXIS));
		
		defineAuxPanelWithChoosenCodeSmell();
		defineAuxPanelWithQualityData();
		
		
		panelQualityLabels.add(panelLayoutCodeSmellSelected);
		panelQualityLabels.add(panelLayoutQualityData);
		panelQualityLabels.add(Box.createVerticalGlue());
	}
	
	private void defineAuxPanelWithChoosenCodeSmell() {
		panelLayoutCodeSmellSelected = new JPanel();
		GroupLayout l = new GroupLayout(panelLayoutCodeSmellSelected);
		panelLayoutCodeSmellSelected.setLayout(l);
		l.setAutoCreateGaps(true);
	    l.setAutoCreateContainerGaps(true);
		
	    l.setHorizontalGroup(
			l.createSequentialGroup()
					.addComponent(codeS)
					//.addGap(250)
					.addComponent(codeSmellSelected)
		);
		l.setVerticalGroup(
			l.createParallelGroup()
				.addGroup(l.createParallelGroup(Alignment.BASELINE)
					.addComponent(codeS)
					.addComponent(codeSmellSelected))
		);
       
	}
	
	private void defineAuxPanelWithQualityData() {
		panelLayoutQualityData = new JPanel();
		GroupLayout l = new GroupLayout(panelLayoutQualityData);
		panelLayoutQualityData.setBorder((BorderFactory.createTitledBorder("Verification Statistics")));
		panelLayoutQualityData.setLayout(l);
        l.setAutoCreateGaps(true);
        l.setAutoCreateContainerGaps(true);
        
        l.setHorizontalGroup(
    			l.createSequentialGroup()
    					.addGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
    							.addGroup(l.createSequentialGroup()
    									.addComponent(tp))
    									
    							.addGroup(l.createSequentialGroup()
    									.addComponent(tn))
    									
    							.addGroup(l.createSequentialGroup()
    									.addComponent(fp))
    									
    							.addGroup(l.createSequentialGroup()
    									.addComponent(fn))
    									
    							)
    					.addGroup(l.createParallelGroup(GroupLayout.Alignment.TRAILING)
    							.addComponent(tp_result)
    							.addComponent(fp_result)
    							.addComponent(tn_result)
    							.addComponent(fn_result))
    					
    		);
        
        l.setVerticalGroup(l.createSequentialGroup()
        	    .addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	        .addComponent(tp)
        	        .addComponent(tp_result))
        	    .addGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
        	    	.addComponent(fp)
            	    .addComponent(fp_result))
        	    .addGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
        	    	.addComponent(tn)
            	    .addComponent(tn_result))
        	    .addGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
        	    	.addComponent(fn)
            	    .addComponent(fn_result))
        	);
	}

	private void definePanelQualityGraphicContent() {
		panelQualityGraphic = new ChartPanel( new PieChart(10,11,20,20).pie);
		//System.out.println(panelQualityGraphic.get);
		panelQualityGraphic.setBorder((BorderFactory.createTitledBorder("Chart Statistics")));
		panelQualityGraphic.setPreferredSize(new Dimension(200,200));
		//canvas = new PieChart();
		//panelQualityGraphic.add(canvas);
		//panelQualityGraphic.repaint();
		
		
	}
	
	/*private JPanel chartCons() {
		
		      DefaultPieDataset dataset = new DefaultPieDataset( );
		      dataset.setValue( "IPhone 5s" , new Double( 10 ) );  
		      dataset.setValue( "SamSung Grand" , new Double( 10 ) );   
		      dataset.setValue( "MotoG" , new Double( 10 ) );    
		      dataset.setValue( "Nokia " , new Double( 10 ) );  
		            		   
		      JFreeChart chart = ChartFactory.createPieChart(      
		         "",   // chart title 
		         dataset,          // data    
		         true,             // include legend   
		         true, 
		         false);
		    
		     
		      return new ChartPanel(chart); 
		   
	}*/
	
	class PieChart {
		private JFreeChart pie;
		
		public PieChart(int tp, int tn, int fp, int fn) {			
			 DefaultPieDataset dataset = dataOnPie(tp, tn, fp, fn);
			 pie = ChartFactory.createPieChart(      
			         "",   				// chart title 
			         dataset,           // data    
			         true,              // include legend   
			         true, 
			         false);
			 PiePlot a = (PiePlot) pie.getPlot();
			 PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(  
				        "Marks {0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));  
			 
			 a.setLabelGenerator(labelGenerator);  
		}
		
		private DefaultPieDataset dataOnPie(int tp, int tn, int fp, int fn){
			 DefaultPieDataset dataset = new DefaultPieDataset( );
		      dataset.setValue( "True Positives" , new Double( tp ) );  
		      dataset.setValue( "True Negatives" , new Double( tn ) );   
		      dataset.setValue( "False Positives" , new Double( fp ) );    
		      dataset.setValue( "False Negatives" , new Double( fn ) );
		      
		     return dataset;
		}
		
	}
	
	
	/*class PieChart extends Canvas  
	{  
		
	  public PieChart() {  
	        	setBounds(0, 0, 200, 200);
	  }
	  @Override
	  public void paint(Graphics g)  
	  {  
		  g.setColor(Color.BLUE);
		  /*g.drawRect(0, 0, 50, 50);
		  g.fillRect(0, 50, 50, 50);
		  g.drawRect(0, 100, 50, 50);
		  g.fillRect(0, 150, 50, 50);
		  
		 
		  g.fillRect(50, 0, 50, 50);
		  g.drawRect(100, 0, 50, 50);
		  g.fillRect(150, 0, 50, 50);
		  
	    //g.fillOval(37,37,125,125);  
	  g.fillArc(37, 37, 125, 125, 180, -90);
	  
	   
	  }
	}*/
	
	public void selectExcelButton() {
		selectExcel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Selecionar Excel");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("XLS files", "xls", "xlsx");
				fileChooser.setFileFilter(filter);
				int result = fileChooser.showSaveDialog(null);
				if (fileChooser.getSelectedFile() != null) {
					String excelPathString = fileChooser.getSelectedFile().getAbsolutePath();
					excelPath.setText(excelPathString);
				}
    
			}
		});
	}
	
	
}

