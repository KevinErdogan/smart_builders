package fr.smart_builders.simulator.models.events.oven;

import fr.smart_builders.simulator.models.oven.OvenModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//-------------------------------------------------------------------------------------


/**
 * The class <code>RunOven</code> describes the event that the oven is 
 * switched on, the consumption of the oven goes to its max value for a while 
 * 
 * @authors <p>Yacine Zaouzoau , Kevin Erdogan</p>
 */
public class 						RunOven 
extends 							AbstractOvenEvent
{
	
	public static class 			CountDown
	implements 						EventInformationI
	{

		/**
		 * 	generated
		 */
		private static final long 	serialVersionUID = -1629900706224599726L;
		public final double 		value ;
		public final double 		temperature ;
		
		public CountDown (double value , double t) {
			super () ; 
			this.value = value ; 
			this.temperature = t ; 
		}
	}
	

	/**
	 *  generated
	 */
	private static final long serialVersionUID = 3703944314322697534L;

	public 							RunOven(
					Time timeOfOccurrence, 
					double countdown, 
					double temperature) {
		super(timeOfOccurrence, new CountDown(countdown , temperature));
	}
	
	@Override
	public String	 				eventAsString () 
	{
		return "Oven::Run" ; 
	}
	
	@Override
	public boolean 					hasPriorityOver (EventI e) 
	{
		if (e instanceof ScheduleRunOven) {
			return true ;
		}
		return false ;
	}
	
	@Override
	public void 					executeOn (AtomicModel model) 
	{
		assert 	model instanceof OvenModel ; 
		
		CountDown cd = ((CountDown) this.getEventInformation()); 
		
		((OvenModel) model).run ( cd.value , cd.temperature ) ; 
	}

}
//-------------------------------------------------------------------------------------










