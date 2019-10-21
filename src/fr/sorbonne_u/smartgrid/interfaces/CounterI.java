package fr.sorbonne_u.smartgrid.interfaces;
import fr.sorbonne_u.components.interfaces.OfferedI;
//-------------------------------------------------------------------

/**
 * 
 * <p>Created on : 2019-10-20</p>
 * 
 * @authors <p>Zaouzaou yacine , Erdogan Kevin</p>
 *
 */
public interface 	CounterI 
extends 		OfferedI
{
	public double total ()	throws Exception;
}
