package fr.sorbonne_u.smartgrid.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;

//-------------------------------------------------------------------

/**
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
	
	public double generation () throws Exception;

}
