package fr.smart_builders.connectors;

import fr.smart_builders.interfaces.ControlerTvI;
import fr.smart_builders.interfaces.TvI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

//---------------------------------------------------------------------

/**
 * 
 * <p>Created on : 2019-10-19</p>
 * 
 * @authors <p>Zaouzaou Yacine , Erdogan Kevin</p>
 *
 */
public class 		TvConnector 
extends 		AbstractConnector
implements 		ControlerTvI
{

	@Override
	public double immediateConsumption() throws Exception {
		return ((TvI) this.offering).immediateConsumption();
	}

}
