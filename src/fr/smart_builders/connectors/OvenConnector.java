package fr.smart_builders.connectors;

import java.util.Calendar;

import fr.smart_builders.interfaces.ControlerOvenI;
import fr.smart_builders.interfaces.OvenI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

//-------------------------------------------------------------------------------------

/**
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */

public class 					OvenConnector 
extends 					AbstractConnector
implements 					ControlerOvenI
{

	@Override
	public boolean switchOn() throws Exception {
		return ((OvenI) this.offering).switchOn();
	}	

	@Override
	public boolean switchOff() throws Exception {
		return ((OvenI) this.offering).switchOff();
	}

	@Override
	public double immediateConsumption() throws Exception {
		return ((OvenI) this.offering).immediateConsumption();
	}

	@Override
	public boolean switchToEcoMode() throws Exception {
		return ((OvenI) this.offering).switchToEcoMode();
	}

	@Override
	public boolean switchToNormalMode() throws Exception {
		return ((OvenI) this.offering).switchToNormalMode();
	}

	@Override
	public boolean setStartTime(Calendar d) throws Exception {
		return ((OvenI) this.offering).setStartTime(d);
	}
	
	

}
