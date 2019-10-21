package fr.smart_builders.system.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface CounterI extends OfferedI, RequiredI {
	public boolean register(String uri) throws Exception;
	public boolean unregister(String uri) throws Exception;
	public double totalConsumption() throws Exception;
}
