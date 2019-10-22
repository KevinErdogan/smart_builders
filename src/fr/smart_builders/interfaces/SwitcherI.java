package fr.sorbonne_u.smartgrid.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;

//-------------------------------------------------------------------------------
/**
 * The interface <code>Switcher</code> defines the service of turning on or off
 * an electric consumer like a refrigerator or a heater.
 * 
 * 
 * <p>Created on : 2019-10-08
 * 
 * @authors <p> Yacine Zaouzaou, Kevin Erdogan </p>
 */


public interface 			SwitcherI 
extends 	OfferedI
{
	/**
	 * allow the controller to switch on the device 
	 * 
	 * 
	 * 
	 * @return					True if the device was switched on, false else.
	 * @throws Exception
	 */
	
	public boolean		switchOn () throws Exception;
	
	/**
	 * allow the controller to switch on the device
	 * 
	 * 
	 * 
	 * @return					True if the device was switched off, false else.
	 * @throws Exception
	 */
	
	public boolean 		switchOff () throws Exception;
	
}
