package fr.smart_builders.port;

import fr.smart_builders.component.Counter;
import fr.smart_builders.interfaces.MonitorI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class 		MonitorOutboundPort 
extends 		AbstractOutboundPort
implements 		MonitorI
{
	
	
	
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -7965114342343988938L;

	public 		MonitorOutboundPort  (
			String uri, 
			ComponentI owner
			) throws Exception
	{
		super (uri , MonitorI.class , owner);
		assert uri != null;
		assert owner instanceof Counter;
	}

	@Override
	public double immediateConsumption() throws Exception {
		return ((MonitorI) this.connector).immediateConsumption();
	}
	
	
	
	

}
