package fr.smart_builders.port;

import java.util.Calendar;

import fr.smart_builders.component.Controler;
import fr.smart_builders.interfaces.ControlerOwenI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

//----------------------------------------------------------------------------------------

/**
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */

public class 			ControlerOwenOutboundPort 
extends 			AbstractOutboundPort
implements 			ControlerOwenI
{
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -4297342308829805530L;

	public 				ControlerOwenOutboundPort (
				String uri,
				ComponentI owner
			)throws Exception
	{
		super (uri , ControlerOwenI.class , owner);
		assert uri != null && owner instanceof Controler;
	}

	@Override
	public boolean switchOn() throws Exception {
		return ((ControlerOwenI) this.connector).switchOn();
	}

	@Override
	public boolean switchOff() throws Exception {
		return ((ControlerOwenI) this.connector).switchOff();
	}

	@Override
	public double immediateConsumption() throws Exception {
		return ((ControlerOwenI) this.connector).immediateConsumption();
	}

	@Override
	public boolean switchToEcoMode() throws Exception {
		return ((ControlerOwenI) this.connector).switchToEcoMode();
	}

	@Override
	public boolean switchToNormalMode() throws Exception {
		return ((ControlerOwenI) this.connector).switchToNormalMode();
	}

	@Override
	public boolean setStartTime(Calendar c) throws Exception {
		return ((ControlerOwenI) this.connector).setStartTime(c);
	}

}
