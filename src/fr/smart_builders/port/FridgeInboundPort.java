package fr.sorbonne_u.smartgrid.port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.smartgrid.coponenet.Fridge;
import fr.sorbonne_u.smartgrid.interfaces.FridgeI;

//---------------------------------------------------------------------------------

/**
 * 
 * 
 * <p>Created on : 2019-10-10</p>
 * 
 * @author <p>Zaouzaou Yacine , Erdogan Kevin</p>
 */
public class 					FridgeInboundPort 
extends 		AbstractInboundPort
implements		FridgeI
{

	
	/**
	 * generated 
	 */
	private static final long serialVersionUID = -2053378145004153472L;

	
	
	public 			FridgeInboundPort(
			String uri,  
			ComponentI owner
			) throws Exception 
	{
		super(uri, FridgeI.class,  owner);
		assert uri != null && owner instanceof Fridge;
	}
	
	
	
	@Override
	public double immediateConsumption() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Fridge) owner).getConsumption());
		
	}

	@Override
	public boolean switchOn() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Fridge) owner).allowStart());

	}

	@Override
	public boolean switchOff() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Fridge) owner).forbidStart());
	}

}
