package fr.smart_builders.interfaces;

import fr.sorbonne_u.components.interfaces.RequiredI;


/**
 * cette interface est du côté du controleur
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */

public interface 		EnergyProviderI 
extends 		RequiredI 

{
	public double egergyProduiced () throws Exception;
}
