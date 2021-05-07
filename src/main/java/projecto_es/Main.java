
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
				+ "            public int testeCinterior(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
				+ "        }\r\n"
				+ "        class D {\r\n"
				+ "            \r\n"
				+ "            public int testeD(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
				+ "        }\r\n"
				+ "    }").getResult().get();
		
		CompilationUnit testParameters = parser.parse("package projecto_es; "
				+ "class A {\r\n"
				+ "    "
				+ "    public int testeA(ClassDataStructure a, MethodDataStructure b, ExceptionTable.Opcode d) { "
				+ "			 try {\r\n"
				+ "      		int[] myNumbers = {1, 2, 3};\r\n"
				+ "      		System.out.println(myNumbers[10]);\r\n"
				+ "    		 } catch (Exception e) {\r\n"
				+ "      		System.out.println(\"Something went wrong.\");\r\n"
				+ "    		}"
				+ "	   }\r\n" 
				+ "}").getResult().get();
		
		CompilationUnit testClassWithout = parser.parse("package projecto_es; "
				+ "class JavaClass {\r\n"
				+ "    "
				+ "    private String a;"
				+ "    		private String e;"
				+ "    private String i;"
				+ "    		private String o;"
				+ "    				private String u;"
				+ "	   \r\n" 
				+ "}").getResult().get();
		
	
		//Teste para ver argumentos dos metodos
		/*ClassOrInterfaceDeclaration cid = (ClassOrInterfaceDeclaration) testParameters.getChildNodesByType(ClassOrInterfaceDeclaration.class).get(0);
		System.out.println("MAIN CID " + cid.getNameAsString());
		MethodDeclaration md  = (MethodDeclaration) cid.getChildNodesByType(MethodDeclaration.class).get(0);
		System.out.println("MAIN METHOD MD " + md.getNameAsString());
		String a = MethodDataStructure.getParameterTypesTEST(md);
		System.out.println("ARGUMENTOS FOUND: " + a);
		System.out.println("NOME METODO FINAL: " + md.getNameAsString().concat(a));*/
		
		//Criar Excel com um CompilationUnit
		/*JavaToExcel excelGenerator = new JavaToExcel();
		excelGenerator.makeClassDataStructureListTESTE(testClass);
		excelGenerator.setPath_exel("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\excels tests main\\testemain_metrics.xlsx");		
		try {
			excelGenerator.makeLines();
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
		
		//Inicializar processo de dete��o
		CodeSmellsCalculator calc = new CodeSmellsCalculator();
		try {
			calc.run(classesPresentes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//Extrair a data do Excel [UPDATE]
		List<ClassDataStructure> classesJasmlNos = ExcelToData.getallClass("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\excels tests main\\testemain_metrics.xlsx");
		System.out.println("Neste Excel gerado, est�o presentes: " + classesJasmlNos.size() + " classes");
		
		List<ClassBooleanObject> classesJasmlProfs = ExcelToData.getBooleanObjects("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\excels tests main\\testemain_profs.xlsx");
		System.out.println("Neste Excel dos profs, est�o presentes: " + classesJasmlProfs.size() + " classes");
		
		//Ap�s a extra��o das duas listas, aceder ao singleton da classe CodeSmellsCalculator e inicializar m�todo run
		/*CodeSmellsCalculator csc = CodeSmellsCalculator.getCodeSmellsCalculatorInstance();
		try {
			csc.run(classesJasmlNos, classesJasmlProfs);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//Teste para ver se captura as inner classes [Funciona]
		/*ClassDataStructure siga = new ClassDataStructure(testClass);
		System.out.println(siga.getPackageName());
		System.out.println(siga.getClassName());
		System.out.println(siga.getLOCmetric());
		System.out.println(siga.getNOMmetric());
		System.out.println(siga.getWMCmetric());
		System.out.println("M�todos da classe Mae: ");
		for(MethodDataStructure f : siga.getMethodDataStructureList()) {
			System.out.println(f.getMethodName());
		}
		System.out.println("InnerClasses: ");
		int cont = 1;
		for(ClassDataStructure f : siga.getInnerClassesList()) {
			System.out.println(cont+"�");
			System.out.println(f.getClassName());
			System.out.println(f.getLOCmetric());
			System.out.println(f.getNOMmetric());
			System.out.println(f.getWMCmetric());
			System.out.println("M�todos da classe Inner " + f.getClassName() +" : ");
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
				System.out.println("N�o � inst�ncia: [ClassOrInterfaceDeclaration]");
			}
			
		}*/
		
			//ABAIXO BOA FORMA PARA RESOLVER SEGUINDO METODO A METODO
		
		/*for(Node n : class_teste)
		{
			List<Node> methods = n.getChildNodes();
			//System.out.println(methods.get(1));
			
			//Resolu��o na main da WMC_class
			/*int sum_cyclo = 0;
			for(Node metho : methods)	{
				System.out.println("Obtained: " + metho);
				if(metho.getClass() == MethodDeclaration.class) {
					sum_cyclo += MetricsCalculator.Cyclo_method((MethodDeclaration)metho);
				}else {
					
				}
			}*/
			
			//Testar Cyclo_Method pela sela��o do method
			/*MethodDeclaration m = (MethodDeclaration)methods.get(methods.size()-1);
			int metric = MetricsCalculator.Cyclo_method(m);
			
			System.out.println("This is the Result: " + metric);
			System.out.println(m.toString());
		}*/

	}
}

