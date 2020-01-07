package fr.smart_builders.interfaces;
//------------------------------------------------------------------------------------------

/**
 * The interface <code>FridgeI</code> defines the services given by a smart fridge 
 * it extends <code>MonitorI</code> and <code>SwitcherI</code>
 * 
 * @authors <p>Zaouzaou Yacine, Erdogan Kevin</p>
 *
 */
public interface FridgeI 
extends MonitorI,
		SwitcherI
{

	
	/**
	 * Switch the fridge to eco mode it will consume less but 
	 * decreases the temperature slower.
	 * @return
	 * @throws Exception
	 */
	public boolean ecoModeOn ()		throws Exception;
	
	/**
	 * Switch the fridge to normal mode it will consume it nominal 
	 * consumption the temperature will decrease faster.
	 * @return
	 * @throws Exception
	 */
	public boolean ecoModeOff ()	throws Exception;
	
}
