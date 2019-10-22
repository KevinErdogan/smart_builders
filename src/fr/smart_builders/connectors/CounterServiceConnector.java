package fr.sorbonne_u.smartgrid.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.smartgrid.interfaces.CounterI;
import fr.sorbonne_u.smartgrid.interfaces.ControlerCounterI;

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
