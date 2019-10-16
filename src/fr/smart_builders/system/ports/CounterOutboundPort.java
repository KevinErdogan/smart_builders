package fr.smart_builders.system.ports;

import fr.smart_builders.system.interfaces.ConsummerI;
import fr.smart_builders.system.interfaces.CounterI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class CounterOutboundPort extends AbstractOutboundPort implements ConsummerI {

	private static final long serialVersionUID = -4287290911239105236L;

	public CounterOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, CounterI.class, owner);
	}

	@Override
	public double consumption() throws Exception {
		return ((ConsummerI)this.connector).consumption();
	}

}
