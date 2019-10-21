package fr.smart_builders.system.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface ConsummerI extends OfferedI, RequiredI{
	public double consumption() throws Exception;
}
