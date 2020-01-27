package fr.smart_builders.simulator.models.events.tv;

import fr.smart_builders.simulator.models.tv.TvModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//-------------------------------------------------------------------------------------------
/**
 * The class <code>SetNormalModeTv</code> describes the event that 
 * the tv is switched to normal mode, the consumption of the tv goes 
 * to its max value when it is switched on 
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p> 
 */
public class 						SetNormalModeTv 
extends 							AbstractTvEvent
{

	/**
	 * 	generated
	 */
	private static final long 		serialVersionUID = -3653435505873018245L;

	public 							SetNormalModeTv(
								Time timeOfOccurrence, 
								EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}
	
	@Override
	public String 				eventAsString () 
	{
		return "Tv::SetNormalMode" ; 
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
		// TODO
	}
	
}
//---------------------------------------------------------------------------------------------


























