package fr.smart_builders.system.components;

import java.util.ArrayList;

import fr.smart_builders.system.interfaces.ConsummerI;
import fr.smart_builders.system.interfaces.CounterI;
import fr.smart_builders.system.ports.CounterOutboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;

@OfferedInterfaces(offered= {CounterI.class})
@RequiredInterfaces(required= {ConsummerI.class})
public class Counter extends AbstractComponent{
	ArrayList<CounterOutboundPort> consummers = new ArrayList<CounterOutboundPort>();

	protected Counter(String uri, String inboundPortURI) throws Exception {
		super(uri, 1, 0);
	}
	
	public boolean register(String uriPort) throws Exception{
		return consummers.add(new CounterOutboundPort(uriPort, this));
	}
	public boolean unregister(String uriPort) throws Exception{
		CounterOutboundPort toRemove=null;
		for(CounterOutboundPort c : consummers) {
			if(c.getPortURI()==uriPort) {
				toRemove = c;
				break;
			}
		}
		return consummers.remove(toRemove);
	}
	public double totalConsumption() throws Exception{
		double cpt = 0;
		for(CounterOutboundPort port : consummers) {
			cpt+=port.consumption();
		}
		return cpt;
	}

}
