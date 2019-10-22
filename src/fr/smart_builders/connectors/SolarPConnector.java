package fr.smart_builders.connectors;

import fr.smart_builders.interfaces.EnergyProviderI;
import fr.smart_builders.interfaces.SolarPanelI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

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
