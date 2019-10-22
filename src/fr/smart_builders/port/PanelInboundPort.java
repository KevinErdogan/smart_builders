package fr.smart_builders.port;

import fr.smart_builders.component.SolarPanel;
import fr.smart_builders.interfaces.SolarPanelI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

//------------------------------------------------------------------------

/**
 * 
 * <p>Created on : 2019-10-20</p>
 * 
 * @authors <p>Zaouzaou yacine , Erdogan Kevin</p>
 *
 */
public class 		PanelInboundPort 
extends 		AbstractInboundPort 
implements 		SolarPanelI 
{
	
	

	/**
	 * generated
	 */
	private static final long serialVersionUID = 8967585980005296890L;


	public 		PanelInboundPort(
			String uri,
			ComponentI owner
			)throws Exception
	{
		super (uri , SolarPanelI.class , owner);
		assert uri != null && owner instanceof SolarPanel;
	}
	
	
	@Override
	public double generation() throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((SolarPanel) owner).energy());
	}


	
	
	/**
	 * produced energy is negatif of consumtion
	 */
	@Override
	public double immediateConsumption() throws Exception {
		return this.generation() * -1;
	}

}
