package fr.smart_builders.port;

import fr.smart_builders.component.Battery;
import fr.smart_builders.component.Fridge;
import fr.smart_builders.component.Owen;
import fr.smart_builders.component.SolarPanel;
import fr.smart_builders.component.Tv;
import fr.smart_builders.interfaces.MonitorI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

//------------------------------------------------------------------------


/**
 * 
 * <p>Created on : 2019-10-20</p>
 * 
 * @authors <p>Zaouzaou Yacine , Erdogan Kevin</p>
 *
 */
public class 			MonitorInboundPort 
extends 		AbstractInboundPort 
implements 		MonitorI 
{
	
	
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -5001059254433713974L;

	public 				MonitorInboundPort (
			String uri, 
			ComponentI owner
			)throws Exception 
	{
		super (uri , MonitorI.class, owner);
		assert uri != null;
		assert (owner instanceof Fridge
				|| owner instanceof Tv
				|| owner instanceof SolarPanel
				|| owner instanceof Battery
				|| owner instanceof Owen);
	}
	

	@Override
	public double immediateConsumption() throws Exception {
		if (this.owner instanceof Fridge) {
			return consumptionFridge ();
		}else if (this.owner instanceof Tv) {
			return consumptionTv ();
		}else if (this.owner instanceof Battery) {
			return consumptionBattery();
		}else if (this.owner instanceof Owen) {
			return consumptionOwen ();
		}
		return 0;
	}
	
	private double consumptionFridge () throws Exception{
		return this.getOwner().handleRequestSync(
				owner -> ((Fridge) owner).getConsumption());
	}
	
	private double consumptionBattery () throws Exception{
		return this.getOwner().handleRequestSync(
				owner -> ((Battery) owner).consumption());
	}
	
	private double consumptionOwen () throws Exception{
		return this.getOwner().handleRequestSync(
				owner -> ((Owen) owner).getConsumption());
	}
	
	private double consumptionTv () throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Tv) owner).getConsumption());
	
	}
	

}
