package fr.smart_builders.system.ports;

import fr.smart_builders.system.interfaces.CounterI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.smart_builders.system.components.Counter;

public class CounterInboundPort extends AbstractInboundPort implements CounterI{

	private static final long serialVersionUID = 3619682909472095921L;

	public CounterInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, CounterI.class, owner);
	}

	@Override
	public boolean register(String uriPort) throws Exception {
		return getOwner().handleRequestSync(owner -> ((Counter)owner).register(uriPort));
	}

	@Override
	public boolean unregister(String uriPort) throws Exception {
		return getOwner().handleRequestSync(owner -> ((Counter)owner).unregister(uriPort));
	}

	@Override
	public double totalConsumption() throws Exception {
		return getOwner().handleRequestSync(owner -> ((Counter)owner).totalConsumption());
	}
	
}
