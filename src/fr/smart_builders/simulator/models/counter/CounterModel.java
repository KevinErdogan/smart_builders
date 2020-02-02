package fr.smart_builders.simulator.models.counter;

import java.util.Vector;
import java.util.concurrent.TimeUnit;
import fr.smart_builders.simulator.models.counter.event.ConsumeEvent;
import fr.smart_builders.simulator.models.counter.event.ConsumptionResponseEvent;
import fr.sorbonne_u.devs_simulation.es.models.AtomicES_Model;
import fr.sorbonne_u.devs_simulation.interfaces.SimulationReportI;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.annotations.ModelExternalEvents;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.time.Duration;
import fr.sorbonne_u.devs_simulation.models.time.Time;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;
import fr.sorbonne_u.devs_simulation.utils.AbstractSimulationReport;
import fr.sorbonne_u.utils.PlotterDescription;
import fr.sorbonne_u.utils.XYPlotter;

//-----------------------------------------------------------------------------------------------------

/**
 * The class <code>CounterModel</code> implements a simple counter component ( model ) which
 * sum up the consumption of all the components plugged to it
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
@ModelExternalEvents (imported = {
		ConsumptionResponseEvent.class },
	exported = {ConsumeEvent.class
})
public class 								CounterModel 
extends 									AtomicModel
{

	//--------------------------------------------------------------------------------------
	//	inner class , enum
	//--------------------------------------------------------------------------------------
	
	public static class 					CounterReport
	extends									AbstractSimulationReport
	{
		/** generated */
		private static final long 			serialVersionUID = -2011053287920055693L;

		public 								CounterReport(
										String modelURI) 
		{
			super(modelURI);
		}
		
		@Override
		public String 						toString () 
		{
			return "CounterReport(" + this.getModelURI() + ")" ; 
		}
		
	}
	//--------------------------------------------------------------------------------------
	//	Constants and variables
	//--------------------------------------------------------------------------------------
	
	/** generated */
	private static final long 				serialVersionUID = -5457360226454500886L;
	
	public static final String 				URI = "CounterModel" ; 
	
	private static final String 			SERIES ="totalConsumtion" ; 
	
	private XYPlotter						consumptionPlotter ;
	
	/** interval between two reading value */
	private Duration 						interval ;
	
	/** total value consumed at a time t */
	double 									totalConsumption ; 

	
	

	public 									CounterModel(
									String uri, 
									TimeUnit simulatedTimeUnit, 
									SimulatorI simulationEngine) 
											throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
		PlotterDescription pd = new PlotterDescription(
										"Total Consuption", 
										"Time (sec)", 
										"Consumption (W)", 
										1900, 
										400, 
										600, 
										400);
		this.consumptionPlotter = new XYPlotter(pd) ; 
		this.consumptionPlotter.createSeries(SERIES) ;
	}
	//--------------------------------------------------------------------------------------
	//	methods
	//--------------------------------------------------------------------------------------

	@Override
	public void 							initialiseState (Time initialTime) 
	{
		this.consumptionPlotter.initialise() ;
		this.consumptionPlotter.showPlotter() ;
		// send an event to get consumptions every 5 seconds
		this.interval = new Duration (5 , this.getSimulatedTimeUnit()) ; 
		this.totalConsumption = 0 ; 
		super.initialiseState(initialTime) ;
		// schedule first event
		System.err.println("sending first event");
		Time t = this.getCurrentStateTime().add(this.interval) ;
		
		this.nextTimeAdvance = this.timeAdvance() ; 
		
		assert this.nextTimeAdvance != null ; 
		
		this.timeOfNextEvent = 
					this.getCurrentStateTime().add(this.nextTimeAdvance) ; 
	}
	
	@Override
	public Duration 						timeAdvance ()
	{
		return this.interval  ; 
	}
	
	@Override
	public Vector<EventI> 					output () 
	{
		
		Vector<EventI> events = new Vector<EventI>() ; 
		events.add(new ConsumeEvent(this.getCurrentStateTime())) ; 
		return events ; 
	}
	
	@Override
	public void 							userDefinedExternalTransition (Duration elapsedTime) 
	{
		super.userDefinedExternalTransition(elapsedTime);
		this.totalConsumption = 0 ; 
		Vector<EventI> currentEvents = this.getStoredEventAndReset() ; 
		
		if (currentEvents.size() > 0) {
			//receives normally as many event as there are consumers
	//		assert currentEvents != null && currentEvents.sier () == nbConsumers ; 
			
			for (EventI ce : currentEvents) {
				ce.executeOn(this);
			}
			
			this.computeReceiveDate()  ; 
		}
		
	}
	
	@Override
	public void 							userDefinedInternalTransition (Duration elapsedTime)
	{
		super.userDefinedInternalTransition(elapsedTime); 
	}
	
	@Override
	public SimulationReportI 				getFinalReport () throws Exception 
	{
		return new CounterModel.CounterReport(CounterModel.URI) ; 
	}
	
	//--------------------------------------------------------------------------------------
	//	specific methods
	//--------------------------------------------------------------------------------------
	
	public void 							getConsumtion (Double d)
	{
		System.err.println(d);
		this.totalConsumption += d ;
	}
	
	private void 							computeReceiveDate () 
	{
		this.consumptionPlotter.addData(
								SERIES, 
								this.getCurrentStateTime().getSimulatedTime() , 
								this.totalConsumption);
	}							
	

}
//-----------------------------------------------------------------------------------------------------





























