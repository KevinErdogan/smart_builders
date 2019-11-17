package fr.smart_builders.interfaces;
//------------------------------------------------------------------------------------------

/**
 * 
 * 
 * 
 * 
 * @authors <p>Zaouzaou Yacine, Erdogan Kevin</p>
 *
 */
public interface FridgeI 
extends MonitorI,
		SwitcherI
{

	public boolean ecoModeOn ()		throws Exception;
	public boolean ecoModeOff ()	throws Exception;
	
}
