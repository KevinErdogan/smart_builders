package fr.smart_builders.simulator.models.events.solarpanel;

import fr.smart_builders.simulator.models.solarpanel.SolarPanelModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//---------------------------------------------------------------------------------------------------
/**
 * The class <code>SolarBrightnessChanged</code> describes the event that the 
 * sun brightness hitting the solar panel has changed for example because of 
 * a cloud or sunrise or sunset
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p> 
 */
public class 								SolarBrightnessChanged 
extends 									AbstractSolarPanelEvent
{
	public static class						Brightness
	implements 								EventInformationI
	{

		/** generated */
		private static final long 			serialVersionUID = -5385639814151762914L;
		public final double 				brightness ;
		public 								Brightness (double value) 
		{
			this.brightness = value ; 
		}
	}
	
	/** generated */
	private static final long 				serialVersionUID = 7554602379547604041L;

	public 									SolarBrightnessChanged(
										Time timeOfOccurrence, 	
										double brightness) 
	{
		super(timeOfOccurrence, new Brightness(brightness));
	}
	
	@Override
	public String 						eventAsString ()
	{
		return "SolarPanel::BrightnessChanged" ;
	}
	
	@Override
	public boolean 						hasPriorityOver (EventI e)
	{
		return false ;
	}
	
	@Override
	public void 						executeOn (AtomicModel model) 
	{
		if (! (model instanceof SolarPanelModel)) {
			System.err.println(model.getClass().getCanonicalName());
		}
		
		assert 	model instanceof SolarPanelModel ;
		
		Brightness b = (Brightness) this.getEventInformation() ; 
		
		((SolarPanelModel) model).brightnessChanged(b.brightness) ;
	}
}










































