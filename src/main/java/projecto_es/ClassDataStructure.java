package projecto_es;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class ClassDataStructure {

	/*
	 * Esta classe foi criada para guardar os dados necessários (que irão ser
	 * introduzidos, posteriormente, no ficheiro excel) como: as métricas relativas
	 * à classe e aos métodos; o package onde está o ficheiro java; o nome da
	 * classe; o nome do método; etc. Na geração de um objeto da classe, é recebido
	 * como parâmetro um [CompilationUnit]. Apartir desse [CompilationUnit], vai ser
	 * retirado o PackageName através do [PackageDeclaration] e a ClassName através
	 * da [ClassOrInterfaceDeclaration], para além disso, é também calculado as
	 * métricas relativas à Classe: [NOM, LOC, WMC].
	 *
	 * Como atributo, também está presente a estrutura: List<MethodDataStruture> que
	 * consiste numa lista desta classe [MethodDataStruture] cujos atributos são:
	 * private String methodName; private int loc_method; private int cyclo_method;
	 * Portanto, cada objeto da classe [MethodDataStruture] possui o nome do método
	 * e os valores das métricas a ele respetivas (estas são, igualmente, calculadas
	 * na classe [MethodDataStruture] na sequência de definir o valor destes
	 * atributos - à semelhança do que acontece com as métricas da classe)
	 * 
	 * Como uma classe pode possuir vários métodos, é relevante/necessário que
	 * exista uma lista em que cada entrada possua o nome do método e os valores das
	 * métricas do respetivo método)
	 * 
	 * Os objetos da classe [MethodDataStruture] vão ser diretamente instancados
	 * após a instância da classe [ClassDataStruture], pois no método
	 * [calculateMetricsStoreMethods(ClassOrInterfaceDeclaration cid)] irá ser
	 * verificado cada nó filho de [cid] cuja classe seja [MethodDeclaration].
	 * Aqueles que assim o forem , irão servir de parâmetro para a construção de um
	 * objeto [MethodDataStruture]
	 * 
	 */

	private String packageName;
	private String className;
	private int nom_class;
	private int loc_class;
	private int wmc_class;
	private List<MethodDataStructure> methodDataStructureList = new ArrayList<>();
	private List<ClassDataStructure> innerClassList = new ArrayList<>();
	private HashMap<String, Boolean> classCodeSmellSpecialistValue = new HashMap<>();
	private String classClassificationDetected;

	public ClassDataStructure(CompilationUnit javaFile) {
		List<Node> children = javaFile.getChildNodes();
		for (Node child : children) {
			if (child.getClass() == PackageDeclaration.class) {
				PackageDeclaration packageDeclaration = (PackageDeclaration) child;
				String packageName = packageDeclaration.getNameAsString();
				this.packageName = packageName;
			} else if (child.getClass() == ClassOrInterfaceDeclaration.class) {
				ClassOrInterfaceDeclaration classInterfaceDeclaration = (ClassOrInterfaceDeclaration) child;
				String className = classInterfaceDeclaration.getNameAsString();
				this.className = className;
				calculateClassDataStructureInformation(classInterfaceDeclaration);
			} else {
			}
		}
		orderClassesAlphabetically();
	}

	public ClassDataStructure(String packageName, String classNameToConcat, ClassOrInterfaceDeclaration innerClass) {
		String innerClassName = classNameToConcat.concat("." + innerClass.getNameAsString());
		this.packageName = packageName;
		this.className = innerClassName;
		calculateClassDataStructureInformation(innerClass);
	}

	public ClassDataStructure(String packageName, String className, String nom_class, String loc_class,
			String wmc_class) {
		this.packageName = packageName;
		this.className = className;
		this.loc_class = (int) Double.parseDouble(loc_class);
		this.nom_class = (int) Double.parseDouble(nom_class);
		this.wmc_class = (int) Double.parseDouble(wmc_class);
	}

	/*
	 * public void addMethodDataStructure(int methodID, String methodName, int
	 * loc_method, int cyclo_method) { MethodDataStructure methodDataStructure = new
	 * MethodDataStructure(methodID, methodName, loc_method, cyclo_method);
	 * //this.addMethod(methodDataStructure); }
	 */

	public void addMethodDataStructure(MethodDataStructure methodDataStructure) {
		this.methodDataStructureList.add(methodDataStructure);
	}

	private void calculateClassDataStructureInformation(ClassOrInterfaceDeclaration classInterfaceDeclaration) {
		calculateClassMetrics(classInterfaceDeclaration);
		List<Node> children = classInterfaceDeclaration.getChildNodes();
		for (Node child : children) {
			if (child.getClass() == MethodDeclaration.class || child.getClass() == ConstructorDeclaration.class) {
				MethodDataStructure methodOnClass = new MethodDataStructure((CallableDeclaration) child);
				methodDataStructureList.add(methodOnClass);
			} else if (child.getClass() == ClassOrInterfaceDeclaration.class) {
				ClassOrInterfaceDeclaration innerClassInterfaceDeclaration = (ClassOrInterfaceDeclaration) child;
				ClassDataStructure innerClass = new ClassDataStructure(packageName, className,
						innerClassInterfaceDeclaration);
				innerClassList.add(innerClass);
			}
		}
	}

	private void calculateClassMetrics(ClassOrInterfaceDeclaration classInterfaceDeclaration) {
		this.wmc_class = MetricsCalculator.getWMC_class(classInterfaceDeclaration);
		this.loc_class = MetricsCalculator.getLOC_Class(classInterfaceDeclaration);
		this.nom_class = MetricsCalculator.getNOM_class(classInterfaceDeclaration);
	}

	private void orderClassesAlphabetically() {
		List<String> method_names = new ArrayList<String>();
		for (int i = 0; i < methodDataStructureList.size(); i++) {
			method_names.add(methodDataStructureList.get(i).getMethodName());
		}
		Sorter sorter = new Sorter(method_names);
		method_names = sorter.getListSorted();
		List<MethodDataStructure> return_list = new ArrayList<>();
		for (int i = 0; i < method_names.size(); i++) {
			boolean found = false;
			for (int j = 0; j < methodDataStructureList.size(); j++) {
				if (methodDataStructureList.get(j).getMethodName() != null) {
					if (methodDataStructureList.get(j).getMethodName().equals(method_names.get(i))) {
						return_list.add(methodDataStructureList.get(j));
//					found = true;
						break;
					}
				}

			}
		}
		methodDataStructureList = return_list;
	}

	public void setClassCodeSmellSpecialistValue(String codeSmellName, boolean codeSmellValue) {
		classCodeSmellSpecialistValue.put(codeSmellName, codeSmellValue);
	}

	public Boolean getClassCodeSmellSpecialistValue(String codeSmellName) {
		if (classCodeSmellSpecialistValue.containsKey(codeSmellName))
			return classCodeSmellSpecialistValue.get(codeSmellName);
		return null;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
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

	public List<MethodDataStructure> getMethodDataStructureList() {
		return methodDataStructureList;
	}

	public List<ClassDataStructure> getInnerClassesList() {
		return innerClassList;
	}

	public HashMap<String, Boolean> getCodeSmellsEvaluation() {
		return classCodeSmellSpecialistValue;
	}

	public void setClassClassificationDetected(String classificationDetected) {
		this.classClassificationDetected = classificationDetected;
	}

	public String getClassClassificationDetected() {
		return classClassificationDetected;
	}

}
