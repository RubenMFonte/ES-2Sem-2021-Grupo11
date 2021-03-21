package projecto_es;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

public class MetricsCalculator {
	// Singleton instance
	private static MetricsCalculator metricsCalculator = null;
	
	// Class variables
	private List<CompilationUnit> compilationUnits;
	
	private MetricsCalculator()
	{
		compilationUnits = new ArrayList<CompilationUnit>();
	}
	
	public MetricsCalculator getMetricsCalculatorInstance()
	{
		if(metricsCalculator == null) metricsCalculator = new MetricsCalculator();
		
		return metricsCalculator;
	}
	
	public void run(String filename) {
		// Start of the metrics calculation process
	}

}
