package fr.smart_builders.port;

import java.util.Calendar;

import fr.smart_builders.component.Oven;
import fr.smart_builders.interfaces.OvenI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

//--------------------------------------------------------------------------------------

/**
 * 
 * 
 * @authors <p>Yacine Zaouzaou , kevin Erdogan</p>
 *
 */

public class 		OvenInboundPort 
extends 			AbstractInboundPort
implements 			OvenI
{
	
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 3358843426525177798L;

	public 				OvenInboundPort(
				String uri, 
				ComponentI owner
			
			)throws Exception
	{
		super (uri, OvenI.class , owner);
		assert uri != null && owner instanceof Oven;
	}

	@Override
	public boolean switchOn() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Oven) owner).switchOn())	;
	}

	@Override
	public boolean switchOff() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Oven) owner).switchOff());
	}

	@Override
	public double immediateConsumption() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Oven) owner).getConsumption());
	}

	@Override
	public boolean switchToEcoMode() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Oven) owner).switchToEcoMode());
	}

	@Override
	public boolean switchToNormalMode() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Oven) owner).switchToNormalMode());
	}

	@Override
	public boolean setStartTime(Calendar starttime) throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Oven) owner).startAt(starttime));
	}
}
