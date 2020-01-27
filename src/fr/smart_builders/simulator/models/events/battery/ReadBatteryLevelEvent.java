package fr.smart_builders.simulator.models.events.battery;

import fr.smart_builders.simulator.models.battery.BatteryModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//------------------------------------------------------------------------------------------------------

/**
 * The class <code>ReadBatteryLevel</code> describes the event that an external model 
 * is asking for the battery level
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 								ReadBatteryLevelEvent 
extends 									AbstractBatteryEvent
{

	/**	generated */
	private static final long 				serialVersionUID = -759250724074646382L;

	public 									ReadBatteryLevelEvent(
										Time timeOfOccurrence, 
										EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}
	
	@Override
	public String 							eventAsString () 
	{
		return "Battery::askForValue" ; 
	}
	
	@Override
	public boolean 							hasPriorityOver ( EventI e) 
	{
		return false ; 
	}

	@Override
	public void 							executeOn (AtomicModel model) 
	{
		assert model instanceof BatteryModel ; 
		
		((BatteryModel) model).readLevel () ;
	}
}
//--------------------------------------------------------------------------------------------------------

























