package fr.smart_builders.port;

import fr.smart_builders.component.Controler;
import fr.smart_builders.interfaces.ControlerCounterI;
import fr.smart_builders.interfaces.MonitorI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class 			ControlerCounterOutboundPort 
extends 		AbstractOutboundPort 
implements 		ControlerCounterI 
{

	/**
	 * generated
	 */
	private static final long serialVersionUID = 3198101426990683179L;

	public			ControlerCounterOutboundPort (
			String uri,
			ComponentI owner
			)	throws Exception
	{
		super (uri , MonitorI.class , owner);
		assert uri != null && owner instanceof Controler;
	}
	
	@Override
	public double immediateConsumption() throws Exception {
		return ((ControlerCounterI) this.connector).immediateConsumption();
	}

}
