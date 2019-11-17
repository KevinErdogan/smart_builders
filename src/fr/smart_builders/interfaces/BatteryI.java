package fr.smart_builders.interfaces;

//----------------------------------------------------------------------------------------------

/**
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */

public interface 		BatteryI 
extends 			MonitorI
{
	public boolean charge (double value) 			throws Exception;
	
	public boolean give (double value) 				throws Exception;
	
	public double level () 							throws Exception;
}
