package projecto_es;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
//import com.github.javaparser.ast.body.
import com.github.javaparser.ast.body.MethodDeclaration;

public class ClassDataStructure {

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

	private String packageName; // PackageDeclaration
	private String className; // ClassOrInterfaceDeclaration
	private int nom_class;
	private int loc_class;
	private int wmc_class;
	private List<MethodDataStructure> lmds = new ArrayList<>();
	private List<ClassDataStructure> innerClasses = new ArrayList<>();
	private HashMap<String, Boolean> codeSmellsEvaluation = new HashMap<>();
	private String codeSmellDetected;
	

	public ClassDataStructure(CompilationUnit javaFile) {
		List<Node> children = javaFile.getChildNodes();
		for (Node child : children) {
			if (child.getClass() == PackageDeclaration.class) {
				PackageDeclaration pd = (PackageDeclaration) child;
				String packageName = pd.getNameAsString();
				this.packageName = packageName;

			} else if (child.getClass() == ClassOrInterfaceDeclaration.class) {
				ClassOrInterfaceDeclaration cid = (ClassOrInterfaceDeclaration) child;
				String className = cid.getNameAsString();
				this.className = className;
				calculateMetricsStoreMethods(cid);
			} else {
			}
		}
	}
	
	public ClassDataStructure(String packag, String classNameToConcat, ClassOrInterfaceDeclaration innerClass) {
		String innerClassName = classNameToConcat.concat("."+innerClass.getNameAsString());
		this.packageName = packag;
		this.className = innerClassName;
		calculateMetricsStoreMethods(innerClass);
	}

	public ClassDataStructure(String packageName, String className, String nom_class, String loc_class,
			String wmc_class) {
		this.packageName = packageName;
		this.className = className;
		this.loc_class = (int) Double.parseDouble(loc_class);
		this.nom_class = (int) Double.parseDouble(nom_class);
		this.wmc_class = (int) Double.parseDouble(wmc_class);
	}

	public void addMethod(int methodID, String methodName, int loc_method, int cyclo_method) {
		MethodDataStructure mds = new MethodDataStructure(methodID, methodName, loc_method, cyclo_method);
		this.addMethod(mds);
	}

	public void addMethod(MethodDataStructure mds) {
		this.lmds.add(mds);
	}

	private void calculateMetricsStoreMethods(ClassOrInterfaceDeclaration cid) {
		this.wmc_class = MetricsCalculator.getWMC_class(cid);
		this.loc_class = MetricsCalculator.getLOC_Class(cid);
		this.nom_class = MetricsCalculator.getNOM_class(cid);	
		for (Node n : cid.getChildNodes()) {
			if (n.getClass() == MethodDeclaration.class || n.getClass() == ConstructorDeclaration.class) {
				System.out.println("Class name: " + n.getClass().getName());
				MethodDataStructure mds_part = new MethodDataStructure((CallableDeclaration) n);
				lmds.add(mds_part);
			} else if(n.getClass() == ClassOrInterfaceDeclaration.class) {
				ClassOrInterfaceDeclaration decl = (ClassOrInterfaceDeclaration) n ;
				ClassDataStructure inner = new ClassDataStructure(packageName, className, decl);
				innerClasses.add(inner);
			}
		}
		/*List<MethodDeclaration> methods = cid.getMethods();
		for (MethodDeclaration md : methods) {
			MethodDataStructure mds_part = new MethodDataStructure(md);
			lmds.add(mds_part);
		}*/
	}
	
	public List<MethodDataStructure> alphbeticOrder(List<MethodDataStructure> list) {
		List<String> method_names = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			method_names.add(list.get(i).getMethodName());
		}
		method_names = method_names.stream().sorted().collect(Collectors.toList());
		List<MethodDataStructure> return_list = new ArrayList<>();
		for (int i = 0; i < method_names.size(); i++) {
			boolean found = false;
			for (int j = 0; found; j++) {
				if (list.get(j).getMethodName().equals(method_names.get(i))) {
					return_list.add(list.get(j));
					found = true;
				}
			}
		}
		return return_list;
	}

	public void setCodeSmellsEvaluation(String codeSmell, boolean codeSmellEvaluation) {
		codeSmellsEvaluation.put(codeSmell, codeSmellEvaluation);
	}

	public Boolean getCodeSmellsEvaluation(String codeSmell) {
		if (codeSmellsEvaluation.containsKey(codeSmell))
			return codeSmellsEvaluation.get(codeSmell);
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
		return lmds;
	}
	
	public List<ClassDataStructure> getInnerClassesList() {
		return innerClasses;
	}
	
	public HashMap<String, Boolean>  getCodeSmellsEvaluation(){
		return codeSmellsEvaluation;
	}
	
	public void setCodeSmellDetected(String codeSmellDetected) {
		this.codeSmellDetected = codeSmellDetected;
	}
	
	public String getCodeSmellDetected() {
		return codeSmellDetected;
	}

}
