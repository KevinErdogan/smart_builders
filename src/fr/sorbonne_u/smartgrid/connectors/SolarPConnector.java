package fr.sorbonne_u.smartgrid.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.smartgrid.interfaces.EnergyProviderI;
import fr.sorbonne_u.smartgrid.interfaces.SolarPanelI;

public class 		SolarPConnector 
extends 		AbstractConnector 
implements 		EnergyProviderI 
{

	
	/**
	 * other controls will come in the future
	 * this is only a monitoring service 
	 */
	
	@Override
	public double egergyProduiced() throws Exception {
		return ((SolarPanelI) this.offering).generation();
	}

}
