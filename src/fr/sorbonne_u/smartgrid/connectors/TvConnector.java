package fr.sorbonne_u.smartgrid.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.smartgrid.interfaces.ControlerTvI;
import fr.sorbonne_u.smartgrid.interfaces.TvI;

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
