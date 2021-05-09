package projecto_es;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class ClassDataStructure extends ClassObjects{

	/*
	 * Esta classe foi criada para guardar os dados necess�rios (que ir�o ser
	 * introduzidos, posteriormente, no ficheiro excel) como: as m�tricas relativas
	 * � classe e aos m�todos; o package onde est� o ficheiro java; o nome da
	 * classe; o nome do m�todo; etc. Na gera��o de um objeto da classe, � recebido
	 * como par�metro um [CompilationUnit]. Apartir desse [CompilationUnit], vai ser
	 * retirado o PackageName atrav�s do [PackageDeclaration] e a ClassName atrav�s
	 * da [ClassOrInterfaceDeclaration], para al�m disso, � tamb�m calculado as
	 * m�tricas relativas � Classe: [NOM, LOC, WMC].
	 *
	 * Como atributo, tamb�m est� presente a estrutura: List<MethodDataStruture> que
	 * consiste numa lista desta classe [MethodDataStruture] cujos atributos s�o:
	 * private String methodName; private int loc_method; private int cyclo_method;
	 * Portanto, cada objeto da classe [MethodDataStruture] possui o nome do m�todo
	 * e os valores das m�tricas a ele respetivas (estas s�o, igualmente, calculadas
	 * na classe [MethodDataStruture] na sequ�ncia de definir o valor destes
	 * atributos - � semelhan�a do que acontece com as m�tricas da classe)
	 * 
	 * Como uma classe pode possuir v�rios m�todos, � relevante/necess�rio que
	 * exista uma lista em que cada entrada possua o nome do m�todo e os valores das
	 * m�tricas do respetivo m�todo)
	 * 
	 * Os objetos da classe [MethodDataStruture] v�o ser diretamente instancados
	 * ap�s a inst�ncia da classe [ClassDataStruture], pois no m�todo
	 * [calculateMetricsStoreMethods(ClassOrInterfaceDeclaration cid)] ir� ser
	 * verificado cada n� filho de [cid] cuja classe seja [MethodDeclaration].
	 * Aqueles que assim o forem , ir�o servir de par�metro para a constru��o de um
	 * objeto [MethodDataStruture]
	 * 
	 */
	private int nom_class;
	private int loc_class;
	private int wmc_class;
	private HashMap<String, Boolean> classCodeSmellSpecialistValue = new HashMap<>();
	//Atributos necess�rios
	private List<ClassDataStructure> innerClassList = new ArrayList<>();
	private String classClassificationDetected;

	public ClassDataStructure(CompilationUnit javaFile) {
		super("","");
		List<Node> children = javaFile.getChildNodes();
		for (Node child : children) {
			if (child.getClass() == PackageDeclaration.class) {
				PackageDeclaration packageDeclaration = (PackageDeclaration) child;
				String packageName = packageDeclaration.getNameAsString();
				this.packageName = packageName;
			} 
			if (child.getClass() == ClassOrInterfaceDeclaration.class) {
				ClassOrInterfaceDeclaration classInterfaceDeclaration = (ClassOrInterfaceDeclaration) child;
				String className = classInterfaceDeclaration.getNameAsString();
				this.className = className;
				calculateClassDataStructureInformation(classInterfaceDeclaration);
			}
		}
	}

	public ClassDataStructure(String packageName, String className, String nom_class, String loc_class,
			String wmc_class) {
		super(packageName, className);
		this.loc_class = (int) Double.parseDouble(loc_class);
		this.nom_class = (int) Double.parseDouble(nom_class);
		this.wmc_class = (int) Double.parseDouble(wmc_class);
	}
	
	public ClassDataStructure(String packageName, String classNameToConcat, ClassOrInterfaceDeclaration innerClass) {
		super("","");
		String innerClassName = classNameToConcat.concat("." + innerClass.getNameAsString());
		this.packageName = packageName;
		this.className = innerClassName;
		calculateClassDataStructureInformation(innerClass);
	}

	/*public void addMethodDataStructure(int methodID, String methodName, int loc_method, int cyclo_method) {	
		MethodDataStructure mds = new MethodDataStructure(methodID, methodName, loc_method, cyclo_method);
		this.addMethod(mds);
	}*/

	private void calculateClassDataStructureInformation(ClassOrInterfaceDeclaration classInterfaceDeclaration) {
		this.wmc_class = MetricsCalculator.getWMC_class(classInterfaceDeclaration);
		this.loc_class = MetricsCalculator.getLOC_Class(classInterfaceDeclaration);
		this.nom_class = MetricsCalculator.getNOM_class(classInterfaceDeclaration);
		List<Node> children = classInterfaceDeclaration.getChildNodes();
		for (Node child : children) {
			if (child.getClass() == MethodDeclaration.class || child.getClass() == ConstructorDeclaration.class) {
				MethodDataStructure methodOnClass = new MethodDataStructure((CallableDeclaration) child);
				this.lmds.add(methodOnClass);
			} 
			if (child.getClass() == ClassOrInterfaceDeclaration.class) {
				ClassOrInterfaceDeclaration innerClassInterfaceDeclaration = (ClassOrInterfaceDeclaration) child;
				ClassDataStructure innerClass = new ClassDataStructure(packageName, className, innerClassInterfaceDeclaration);
				innerClassList.add(innerClass);
			}
		}
	}
	
	public List<ClassDataStructure> getInnerClassesList() {
		return innerClassList;
	}	

	public void setClassCodeSmellSpecialistValue(String codeSmellName, boolean codeSmellValue) {
		classCodeSmellSpecialistValue.put(codeSmellName, codeSmellValue);
	}

	public Boolean getClassCodeSmellSpecialistValue(String codeSmellName) {
		if (classCodeSmellSpecialistValue.containsKey(codeSmellName))
			return classCodeSmellSpecialistValue.get(codeSmellName);
		return null;
	}

	public int getNOMmetric() {
		return nom_class;
	}

	public int getLOCmetric() {
		return loc_class;
	}

	public int getWMCmetric() {
		return wmc_class;
	}
	
	/*public HashMap<String, Boolean> getCodeSmellsEvaluation() {
		return classCodeSmellSpecialistValue;
	}*/

	public void setClassClassificationDetected(String classificationDetected) {
		this.classClassificationDetected = classificationDetected;
	}

	public String getClassClassificationDetected() {
		return classClassificationDetected;
	}


}
