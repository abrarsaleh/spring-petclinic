package transformers;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.hibernate.engine.query.spi.sql.NativeSQLQueryReturn.TraceLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class ClassTransformer implements ClassFileTransformer {

	 private String targetClassName;
	 private ClassLoader targetClassLoader;
	 
	 public ClassTransformer(String targetClassName, ClassLoader targetClassLoader) {
	        this.targetClassName = targetClassName;
	        this.targetClassLoader = targetClassLoader;
	        System.out.println("ClassTransformer called for: " +targetClassName);
	    }
	
	@Override
	public byte[] transform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
			throws IllegalClassFormatException {
		//System.out.println("Entered transform class");
		
		byte[] byteCode = classfileBuffer;
		String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/"); 
		        if (!className.equals(finalTargetClassName)) {
		        	//System.out.println(className + " and " +finalTargetClassName);
		            return byteCode;
		        }
		        
		        if(className.equals(finalTargetClassName) && classLoader.equals(targetClassLoader)) {
		        	ClassPool cp = ClassPool.getDefault();
		        	System.out.println("Hit for: " +className);
	                try {
						CtClass cc = cp.get(targetClassName);
						
						CtMethod[] methods = cc.getMethods();
					
						for(CtMethod cm : methods) {
							if(cm == null) continue;
							 cc = cp.get(targetClassName);
							System.out.println("At method: " +cm);
							
							
							if(cm.hasAnnotation(GetMapping.class) || cm.hasAnnotation(PostMapping.class)) {
								System.out.println("which has an annotation match.");
								if(cc.isFrozen()) cc.defrost();
								cm.addLocalVariable("elapsedTime", CtClass.longType);
				                cm.insertBefore("{elapsedTime = System.currentTimeMillis();}");
				                cm.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
				                        + "System.out.println(\"Method Executed in ms: \" + elapsedTime);}", false);
				                
				                cc.toClass(targetClassLoader, protectionDomain);
				                //byte[] newCode = cc.toBytecode();
				                //byteCode = newCode;
							}
							
						}
						cc.detach();
						
					} catch (NotFoundException | CannotCompileException | RuntimeException e) {
						System.out.println("Transformation unsuccessful");
						e.printStackTrace();
					}
		        }
		        return byteCode;
	}

}
