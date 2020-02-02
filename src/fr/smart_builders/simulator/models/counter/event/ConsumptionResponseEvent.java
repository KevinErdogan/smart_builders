package fr.smart_builders.simulator.models.counter.event;

import fr.smart_builders.simulator.models.counter.CounterModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//--------------------------------------------------------------------------------------------
/**
 * The class <code>ConsumptionResponseEvent</p> represent the response
 * of consumers for their consumption it contains the URI of the component 
 * (consumer) and its consumption (at the time t)
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 								ConsumptionResponseEvent 
extends										AbstractCounterEvent
{
	//-----------------------------------------------------------------------
	// inner class
	//-----------------------------------------------------------------------
	
	public static class 					ConsumptionReading 
	implements 								EventInformationI
	{

		/** generated */
		private static final long 			serialVersionUID = 8780117789974305320L;
		public final String 				modelUri ; 
		public final double 				consumption ;
		
		public ConsumptionReading (String uri , double c)
		{
			this.modelUri = uri ; 
			this.consumption = c ; 
		}
		
	}
	
	

	/** generated */
	private static final long 				serialVersionUID = 536074611468203741L;

	public 									ConsumptionResponseEvent(
										Time timeOfOccurrence, 
										String uri, 
										Double consumption)
	{
		super(timeOfOccurrence, 
				new ConsumptionReading(uri, consumption));
	}
	
	@Override
	public String 							eventAsString () 
	{
		return "Consumer::ConsumptionReading" ; 
	}
	
	@Override
	public boolean 							hasPriorityOver (EventI e)
	{
		return false ; 
	}
	
	@Override
	public void 							executeOn (AtomicModel model)
	{
		assert model instanceof CounterModel ; 
		ConsumptionReading cr = 
				(ConsumptionReading) this.getEventInformation() ; 
		((CounterModel)model).getConsumtion(cr.consumption);
	}

}
//------------------------------------------------------------------------------------------------




































