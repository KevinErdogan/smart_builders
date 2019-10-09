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
	
	protected String uriFridge;
	
	
	public void deploy() throws Exception{
		assert !this.deploymentDone();
		
		uriFridge = AbstractComponent.createComponent(Fridge.class.getCanonicalName(), new Object[] {URIFridge, URIFridgeInbountPort});
		assert isDeployedComponent(uriFridge);
	}
}
