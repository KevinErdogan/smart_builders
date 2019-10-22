package fr.sorbonne_u.smartgrid.port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import fr.sorbonne_u.smartgrid.coponenet.Controler;
import fr.sorbonne_u.smartgrid.interfaces.EnergyProviderI;

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
