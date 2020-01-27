package fr.smart_builders.simulator.models.events.battery;

import fr.smart_builders.simulator.models.battery.BatteryModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//--------------------------------------------------------------------------------------------------------
/**
 * The class <code>ChargeBatteryEvent</code> describes the event that the 
 * battery is being charged by the system 
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 								ChargeBatteryEvent 
extends 									AbstractBatteryEvent
{
	
	public static class						ChargingBatteryInformation 
	implements 								EventInformationI
	{

		/** generated */
		private static final long 			serialVersionUID = 908373208390540150L;
		public final double 				power ;
		
		public ChargingBatteryInformation (double p) {
			this.power = p ;
		}
	}
	

	/** generated */
	private static final long serialVersionUID = 8194540227576858374L;

	public 									ChargeBatteryEvent(
										Time timeOfOccurrence, 
										double power) 
	{
		super(timeOfOccurrence, new ChargingBatteryInformation(power));
	}
	
	@Override
	public String 							eventAsString ()
	{
		return "Battery::Charge" ; 
	}
	
	@Override
	public boolean 							hasPriorityOver (EventI e)
	{
		return false ; 
	}
	
	@Override
	public void 							executeOn (AtomicModel model) 
	{
		assert 	model instanceof BatteryModel ; 
		
		ChargingBatteryInformation cbi = (ChargingBatteryInformation) this.getEventInformation() ;  
		
		((BatteryModel) model).receiveCharge(cbi.power);
	}

}
//-------------------------------------------------------------------------------------------------------











































