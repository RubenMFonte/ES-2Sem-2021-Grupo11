//package projecto_es;
//
//import java.io.FileNotFoundException;
//import com.github.javaparser.*;
//import java.util.List;
//import com.github.javaparser.ast.*;
//import com.github.javaparser.ast.body.*;
//
//public class Main {
//	public static void main(String[] args) {
//		JavaParser parser = new JavaParser();
//		CompilationUnit teste = parser.parse(
//				"public class Teste { public int teste(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } public int teste2(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } public int teste3(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } }")
//				.getResult().get();
//
//		CompilationUnit testClass = parser.parse("package projecto_es; " + "class A {\r\n" + " "
//				+ " public int testeA(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
//				+ " class B {\r\n" + " \r\n"
//				+ " public int testeB(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
//				+ " }\r\n" + " class C {\r\n" + " \r\n"
//				+ " public int testeC(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
//				+ " }\r\n" + " }").getResult().get();
//
//		// Criar Excel com um CompilationUnit
//		javaToExcel excelGenerator = new javaToExcel();
//		excelGenerator.makeLinesTESTE(testClass);
//		excelGenerator.setPath_exel("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\testemain.xlsx");
//		// Extrair para dados apartir de Excel
//
//		try {
//			excelGenerator.writeToExcel();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		// Teste para ver se captura as inner classes [Funciona]
////		ClassDataStructure siga = new ClassDataStructure(testClass);
////		System.out.println(siga.getPackageName());
////		System.out.println(siga.getClassName());
////		System.out.println(siga.getLOCmetric());
////		System.out.println(siga.getNOMmetric());
////		System.out.println(siga.getWMCmetric());
////		System.out.println("Métodos da classe Mae: ");
////		for (MethodDataStructure f : siga.getMethodDataStructureList()) {
////			System.out.println(f.getMethodName());
////		}
////		System.out.println("InnerClasses: ");
////		int cont = 1;
////		for (ClassDataStructure f : siga.getInnerClassesList()) {
////			System.out.println(cont + "º");
////			System.out.println(f.getClassName());
////			System.out.println(f.getLOCmetric());
////			System.out.println(f.getNOMmetric());
////			System.out.println(f.getWMCmetric());
////			System.out.println("Métodos da classe Inner " + f.getClassName() + " : ");
////			for (MethodDataStructure f2 : f.getMethodDataStructureList()) {
////				System.out.println("Size " + f.getMethodDataStructureList().size());
////				System.out.println(f2.getMethodName());
////			}
////			cont++;
//		}
//
//	}
//
