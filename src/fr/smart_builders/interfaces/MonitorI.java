package fr.smart_builders.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

//-------------------------------------------------------------------------------
/**
 * The interface <code>MonitorI</code> defines the services of showing the intern
 * state of devices, for example power consumption.
 * it also the required interface by components that needs to get
 * the consumption of other devices
 * 
 * <p>Created on : 2019-10-08</p>
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan  </p>
 *
 */
public interface 			MonitorI 
extends 	OfferedI,
			RequiredI
{
	
	/**
	 * 
	 * get the value of the amount of power that the device is consuming when
	 * the method is called.
	 * 
	 * 
	 * @return		the value of the immediate consumption of the device
	 * @throws Exception
	 */

	public double 				immediateConsumption () throws Exception;		
	
}
