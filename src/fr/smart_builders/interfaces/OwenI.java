package fr.smart_builders.interfaces;

import java.util.Calendar;

//------------------------------------------------------------------------------

/**
 * The interface <code>OwenI</code> defines the services offered by a smart owen
 * @author <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */
public interface 		OwenI 
extends 				SwitcherI, 
						MonitorI
{
	
	public boolean switchToEcoMode () 					throws Exception;
	public boolean switchToNormalMode () 				throws Exception;
	public boolean setStartTime (Calendar starttime)	throws Exception;
	
}
