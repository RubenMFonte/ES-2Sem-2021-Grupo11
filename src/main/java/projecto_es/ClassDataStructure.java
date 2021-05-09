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
/**
 * Esta classe foi criada para guardar os dados necessários (que irão ser
 * introduzidos, posteriormente, no ficheiro excel) como: as métricas relativas
 * à classe e aos métodos; o package onde está o ficheiro java; o nome da
 * classe; o nome do método; etc. Na geração de um objeto da classe, é recebido
 * como parâmetro um [CompilationUnit]. Apartir desse [CompilationUnit], vai ser
 * retirado o PackageName através do [PackageDeclaration] e a ClassName através
 * da [ClassOrInterfaceDeclaration], para além disso, é também calculado as
 * métricas relativas à Classe: [NOM, LOC, WMC].
 *<p>
 * Como atributo, também está presente a estrutura: List de {@link MethodDataStructure} que
 * consiste numa lista desta classe [MethodDataStruture] cujos atributos são:
 * private String methodName; private int loc_method; private int cyclo_method;
 * Portanto, cada objeto da classe [MethodDataStruture] possui o nome do método
 * e os valores das métricas a ele respetivas (estas são, igualmente, calculadas
 * na classe [MethodDataStruture] na sequência de definir o valor destes
 * atributos - à semelhança do que acontece com as métricas da classe)
 * <p>
 * Como uma classe pode possuir vários métodos, é relevante/necessário que
 * exista uma lista em que cada entrada possua o nome do método e os valores das
 * métricas do respetivo método)
 * <p>
 * Os objetos da classe [MethodDataStruture] vão ser diretamente instancados
 * após a instância da classe [ClassDataStruture], pois no método
 * [calculateMetricsStoreMethods(ClassOrInterfaceDeclaration cid)] irá ser
 * verificado cada nó filho de [cid] cuja classe seja [MethodDeclaration].
 * Aqueles que assim o forem , irão servir de parâmetro para a construção de um
 * objeto [MethodDataStruture]
 * </p>
 */

public class ClassDataStructure extends ClassObjects{
	
	/**
	 * Number of methods
	 */
	private int nom_class;
	/**
	 * Number of lines
	 */
	private int loc_class;
	/**
	 *<a href="https://en.wikipedia.org/wiki/Cyclomatic_complexity">Cyclomatic complexity</a>
	 */
	private int wmc_class;
	
	/**
	 * <p>A Hash Map that receives the key is a String with the name of the code smell and a value Boolean that represents if the code smells is true or false</p>
	 */
	private HashMap<String, Boolean> classCodeSmellSpecialistValue = new HashMap<>();
	//Atributos necessários
	private List<ClassDataStructure> innerClassList = new ArrayList<>();
	private String classClassificationDetected;
	
	/**
	 * <p>Creates a ClassDataStructure using a Compilation Unit from a java class</p>
	 * @param javaFile CompilationUnit created from a class of the java project selected ({@link MetricsCalculator#compilationUnits})
	 */
	public ClassDataStructure(CompilationUnit javaFile) {
		super("","");
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
	}
	
	/**
	 * <p>Creates a ClassDataStructure using the arguments.</p>
	 * @param packageName Name of the package where the class is 
	 * @param className Name of the class
	 * @param nom_class	{@link nom_class}
	 * @param loc_class	{@link loc_class}
	 * @param wmc_class {@link wmc_class}
	 */
	public ClassDataStructure(String packageName, String className, String nom_class, String loc_class,
			String wmc_class) {
		super(packageName, className);
		this.loc_class = (int) Double.parseDouble(loc_class);
		this.nom_class = (int) Double.parseDouble(nom_class);
		this.wmc_class = (int) Double.parseDouble(wmc_class);
	}
	
	/**
	 * <p></p>
	 * @param packageName Name of the package where the class is
	 * @param classNameToConcat
	 * @param innerClass
	 */
	public ClassDataStructure(String packageName, String classNameToConcat, ClassOrInterfaceDeclaration innerClass) {
		super("","");
		String innerClassName = classNameToConcat.concat("." + innerClass.getNameAsString());
		this.packageName = packageName;
		this.className = innerClassName;
		calculateClassDataStructureInformation(innerClass);
	}
	
	/**
	 * <p>Creates an object MethodDataStructure using the arguments and adds it to the list of methods.</p>
	 * @param methodID The method's ID in the excel file
	 * @param methodName The method name
	 * @param loc_method The number of lines in the method
	 * @param cyclo_method Cyclomatic complexity of the method
	 */
	public void addMethodDataStructure(int methodID, String methodName, int loc_method, int cyclo_method) {	
		MethodDataStructure mds = new MethodDataStructure(methodID, methodName, loc_method, cyclo_method);
		this.addMethod(mds);
	}
	
	/**
	 * 
	 * @param classInterfaceDeclaration
	 */
	private void calculateClassDataStructureInformation(ClassOrInterfaceDeclaration classInterfaceDeclaration) {
		this.wmc_class = MetricsCalculator.getWMC_class(classInterfaceDeclaration);
		this.loc_class = MetricsCalculator.getLOC_Class(classInterfaceDeclaration);
		this.nom_class = MetricsCalculator.getNOM_class(classInterfaceDeclaration);
		List<Node> children = classInterfaceDeclaration.getChildNodes();
		for (Node child : children) {
			if (child.getClass() == MethodDeclaration.class || child.getClass() == ConstructorDeclaration.class) {
				MethodDataStructure methodOnClass = new MethodDataStructure((CallableDeclaration) child);
				this.lmds.add(methodOnClass);
			} else if (child.getClass() == ClassOrInterfaceDeclaration.class) {
				ClassOrInterfaceDeclaration innerClassInterfaceDeclaration = (ClassOrInterfaceDeclaration) child;
				ClassDataStructure innerClass = new ClassDataStructure(packageName, className, innerClassInterfaceDeclaration);
				innerClassList.add(innerClass);
			}
		}
		/*List<MethodDeclaration> methods = cid.getMethods();
		for (MethodDeclaration md : methods) {
			MethodDataStructure mds_part = new MethodDataStructure(md);
			lmds.add(mds_part);
		}*/
	}
	
	/**
	 * Returns {@link innerClassList}
	 * @return {@link innerClassList}
	 */
	public List<ClassDataStructure> getInnerClassesList() {
		return innerClassList;
	}	
	
	/**
	 * <p>Takes the name of a code smell and if it's true or false to the class, then adds the pair to the HashMap {@link classCodeSmellSpecialistValue}</p> 
	 * @param codeSmellName Name of the code smell
	 * @param codeSmellValue Indicates if the class has the code smell
	 */
	public void setClassCodeSmellSpecialistValue(String codeSmellName, boolean codeSmellValue) {
		classCodeSmellSpecialistValue.put(codeSmellName, codeSmellValue);
	}
	
	/**
	 * Returns the value of the boolean that pairs with the given code smell.
	 * @param codeSmellName Name of the code smell
	 * @return A boolean, null of the code smell doens't exist in the hash map.
	 */
	public Boolean getClassCodeSmellSpecialistValue(String codeSmellName) {
		if (classCodeSmellSpecialistValue.containsKey(codeSmellName))
			return classCodeSmellSpecialistValue.get(codeSmellName);
		return null;
	}
	/**
	 * Returns {@link nom_class}
	 * @return {@link nom_class}
	 */
	public int getNOMmetric() {
		return nom_class;
	}
	
	/**
	 * Returns {@link loc_class}
	 * @return {@link loc_class}
	 */
	public int getLOCmetric() {
		return loc_class;
	}
	
	/**
	 * Returns {@link wmc_class}
	 * @return {@link wmc_class}
	 */
	public int getWMCmetric() {
		return wmc_class;
	}
	
	/**
	 * Returns {@link classCodeSmellSpecialistValue} 
	 * @return {@link classCodeSmellSpecialistValue} 
	 */
	public HashMap<String, Boolean> getCodeSmellsEvaluation() {
		return classCodeSmellSpecialistValue;
	}
	
	/**
	 * 
	 * @param classificationDetected
	 */
	public void setClassClassificationDetected(String classificationDetected) {
		this.classClassificationDetected = classificationDetected;
	}
	
	/**
	 * Returns {@link classClassificationDetected}
	 * @return {@link classClassificationDetected}
	 */
	public String getClassClassificationDetected() {
		return classClassificationDetected;
	}


}
