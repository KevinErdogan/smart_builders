package fr.smart_builders.interfaces;

public interface 				ControlerFridgeI 
		extends 				SwitcherI, 
								MonitorI 
{
	/**
	 * switch the fridge to eco mode
	 * 
	 * @return true if the fridge is turned to eco mode successfully, false else
	 * @throws Exception
	 */
	public boolean ecoModeOn ()		throws Exception;

	
	/**
	 * switch the fridge to normal mode
	 * 
	 * @return true if the fridge is turned to normal mode successfully, false else
	 * @throws Exception
	 */

	public boolean ecoModeOff ()	throws Exception;


}
