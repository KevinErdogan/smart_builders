package fr.smart_builders.simulator.models.events.battery;

import fr.smart_builders.simulator.models.battery.BatteryModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//--------------------------------------------------------------------------------------------------------
/**
* The class <code>DischargeBatteryEvent</code> describes the event that the 
* battery is being supplied by the system 
* 
* @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
*/
public class 								DischargeBatteryEvent 
extends 									AbstractBatteryEvent
{

	public static class 					DischargeBatteryInformation
	implements 								EventInformationI
	{

		/** generated */
		private static final long 			serialVersionUID = 555793393572852458L;
		public final double 				power ; 
		public 								DischargeBatteryInformation (double p)
		{
			this.power = p ;
		}
		
	}
	
	/** generated */
	private static final long serialVersionUID = -5578471047152624364L;

	public 									DischargeBatteryEvent(
										Time timeOfOccurrence, 
										double power) 
	{
		super(timeOfOccurrence, new DischargeBatteryInformation(power));
	}
	
	@Override
	public String 							eventAsString ()
	{
		return "Battery::Discharge" ; 
	}
	
	@Override
	public boolean 							hasPriorityOver (EventI e)
	{
		return false ;
	}
	
	@Override
	public void 							executeOn (AtomicModel model) 
	{
		assert model instanceof BatteryModel ; 
		
		DischargeBatteryInformation dbi = (DischargeBatteryInformation) this.getEventInformation() ; 
		
		((BatteryModel) model).receiveDischarge(dbi.power) ; 
	}

}
//--------------------------------------------------------------------------------------------------------



































