package fr.smart_builders.port;

import fr.smart_builders.component.Battery;
import fr.smart_builders.interfaces.BatteryI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

//-----------------------------------------------------------------------------------------
/**
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */

public class 			BatteryInboundPort 
extends 			AbstractInboundPort
implements 			BatteryI
{

	
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -1476677021022725292L;

	public 				BatteryInboundPort (
				String uri, 
				ComponentI owner			
			) throws Exception 
	{
		super (uri , BatteryI.class , owner);
		assert uri != null && owner instanceof Battery;
	}

	@Override
	public double immediateConsumption() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Battery) owner).consumption());
	}

	@Override
	public boolean charge(double value) throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Battery) owner).charge(value));
	}

	@Override
	public boolean give(double value) throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Battery) owner).discharge(value));
	}

	@Override
	public double level() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Battery) owner).level());
	}
	
	
	
}
