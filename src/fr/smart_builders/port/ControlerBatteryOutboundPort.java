package fr.smart_builders.port;

import fr.smart_builders.component.Controler;
import fr.smart_builders.interfaces.ControlerBatteryI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

//------------------------------------------------------------------------------------------

/**
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */
public class 				ControlerBatteryOutboundPort 
extends 				AbstractOutboundPort
implements 				ControlerBatteryI
{
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -588397422572889731L;

	public 				ControlerBatteryOutboundPort (
					String uri, 
					ComponentI owner
			) throws Exception
	{
		super (uri , ControlerBatteryI.class , owner);
		assert uri != null && owner instanceof Controler;
		
	}

	@Override
	public double immediateConsumption() throws Exception {
		return ((ControlerBatteryI) this.connector).immediateConsumption();
	}

	@Override
	public boolean charge(double value) throws Exception {
		return ((ControlerBatteryI) this.connector).charge(value);
	}

	@Override
	public boolean give(double value) throws Exception {
		return ((ControlerBatteryI) this.connector).give(value);
	}

	@Override
	public double level() throws Exception {
		return ((ControlerBatteryI) this.connector).level();
	}
	

}
