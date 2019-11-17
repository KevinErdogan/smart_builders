package fr.smart_builders.connectors;

import java.util.Calendar;

import fr.smart_builders.interfaces.ControlerOwenI;
import fr.smart_builders.interfaces.OwenI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

//-------------------------------------------------------------------------------------

/**
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */

public class 					OwenConnector 
extends 					AbstractConnector
implements 					ControlerOwenI
{

	@Override
	public boolean switchOn() throws Exception {
		return ((OwenI) this.offering).switchOn();
	}	

	@Override
	public boolean switchOff() throws Exception {
		return ((OwenI) this.offering).switchOff();
	}

	@Override
	public double immediateConsumption() throws Exception {
		return ((OwenI) this.offering).immediateConsumption();
	}

	@Override
	public boolean switchToEcoMode() throws Exception {
		return ((OwenI) this.offering).switchToEcoMode();
	}

	@Override
	public boolean switchToNormalMode() throws Exception {
		return ((OwenI) this.offering).switchToNormalMode();
	}

	@Override
	public boolean setStartTime(Calendar d) throws Exception {
		return ((OwenI) this.offering).setStartTime(d);
	}
	
	

}
