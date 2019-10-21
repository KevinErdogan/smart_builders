package fr.sorbonne_u.smartgrid.port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import fr.sorbonne_u.smartgrid.coponenet.Controler;
import fr.sorbonne_u.smartgrid.interfaces.ControlerCounterI;
import fr.sorbonne_u.smartgrid.interfaces.MonitorI;

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
