package core;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.PetClinicApplication;

import transformers.ClassTransformer;
import transformers.TransformManager;

public class InstrumentationAgent {

	public static void premain(String args, Instrumentation inst) {
		
	
		
		for (int i = 0; i < 1; i++) {
			System.out.println("AGENT HAS STARTED");
		}
		TransformManager former = new TransformManager(inst);
		
		List<Class> classesToTransform = former.parseAllClasses();
		
		
		for(Class c : classesToTransform) {
			System.out.println("ITERATING");
			System.out.println(c);
			ClassLoader targetClassLoader = c.getClassLoader();
			transform(c, targetClassLoader, inst);
		}
		System.out.println(classesToTransform);
		
	}
	
	private static void transform(Class c, ClassLoader targetClassLoader, Instrumentation inst) {
		System.out.println("Currently transforming: " +c);
		ClassTransformer ctformer = new ClassTransformer(c.getName(), targetClassLoader);
		inst.addTransformer(ctformer, true);
		
		try {
			inst.retransformClasses(c);
			System.out.println("Transformation successful");
		} catch (UnmodifiableClassException e) {
			System.err.println("Couldn't transform class " +c.getName());
			e.printStackTrace();
		}
		
	}



	
	
}
