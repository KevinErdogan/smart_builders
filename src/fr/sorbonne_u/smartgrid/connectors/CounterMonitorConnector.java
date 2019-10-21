package fr.sorbonne_u.smartgrid.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.smartgrid.interfaces.MonitorI;

public class 		CounterMonitorConnector 
extends 		AbstractConnector
implements 		MonitorI
{

	@Override
	public double immediateConsumption() throws Exception {
		return ((MonitorI) this.offering).immediateConsumption();
	}
	

}
