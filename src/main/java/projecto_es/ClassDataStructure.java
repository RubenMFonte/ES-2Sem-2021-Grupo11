package projecto_es;

import java.util.ArrayList;
import java.util.List;


import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class ClassDataStructure {

	private PackageDeclaration pd;
	private ClassOrInterfaceDeclaration cid;
	private int wmc_class;
	private List<MethodDataStructure> mds = new ArrayList<>();
	
	public ClassDataStructure(CompilationUnit javaFile) {
		List<Node> children = javaFile.getChildNodes();
		for(Node child : children) {
			if (child.getClass() == PackageDeclaration.class) {
				this.pd = (PackageDeclaration)child;
			} else if (child.getClass() == ClassOrInterfaceDeclaration.class){
				this.cid = (ClassOrInterfaceDeclaration)child;
			} else {}
		}
		this.wmc_class = MetricsCalculator.WMC_class(cid);
		storeMethods();
	}
	
	private void storeMethods() {
		List<MethodDeclaration> methods = cid.getMethods();
		for(MethodDeclaration md : methods) {
			MethodDataStructure mds_part = new MethodDataStructure(md);
			mds.add(mds_part);
		}
	}
	
	public String getPackageName() {
		return pd.getNameAsString();
	}
	
	public String getClassName() {
		return cid.getNameAsString();
	}
	
	public int getWMCmetric() {
		return wmc_class;
	}
	
	public List<MethodDataStructure> getMethodDataStructureList(){
		return mds;
	}
	
}
