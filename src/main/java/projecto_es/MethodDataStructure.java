package projecto_es;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class MethodDataStructure {
	
	private MethodDeclaration md;
	private int cyclo_method;
	
	public MethodDataStructure (MethodDeclaration md_given) {
		this.md = md_given;
		this.cyclo_method = MetricsCalculator.Cyclo_method(md_given);
	}
	
	public String getMethodName() {
		return md.getNameAsString();
	}
	
	public int getCYCLOMetric () {
		return cyclo_method;	
	}
}
