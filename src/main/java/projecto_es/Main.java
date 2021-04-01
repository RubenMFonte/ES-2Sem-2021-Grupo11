package projecto_es;

import java.io.FileNotFoundException;
import com.github.javaparser.*;
import java.util.List;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;

public class Main {
	public static void main(String[] args) {

		JavaParser parser = new JavaParser();
		CompilationUnit teste = parser.parse("public class Teste { public int teste(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }  public int teste2(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }  public int teste3(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } }").getResult().get();
		
		List<Node> class_teste = teste.getChildNodes();
		
		for(Node class_find : class_teste) {
			if(class_find.getClass() == ClassOrInterfaceDeclaration.class) {
				//System.out.println("Yeah");
				ClassOrInterfaceDeclaration class_cast = (ClassOrInterfaceDeclaration)class_find;
				int wmc_class = MetricsCalculator.WMC_class(class_cast);
				System.out.println("This is the value of [WMC_Class]: " + wmc_class);
			} else {
				System.out.println("Não é instância: [ClassOrInterfaceDeclaration]");
			}
			
		}
		
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
