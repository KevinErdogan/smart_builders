package fr.smart_builders.connectors;

import fr.smart_builders.interfaces.ControlerFridgeI;
import fr.smart_builders.interfaces.FridgeI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

//---------------------------------------------------------------------

/**
 * 
 * <p>Created on : 2019-10-19</P>
 * 
 * @authors <p>Zaouzaou Yacine , Erdogan Kevin</p>
 *
 */
public class 			FridgeConnector 
extends			AbstractConnector
implements 		ControlerFridgeI
{

	@Override
	public double immediateConsumption() throws Exception {
		return ((FridgeI) this.offering).immediateConsumption();
	}

	@Override
	public boolean switchOn() throws Exception {
		return ((FridgeI) this.offering).switchOn();
	}

	@Override
	public boolean switchOff() throws Exception {
		return ((FridgeI) this.offering).switchOff();
	}

}
