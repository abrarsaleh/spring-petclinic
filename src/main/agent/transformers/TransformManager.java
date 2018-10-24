package transformers;

import java.lang.annotation.Annotation;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javassist.ClassPool;
import javassist.CtClass;
import java.lang.reflect.*;

public class TransformManager {
	
	final private Class<?>[] holder = Object.class.getClasses();
	
	private ArrayList<Class> classesToTransform = new ArrayList<Class>();
	private ArrayList<Method> methodsToTransform = new ArrayList<Method>();
	private Instrumentation inst;

	
	public TransformManager(Instrumentation inst) {
		this.inst = inst;
		//cp = ClassPool.getDefault();
	}
	
	public ArrayList<Class> parseAllClasses(){
		
		for(Class<?> c : holder) {
			
			if(c.isAnnotationPresent(Controller.class)) {
				classesToTransform.add(c);
			}
		}
		
		
		return classesToTransform;
		
	}
	
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
		
	}

	

}
