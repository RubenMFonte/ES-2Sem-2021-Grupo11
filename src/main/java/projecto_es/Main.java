
package projecto_es;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.github.javaparser.*;
import java.util.List;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;

public class Main {
	public static void main(String[] args) {

		JavaParser parser = new JavaParser();
		CompilationUnit teste = parser.parse("public class Teste { public int teste(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }  public int teste2(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }  public int teste3(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } }").getResult().get();
		
		CompilationUnit testClass = parser.parse("package projecto_es; "
				+ "class A {\r\n"
				+ "    "
				+ "    public int testeA(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
				+ "        class B {\r\n"
				+ "            \r\n"
				+ "            public int testeB(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
				+ "        }\r\n"
				+ "        class C {\r\n"
				+ "            \r\n"
				+ "            public int testeC(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
				+ "        }\r\n"
				+ "    }").getResult().get();
		
		//Criar Excel com um CompilationUnit
		/*javaToExcel excelGenerator = new javaToExcel();
		excelGenerator.makeLinesTESTE(testClass);
		excelGenerator.setPath_exel("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\testemain.xlsx");		
		try {
			excelGenerator.writeToExcel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//Extrair a data do Excel
		/*List<ClassDataStructure> classesPresentes = ExcelToData.getallClass("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\testemain.xlsx");
		
		for(ClassDataStructure a : classesPresentes) {
			System.out.println("Encontrei as classes " + a.getClassName());
			System.out.println("Valor do code_smell [God_class] " + a.getCodeSmellsEvaluation("God_class"));
		}
		
		//Inicializar processo de deteção
		CodeSmellsCalculator calc = new CodeSmellsCalculator();
		try {
			calc.run(classesPresentes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//Extrair a data do Excel [UPDATE]
		List<ClassDataStructure> classesJasmlNos = ExcelToData.getallClass("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\jasml_metrics.xlsx");
		System.out.println("Neste Excel gerado, estão presentes: " + classesJasmlNos.size() + " classes");
		
		List<ClassBooleanObject> classesJasmlProfs = ExcelToData.getBooleanObjects("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\Code_Smell.xlsx");
		System.out.println("Neste Excel dos profs, estão presentes: " + classesJasmlProfs.size() + " classes");
		
		//Após a extração das duas listas, aceder ao singleton da classe CodeSmellsCalculator e inicializar método run
		CodeSmellsCalculator csc = CodeSmellsCalculator.getCodeSmellsCalculatorInstance();
		try {
			csc.run(classesJasmlNos, classesJasmlProfs);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Teste para ver se captura as inner classes [Funciona]
		/*ClassDataStructure siga = new ClassDataStructure(testClass);
		System.out.println(siga.getPackageName());
		System.out.println(siga.getClassName());
		System.out.println(siga.getLOCmetric());
		System.out.println(siga.getNOMmetric());
		System.out.println(siga.getWMCmetric());
		System.out.println("Métodos da classe Mae: ");
		for(MethodDataStructure f : siga.getMethodDataStructureList()) {
			System.out.println(f.getMethodName());
		}
		System.out.println("InnerClasses: ");
		int cont = 1;
		for(ClassDataStructure f : siga.getInnerClassesList()) {
			System.out.println(cont+"º");
			System.out.println(f.getClassName());
			System.out.println(f.getLOCmetric());
			System.out.println(f.getNOMmetric());
			System.out.println(f.getWMCmetric());
			System.out.println("Métodos da classe Inner " + f.getClassName() +" : ");
			for(MethodDataStructure f2 : f.getMethodDataStructureList()) {
				System.out.println("Size " + f.getMethodDataStructureList().size());
				System.out.println(f2.getMethodName());
			}
			cont++;
		}*/
		/*List<Node> class_teste = teste.getChildNodes();
		
		
		for(Node class_find : class_teste) {
			if(class_find.getClass() == ClassOrInterfaceDeclaration.class) {
				//System.out.println("Yeah");
				ClassOrInterfaceDeclaration class_cast = (ClassOrInterfaceDeclaration)class_find;
				int wmc_class = MetricsCalculator.NOM_class(class_cast);
				//NumberOfMethods.NOM_class(class_cast);
				System.out.println("This is the value of [WMC_Class]: " + wmc_class);
			} else {
				System.out.println("Não é instância: [ClassOrInterfaceDeclaration]");
			}
			
		}*/
		
			//ABAIXO BOA FORMA PARA RESOLVER SEGUINDO METODO A METODO
		
		/*for(Node n : class_teste)
		{
			List<Node> methods = n.getChildNodes();
			//System.out.println(methods.get(1));
			
			//Resolução na main da WMC_class
			/*int sum_cyclo = 0;
			for(Node metho : methods)	{
				System.out.println("Obtained: " + metho);
				if(metho.getClass() == MethodDeclaration.class) {
					sum_cyclo += MetricsCalculator.Cyclo_method((MethodDeclaration)metho);
				}else {
					
				}
			}*/
			
			//Testar Cyclo_Method pela selação do method
			/*MethodDeclaration m = (MethodDeclaration)methods.get(methods.size()-1);
			int metric = MetricsCalculator.Cyclo_method(m);
			
			System.out.println("This is the Result: " + metric);
			System.out.println(m.toString());
		}*/

	}
}

