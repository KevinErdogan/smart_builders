package fr.smart_builders.interfaces;

import java.util.Calendar;

//------------------------------------------------------------------------------

/**
 * The interface <code>OwenI</code> defines the services offered by a smart oven
 * @author <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */
public interface 		OvenI 
extends 				SwitcherI, 
						MonitorI
{
	
	
	/**
	 * switch the oven to mode eco, the oven will consume less
	 * and get lower temperature
	 * @return
	 * @throws Exception
	 */
	public boolean switchToEcoMode () 					throws Exception;
	
	/**
	 * Switch the oven to normal mode, the oven will consume its
	 * nominal or maximum consumption and can hit its max temperature
	 * @return
	 * @throws Exception
	 */
	public boolean switchToNormalMode () 				throws Exception;
	
	/**
	 * Can schedule the oven to start at a certain time
	 * can make better for the parameter it takes 
	 * for now we use Calendar from java.util
	 * @param starttime
	 * @return
	 * @throws Exception
	 */
	public boolean setStartTime (Calendar starttime)	throws Exception;
	
}
