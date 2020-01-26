package fr.smart_builders.simulator.models.events.oven;

import fr.smart_builders.simulator.models.oven.OvenModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//-------------------------------------------------------------------------------------


/**
 * The class <code>ScheduleRunOven</code> describes the event that the oven is 
 * scheduled to start at a certain time t for a certain duration d
 * at time t (in the simulation) the oven will start (automatically) its consumption
 * goes to its max value for a while and then bend down to its cruise consumption
 * after a duration of value d the oven will be switch off and it consumption became 0 
 * 
 * @authors <p>Yacine Zaouzaou, Kevin Erdogan</p>
 */
public class 					ScheduleRunOven 
extends 						AbstractOvenEvent
{
	
	
	public static class			ScheduleOvenInformation
	implements					EventInformationI
	{

		/**
		 * generated
		 */
		private static final long serialVersionUID = 8208293934729607054L;
		public final double 			duration ; 
		public final double 			temperature ; 
		public final Time 				startTime ;
		
		public 					ScheduleOvenInformation (
							double 	d ,
							double t  ,
							Time 	st)
		
		{
			super () ;
			this.duration = d;
			this.startTime = st ; 
			this.temperature = t ; 
		}
		
	}
	

	/**
	 * 	generated
	 */
	private static final long serialVersionUID = 8808566802979938005L;

	public 						ScheduleRunOven(
							Time timeOfOccurrence, 
							double duration, 
							double temperature , 
							Time startTime) {
		super(timeOfOccurrence, new ScheduleOvenInformation(
										duration,
										temperature,
										startTime));
	}
	
	@Override
	public String 				eventAsString () 
	{
		return "Oven::Schedule" ; 
	}
	
	
	@Override
	public boolean 				hasPriorityOver (EventI e)
	{
		return false ;
	}
	
	@Override
	public void 				executeOn (AtomicModel model) 
	{
		assert model 	instanceof OvenModel ; 
		ScheduleOvenInformation soi = 
				(ScheduleOvenInformation) this.getEventInformation() ; 
		((OvenModel) model).schedule (
								soi.startTime , 
								soi.temperature ,
								soi.duration) ;
	}
	
	
}
//-------------------------------------------------------------------------------------











