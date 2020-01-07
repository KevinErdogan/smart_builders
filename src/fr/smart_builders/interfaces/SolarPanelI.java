package fr.smart_builders.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;

//-------------------------------------------------------------------

/**
 * 
 * The interface <code> SolarPanel </code> defines the services offered by a 
 * solar panel.
 * 
 * <p>Created on : 2019-10-20</p>
 * 
 * @authors <p>Zaouzaou Yacine , Erdogan Kevin</p>
 *
 */
public interface 	SolarPanelI 
extends 			OfferedI,
					MonitorI
{
	/**
	 * give the value of energy generated
	 * @return
	 * @throws Exception
	 */
	public double generation () throws Exception;

}
