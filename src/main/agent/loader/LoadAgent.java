package loader;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.ServiceConfigurationError;
import java.util.stream.Stream;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.sun.tools.attach.spi.AttachProvider;

@SuppressWarnings("restriction")
public class LoadAgent {

	@SuppressWarnings("restriction")
	public static void run(String[] args) {
		System.out.println("Running agent");
		 String agentFilePath = "D:/spring-petclinic/spring-petclinic/src/main/agent/core/InstrumentationAgent.java";
		 String applicationName = "PetClinicApplication";
	     /*
		 Optional<String> jvmProcessOpt = Optional.ofNullable(VirtualMachine.list()
		          .stream()
		          .filter(jvm -> {
		              System.out.println("jvm: " +jvm.displayName());
		              return jvm.displayName().contains(applicationName);
		          })
		          .findFirst().get().id());

		        if(!jvmProcessOpt.isPresent()) {
		        	System.out.println("Target Application not found");
		            return;
		        }
		            File agentFile = new File(agentFilePath);
		            
		            try {
		                String jvmPid = jvmProcessOpt.get();
		               System.out.println("Attaching to target JVM with PID: " + jvmPid);
		                VirtualMachine jvm = VirtualMachine.attach(jvmPid);
		                jvm.loadAgent(agentFile.getAbsolutePath());
		                jvm.detach();
		                System.out.println("Attached to target JVM and loaded Java agent successfully");
		            } catch (Exception e) {
		                throw new RuntimeException(e);
		            }
		            */
		 String goalId = "EMPTY";        
	      Object[] goalvm = null;
		try {
			System.out.println(VirtualMachine.list());
			goalvm = VirtualMachine.list().stream().toArray();

				} catch (Exception e) {
					System.out.println("PID was : " +goalvm);
				}
		 
	     // System.out.println(AttachProvider.providers());
	      for(Object o : goalvm) {
	    	  VirtualMachineDescriptor cjvm = (VirtualMachineDescriptor) o;
	    	   System.out.println("Display Name: " +cjvm.displayName());
	    	   if(cjvm.displayName().contains("PetClinicApplication")) {
	    		   goalId = cjvm.id();
	    		   System.out.println("FOUND VM: " +cjvm.displayName());
	    	   }
	       }
	       
	       File agentFile = new File(agentFilePath);
	        VirtualMachine jvm;
		
				try {
					jvm = VirtualMachine.attach(goalId);
					jvm.loadAgent(agentFile.getAbsolutePath());
				    jvm.detach();
				    System.out.println("Transformation Successful");
				} catch (AttachNotSupportedException e) {
					// TODO Auto-generated catch block
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
				
				}
				 
			
	        
	        
	        
	       
		        }

	}

