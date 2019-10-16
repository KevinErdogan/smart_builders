package fr.smart_builders.system.ports;

import fr.smart_builders.system.components.Fridge;
import fr.smart_builders.system.interfaces.FridgeI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class FridgeInboundPort extends AbstractInboundPort implements FridgeI{
	
	private static final long serialVersionUID = 4654747360121748768L;

	public FridgeInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, FridgeI.class, owner);
		
		
	}

	@Override
	public boolean switchOn() throws Exception {
		return getOwner().handleRequestSync(owner -> ((Fridge)owner).authorize());
	}

	@Override
	public boolean switchOff() throws Exception {
		return getOwner().handleRequestSync(owner -> ((Fridge)owner).notAuthorize());
	}

	@Override
	public double fridgeTemp() throws Exception {
		return getOwner().handleRequestSync(owner -> ((Fridge)owner).fridgeTemp());
	}

	@Override
	public double consumption() throws Exception {
		return getOwner().handleRequestSync(owner -> ((Fridge)owner).consumption());
	}
}
