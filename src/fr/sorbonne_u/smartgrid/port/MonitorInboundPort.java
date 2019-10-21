package fr.sorbonne_u.smartgrid.port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.smartgrid.coponenet.Fridge;
import fr.sorbonne_u.smartgrid.coponenet.SolarPanel;
import fr.sorbonne_u.smartgrid.coponenet.Tv;
import fr.sorbonne_u.smartgrid.interfaces.MonitorI;

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
				|| owner instanceof SolarPanel);
	}
	

	@Override
	public double immediateConsumption() throws Exception {
		if (this.owner instanceof Fridge) {
			return consumptionFridge ();
		}else if (this.owner instanceof Tv) {
			return consumptionTv ();
		}
		return 0;
	}
	
	private double consumptionFridge () throws Exception{
		return this.getOwner().handleRequestSync(
				owner -> ((Fridge) owner).getConsumption());
	}
	
	private double consumptionTv () throws Exception {
		return this.getOwner().handleRequestSync(
				owner -> ((Tv) owner).getConsumption());
	
	}
	

}
