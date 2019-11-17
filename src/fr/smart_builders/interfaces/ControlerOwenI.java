package fr.smart_builders.interfaces;

import java.util.Calendar;

//-------------------------------------------------------------------------------------

/**
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */

public interface 			ControlerOwenI 
extends					SwitcherI, 
						MonitorI

{
		
	public boolean switchToEcoMode () 			throws Exception;
	public boolean switchToNormalMode () 		throws Exception;
	public boolean setStartTime (Calendar d)	throws Exception;

}
