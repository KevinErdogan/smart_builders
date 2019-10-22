package fr.smart_builders.connectors;

import fr.smart_builders.interfaces.ControlerCounterI;
import fr.smart_builders.interfaces.CounterI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

//---------------------------------------------------------------------


/**
 * 
 * <p>Created on : 2019-10-20</p>
 * 
 * @authors <p>Zaouzaou yacine , Erdogan Kevin</p>
 *
 */
public class 			CounterServiceConnector 
extends 		AbstractConnector 
implements 		ControlerCounterI
{
	
	@Override
	public double immediateConsumption() throws Exception {
		return ((CounterI) this.offering).total();
	}

}
