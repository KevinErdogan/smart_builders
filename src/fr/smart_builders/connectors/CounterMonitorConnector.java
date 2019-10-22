package fr.smart_builders.connectors;

import fr.smart_builders.interfaces.MonitorI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

public class 		CounterMonitorConnector 
extends 		AbstractConnector
implements 		MonitorI
{

	@Override
	public double immediateConsumption() throws Exception {
		return ((MonitorI) this.offering).immediateConsumption();
	}
	

}
