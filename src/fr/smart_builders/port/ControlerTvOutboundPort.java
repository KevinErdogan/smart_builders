package fr.smart_builders.port;

import fr.smart_builders.component.Controler;
import fr.smart_builders.interfaces.ControlerTvI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class 		ControlerTvOutboundPort 
extends 		AbstractOutboundPort 
implements 		ControlerTvI 

{
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -2623682554345235763L;
	
	public 		ControlerTvOutboundPort (
				String uri,
				ComponentI owner
			) throws Exception

	{
		super (uri , ControlerTvI.class , owner);
		assert uri != null && owner instanceof Controler;
	}
	@Override
	public double immediateConsumption() throws Exception {
		return ((ControlerTvI) this.connector).immediateConsumption();
	}

}
