package fr.smart_builders.simulator.models.solarpanel;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import fr.smart_builders.simulator.models.events.solarpanel.AbstractSolarPanelEvent;
import fr.smart_builders.simulator.models.events.solarpanel.SolarBrightnessChanged;
import fr.sorbonne_u.devs_simulation.hioa.models.AtomicHIOAwithEquations;
import fr.sorbonne_u.devs_simulation.interfaces.SimulationReportI;
import fr.sorbonne_u.devs_simulation.models.annotations.ModelExternalEvents;
import fr.sorbonne_u.devs_simulation.models.events.Event;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.time.Duration;
import fr.sorbonne_u.devs_simulation.models.time.Time;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;
import fr.sorbonne_u.devs_simulation.utils.AbstractSimulationReport;
import fr.sorbonne_u.utils.PlotterDescription;
import fr.sorbonne_u.utils.XYPlotter;

//-----------------------------------------------------------------------------------------
/**
 * The class <code>SolarPanelModel</code> implements a simple solar panel component 
 * (model) which holds all its features such as how much power it can 
 * produce in certain condition
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
@ModelExternalEvents (imported = {SolarBrightnessChanged.class})
public class 								SolarPanelModel 
extends 									AtomicHIOAwithEquations
{
	//----------------------------------------------------------------------------------------------------
	// inner class & enum
	//----------------------------------------------------------------------------------------------------	

	public static class 					SolarPanelReport 
	extends 								AbstractSimulationReport
	{
		/** generated */
		private static final long 			serialVersionUID = -5973025797334601737L;
		public 								SolarPanelReport(String modelURI) {
			super(modelURI);
		}
		@Override
		public String 						toString () 
		{
			return "SolarPanel(" + this.getModelURI() + ")" ; 
		}
	}
	
	//-----------------------------------------------------------------------------------------------------
	// constants & variables
	//-----------------------------------------------------------------------------------------------------
	
	/** generated */
	private static final long 				serialVersionUID = 5616005257507253643L;

	public static final String 				URI = "SolarPanelModel" ; 
	
	private static final String 			SERIES = "powerGenerated" ; 
	/** the yield of of the solar panel, fixed to one value
	 * normally we have to take into account more parameters  */
	public static final double 				YIELD = 0.88 ; 
	
	// remove when we add a tic model to take the role of the 
	// system inner clock
	private final Duration 					dur ; 
	
	private XYPlotter 						generationPlotter ;
	
	private double 							brightness ;
	
	private double 							lastGenerationValue ; 
	
	//-----------------------------------------------------------------------------------------------------
	// constructor
	//-----------------------------------------------------------------------------------------------------

	public 									SolarPanelModel(
										String uri, 
										TimeUnit simulatedTimeUnit, 
										SimulatorI simulationEngine) 
												throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
		this.dur = new Duration (1 , this.getSimulatedTimeUnit()) ; 
		PlotterDescription pd = new PlotterDescription (
										"GeneratedPower", 
										"Time (sec)",
										"Power (watt)", 
										1900 , 
										1200, 
										600, 
										400) ;
		this.generationPlotter = new XYPlotter(pd) ;
		this.generationPlotter.createSeries(SERIES) ;
	}

	
	//-----------------------------------------------------------------------------------------------------
	// override method
	//-----------------------------------------------------------------------------------------------------

	@Override
	public void 							initialiseState (Time initialTime)
	{
		this.generationPlotter.initialise();
		this.generationPlotter.showPlotter(); 
		super.initialiseState(initialTime);
	}
	
	@Override
	public void 							initialiseVariables (Time startTime) 
	{
		this.lastGenerationValue = 0.0 ;
		this.brightness = 0.0 ; 
		super.initialiseVariables(startTime) ; 
	}
	
	@Override
	public Vector<EventI> 					output() {
		// TODO Auto-generated method stub
		// nothing to do for now 
		return null;
	}

	@Override
	public Duration 						timeAdvance() {
		return this.dur;
	}
	
	@Override
	public void 							userDefinedInternalTransition (Duration elapsedTime)
	{
		this.computeReceiveNewData() ;
		this.generate(this.brightness) ;
		this.computeReceiveNewData() ;
	}
	
	@Override
	public void 							userDefinedExternalTransition (Duration elapsedTime) 
	{
		Vector<EventI> currentEvents = this.getStoredEventAndReset() ; 
		
		// one event at a time
		assert currentEvents != null && currentEvents.size () == 1 ; 
		
		Event ce = (Event) currentEvents.get(0) ;
		
		assert ce instanceof AbstractSolarPanelEvent ; 
		
		ce.executeOn(this);
		
		super.userDefinedExternalTransition(elapsedTime);
	}
	
	@Override
	public void 							endSimulation (Time endTime) 
	throws 								Exception
	{
		
	}
	
	@Override
	public SimulationReportI				getFinalReport () throws Exception
	{
		return new SolarPanelReport(this.getURI()) ; 
	}
	
	
	
	//-----------------------------------------------------------------------------------------------------
	// specific method
	//-----------------------------------------------------------------------------------------------------
	
	/**
	 * compute the power that the solar panel generates when 
	 * it receives a certain value of brightness 
	 * normally there are more parameters to take into account
	 * @param brightness
	 * @return power generated 
	 */
	private void 							generate (double brightness) 
	{
//		System.err.println(brightness);
		this.lastGenerationValue = SolarPanelModel.YIELD * brightness ; 
	}
	
	public void 							brightnessChanged (double value) 
	{
		assert value >= 0 ; 
		
		this.brightness = value ; 
	}
	
	private void 							computeReceiveNewData ()
	{
		this.generationPlotter.addData(
									SERIES, 
									this.getCurrentStateTime().getSimulatedTime(), 
									this.lastGenerationValue);
	}
	
}
//---------------------------------------------------------------------------------------------------------

















































