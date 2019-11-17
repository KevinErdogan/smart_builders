package fr.smart_builders.port;

import java.util.Calendar;

import fr.smart_builders.component.Owen;
import fr.smart_builders.interfaces.OwenI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

//--------------------------------------------------------------------------------------

/**
 * 
 * 
 * @authors <p>Yacine Zaouzaou , kevin Erdogan</p>
 *
 */

public class 		OwenInboundPort 
extends 			AbstractInboundPort
implements 			OwenI
{
	
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 3358843426525177798L;

	public 				OwenInboundPort(
				String uri, 
				ComponentI owner
			
			)throws Exception
	{
		super (uri, OwenI.class , owner);
		assert uri != null && owner instanceof Owen;
	}

	@Override
	public boolean switchOn() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Owen) owner).switchOn())	;
	}

	@Override
	public boolean switchOff() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Owen) owner).switchOff());
	}

	@Override
	public double immediateConsumption() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Owen) owner).getConsumption());
	}

	@Override
	public boolean switchToEcoMode() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Owen) owner).switchToEcoMode());
	}

	@Override
	public boolean switchToNormalMode() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Owen) owner).switchToNormalMode());
	}

	@Override
	public boolean setStartTime(Calendar starttime) throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Owen) owner).startAt(starttime));
	}
}
