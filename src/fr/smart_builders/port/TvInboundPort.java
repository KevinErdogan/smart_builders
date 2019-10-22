package fr.smart_builders.port;

import fr.smart_builders.component.Tv;
import fr.smart_builders.interfaces.TvI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
//-------------------------------------------------------------------



/**
 * 
 * <p>Created on : 2019-10-28</p>
 * 
 * @authors <p>Zaouzaou yacine , Erdogan Kevin</p>
 *
 */
public class 		TvInboundPort
extends 		AbstractInboundPort
implements 		TvI
{
	
	

	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 1401503078307605597L;

	public 				TvInboundPort (
			String uri, 
			ComponentI owner
			) throws Exception
	{
		super (uri , TvI.class , owner);
		assert uri != null && owner instanceof Tv;
	}
	
	@Override
	public double immediateConsumption() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Tv) owner).getConsumption());
	}
}
