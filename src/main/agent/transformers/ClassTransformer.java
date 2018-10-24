package transformers;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.springframework.web.bind.annotation.GetMapping;

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
	    }
	
	@Override
	public byte[] transform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
			throws IllegalClassFormatException {
		byte[] byteCode = classfileBuffer;
		String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/"); 
		        if (!className.equals(finalTargetClassName)) {
		            return byteCode;
		        }
		        
		        if(className.equals(targetClassName) && classLoader.equals(targetClassLoader)) {
		        	ClassPool cp = ClassPool.getDefault();
	                try {
						CtClass cc = cp.get(targetClassName);
						
						for(CtMethod cm : cc.getDeclaredMethods()) {
							
							if(cm.hasAnnotation(GetMapping.class)) {
								 cm.addLocalVariable("startTime", CtClass.longType);
					             cm.insertBefore("startTime = System.currentTimeMillis();");
					             
					             StringBuilder endBlock = new StringBuilder();

					                cm.addLocalVariable("endTime", CtClass.longType);
					                cm.addLocalVariable("totalTime", CtClass.longType);
					                endBlock.append("endTime = System.currentTimeMillis();");
					                endBlock.append("totalTime = (endTime-startTime)/1000;");
					                cm.insertAfter(endBlock.toString());
					                
					                byteCode = cc.toBytecode();
					                cc.detach();
					                
					                
							}
						}
						
					} catch (NotFoundException | CannotCompileException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        return byteCode;
	}

}
