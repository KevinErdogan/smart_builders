package fr.smart_builders.port;

import fr.smart_builders.component.Counter;
import fr.smart_builders.interfaces.CounterI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * 
 * <p>Created on : 2019-10-20</p>
 * 
 * @authors <p>Zaouzaou yacine , Erdogan Kevin</p>
 *
*/
public class 	CounterInboundPort 
extends 	AbstractInboundPort 
implements 	CounterI
{

	/**
	 * generated
	 */
	private static final long serialVersionUID = 2382605286652128251L;

	public CounterInboundPort(
			String uri,
			ComponentI owner
			) throws Exception 
	{
		super(uri , CounterI.class, owner);
		assert uri != null && owner instanceof Counter;
	}

	@Override
	public double total() throws Exception{
		return this.getOwner().handleRequestSync(
				owner -> ((Counter) owner).getTotal());
	}
	
	
	
	
	
	
	


}
