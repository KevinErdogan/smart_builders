package fr.smart_builders.simulator.models.events.tv;

import fr.smart_builders.simulator.models.tv.TvModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//------------------------------------------------------------------------------

/**
* The class <code>SetEcoModeTv</code> describes the event that the tv is 
* switched to eco mode, the consumption of the tv goes to its eco value when it is
* switched on 
* 
* @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
*/
public class 					SetEcoModeTv 
extends 						AbstractTvEvent
{

	/**
	 * 	generated
	 */
	private static final long serialVersionUID = 2964595956824221087L;

	public 						SetEcoModeTv(
							Time timeOfOccurrence, 
							EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}
	
	@Override
	public String 				eventAsString () 
	{
		return "Tv::SetEcoMode" ; 
	}

	
	@Override
	public boolean 				hasPriorityOver (EventI e)
	{
		if (e instanceof SetNormalModeTv) {
			return true ; 
		}
		return false ;
	}
	
	@Override
	public void 				executeOn (AtomicModel model) 
	{
		assert 	model instanceof TvModel ; 
		
		// TODO
	}
	
	
}
//---------------------------------------------------------------------------------------

















