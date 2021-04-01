package projecto_es;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class MainTeste {

	public static void main(String[] args) {
		JavaParser parser = new JavaParser();
		
				//TESTE BASE
		/*CompilationUnit teste = parser.parse("package com.github.javaparser; public class Teste { public int teste(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }  public int teste2(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } }").getResult().get();
			
		DataStructure data = new DataStructure(teste);
		System.out.println("Este é o nome do package: " + data.getPackageName());
		System.out.println("Este é o nome da classe: " + data.getClassName());*/
		
				//TESTE PERCORRER COMPILATION UNITS
		List<CompilationUnit> compilationUnits = new ArrayList<CompilationUnit>();
		CompilationUnit teste = parser.parse("package com.github.javaparser; public class Teste { public int teste(boolean t, String d) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }  public int teste2(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } }").getResult().get();
		CompilationUnit teste2 = parser.parse("package com.github.java; public class TestTest { public int teste(boolean t, int d) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } }").getResult().get();
		compilationUnits.add(teste);
		compilationUnits.add(teste2);
        //System.out.println("ArrayList : " + compilationUnits.toString());
        
        int unit_id = 1;
        for(CompilationUnit unit : compilationUnits) {
        	ClassDataStructure data = new ClassDataStructure(unit);
        	System.out.println(unit_id + "º unit:" + " Este é o nome do package: " + data.getPackageName());
    		System.out.println(unit_id + "º unit:" + " Este é o nome da classe: " + data.getClassName());
    		System.out.println(unit_id + "º unit:" + " A métrica [WMC_class]: " + data.getWMCmetric());
    		List<MethodDataStructure> mds = data.getMethodDataStructureList();
    		for(MethodDataStructure mds_ind : mds) {
    			System.out.println(unit_id + "º unit:" + " O método: " + mds_ind.getMethodName() + " com a métrica [Cyclo_method]: " + mds_ind.getCYCLOMetric());
    		}
    		unit_id++;
        }
		
			//TESTAR LEITURA CLASSE METHOD...
		/*CompilationUnit teste = parser.parse("package com.github.javaparser; public class Teste { public int teste(boolean t, String d) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }  public int teste2(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } }").getResult().get();
		List<Node> a = teste.getChildNodes();
		for(Node b : a) {
			if(b.getClass() == ClassOrInterfaceDeclaration.class) {
				ClassOrInterfaceDeclaration c = (ClassOrInterfaceDeclaration)b;
				MethodDeclaration d = c.getMethods().get(0);
				MethodDataStructure e = new MethodDataStructure(d);
				System.out.println("Nome do método: " + e.getMethodName());
				System.out.println("Metric [CYCLO]: " + e.getCYCLOMetric());
			}else {
				
			}
		}*/
		
		
	}

}
