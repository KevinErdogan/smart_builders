package fr.smart_builders.connectors;
//----------------------------------------------------------------------------------------

import fr.smart_builders.interfaces.BatteryI;
import fr.smart_builders.interfaces.ControlerBatteryI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

/**
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */
public class 				BatteryConnector 
extends 				AbstractConnector
implements 				ControlerBatteryI
{

	@Override
	public double immediateConsumption() throws Exception {
		return ((BatteryI) this.offering).immediateConsumption();
	}

	@Override
	public boolean charge(double value) throws Exception {
		return ((BatteryI) this.offering).charge(value);
	}

	@Override
	public boolean give(double value) throws Exception {
		return ((BatteryI) this.offering).give(value);
	}

	@Override
	public double level() throws Exception {
		return ((BatteryI) this.offering).level();
	}
	
}
