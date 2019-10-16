package fr.smart_builders.system;

import fr.smart_builders.system.components.Fridge;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

public class CVM extends AbstractCVM{
	
	// Fridge
	protected static final String URIFridge = "my-precious-fridge";
	protected static final String URIFridgeInbountPort = "fridge-iport";

	public CVM() throws Exception {
		super();
	}
	
	
	
	public void deploy() throws Exception{
		assert !this.deploymentDone();
		
		String uriFridge = 
				AbstractComponent.createComponent(
						Fridge.class.getCanonicalName(),
						new Object[] {URIFridge, URIFridgeInbountPort});
		assert isDeployedComponent(uriFridge);
		toggleTracing(uriFridge);
		
		super.deploy();
	}
	
	
	public static void main(String[] args) {
		try {
			CVM cvm = new CVM() ;
			cvm.startStandardLifeCycle(60000L) ;
			Thread.sleep(5000L) ;
			System.exit(0) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}		
	}
}
