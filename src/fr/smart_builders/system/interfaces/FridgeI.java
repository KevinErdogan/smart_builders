package fr.smart_builders.system.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
/* Commenter */
public interface FridgeI extends OfferedI, ConsummerI{
	public boolean switchOn() throws Exception;
	public boolean switchOff() throws Exception;
	public double fridgeTemp() throws Exception;
}
