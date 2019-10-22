package fr.smart_builders.port;

import fr.smart_builders.component.Controler;
import fr.smart_builders.interfaces.EnergyProviderI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

//-----------------------------------------------------------------------


/**
 * 
 * <p>Created on : 2019-10-20</p>
 * @authors <p>Zaouzaou Yacine , Erdogan Kevin</p>
 *
 */
public class 		ControlerEproviderOutboundPort 
extends 		AbstractOutboundPort 
implements 		EnergyProviderI
{
	
	
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 5106301845178844822L;

	public 			ControlerEproviderOutboundPort (
			String uri, 
			ComponentI owner
			) throws Exception
	{
		super(uri , EnergyProviderI.class , owner);
		
		assert uri != null && owner instanceof Controler;
	}

	@Override
	public double egergyProduiced() throws Exception {
		return ((EnergyProviderI) this.connector).egergyProduiced();
	}
	

}
