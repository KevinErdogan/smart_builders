package fr.smart_builders.simulator.models.events.tv;

import fr.smart_builders.simulator.models.tv.TvModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//------------------------------------------------------------------------------

/**
 * The class <code>SwitchOnTv</code> describes the event that the tv is 
 * switched on, the consumption of the tv goes to its max value
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 					SwitchOnTv 
extends 						AbstractTvEvent
{

	/**
	 * 	generated
	 */
	private static final long serialVersionUID = -3784050185737006670L;

	public 						SwitchOnTv(
							Time timeOfOccurrence, 
							EventInformationI content) {
		super(timeOfOccurrence, content);
	}
	
	@Override
	public String 				eventAsString () 
	{
		return "Tv::SwitchOn" ;
	}
	
	@Override
	public boolean 				hasPriorityOver (EventI e)
	{
		return false ; 
	}
	
	@Override
	public void 				executeOn (AtomicModel model)
	{
		assert 	model instanceof TvModel ; 
		
		((TvModel) model).switchOn () ; 
	}

	
}
