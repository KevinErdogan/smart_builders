package fr.sorbonne_u.smartgrid.port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import fr.sorbonne_u.smartgrid.coponenet.Controler;
import fr.sorbonne_u.smartgrid.interfaces.ControlerFridgeI;

public class 	ControlerFridgeOutboundPort 
extends 		AbstractOutboundPort 
implements 		ControlerFridgeI 
{
	
	
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 5832314809682832053L;

	public 		ControlerFridgeOutboundPort ( 
			String uri, 
			ComponentI owner
			
			) throws Exception 
	{
		super (uri , ControlerFridgeI.class , owner);
		assert uri != null && owner instanceof Controler;
	}

	@Override
	public boolean switchOn() throws Exception {
		return ((ControlerFridgeI) this.connector).switchOn();
	}

	@Override
	public boolean switchOff() throws Exception {
		return ((ControlerFridgeI) this.connector).switchOff();
	}

	@Override
	public double immediateConsumption() throws Exception {
		return ((ControlerFridgeI) this.connector).immediateConsumption();
	}

}
