package core;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Method;
import java.util.List;

import transformers.ClassTransformer;
import transformers.TransformManager;

public class InstrumentationAgent {

	public static void premain(String args, Instrumentation inst) {
		
		String className = "spring-petclinic.src.main.java.org.springframework.samples.petclinic.PetClinicApplication";
		TransformManager former = new TransformManager(inst);
		
		List<Class> classesToTransform = former.parseAllClasses();
		
		for(Class c : classesToTransform) {
			ClassLoader targetClassLoader = c.getClassLoader();
			ClassTransformer ctformer = new ClassTransformer(c.getName(), targetClassLoader);
			inst.addTransformer(ctformer, true);
			
			try {
				inst.retransformClasses(c);
			} catch (UnmodifiableClassException e) {
				System.err.println("Couldn't transform class " +c.getName());
				e.printStackTrace();
			}
		}
		
	}
	
	public static void agentmain(String args, Instrumentation inst) {
		String className = "spring-petclinic.src.main.java.org.springframework.samples.petclinic.PetClinicApplication";
		TransformManager former = new TransformManager(inst);
		
		List<Class> classesToTransform = former.parseAllClasses();
		
		for(Class c : classesToTransform) {
			ClassLoader targetClassLoader = c.getClassLoader();
			ClassTransformer ctformer = new ClassTransformer(c.getName(), targetClassLoader);
			inst.addTransformer(ctformer, true);
			
			try {
				inst.retransformClasses(c);
			} catch (UnmodifiableClassException e) {
				System.err.println("Couldn't transform class " +c.getName());
				e.printStackTrace();
			}
		}
		
		
	}

	
	
}
