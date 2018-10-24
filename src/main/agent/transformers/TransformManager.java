package transformers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.lang.reflect.*;

public class TransformManager {
	
	private ArrayList<Class> holder = new ArrayList<Class>();
	

	private ArrayList<Class> classesToTransform = new ArrayList<Class>();
	private ArrayList<Method> methodsToTransform = new ArrayList<Method>();
	public TransformManager(Instrumentation inst) {
		File file = new File("classNameHolder.txt");
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				holder.add(Class.forName(line));
				
			}
			
			fileReader.close();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Class> parseAllClasses(){
		
		return holder;
		
	}
	/*
	public ArrayList<Method> parseAllMethods(List classesToTransform2) {
		
		for(Object o : classesToTransform2) {
			Class c = (Class) o;
			
			for(Method m : c.getMethods()) {
				if(m.isAnnotationPresent(GetMapping.class) || m.isAnnotationPresent(PostMapping.class)) {
					methodsToTransform.add(m);
				}
			}
			
		}
		return methodsToTransform;
	}
	
	private void transformClass(String className) {
		try {
			Class<?> targetClass = Class.forName(className);
			ClassLoader classLoader = targetClass.getClassLoader();
			transform(targetClass, classLoader, inst);
		} catch (ClassNotFoundException e) {
			System.err.println("Class " +className +" not found.");
			e.printStackTrace();
		}	
		
	}

	private static void transform(Class<?> targetClass, ClassLoader classLoader, Instrumentation inst) {
		// TODO Auto-generated method stub
		
	}*/

	

}
