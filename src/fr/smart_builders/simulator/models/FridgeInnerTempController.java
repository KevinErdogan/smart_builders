package fr.smart_builders.simulator.models;
//---------------------------------------------------------------------------

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import fr.smart_builders.simulator.models.events.RunFridge;
import fr.smart_builders.simulator.models.events.StopFridge;
import fr.sorbonne_u.devs_simulation.es.events.ES_Event;
import fr.sorbonne_u.devs_simulation.hioa.annotations.ImportedVariable;
import fr.sorbonne_u.devs_simulation.hioa.models.AtomicHIOAwithEquations;
import fr.sorbonne_u.devs_simulation.hioa.models.vars.Value;
import fr.sorbonne_u.devs_simulation.interfaces.SimulationReportI;
import fr.sorbonne_u.devs_simulation.models.annotations.ModelExternalEvents;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.time.Duration;
import fr.sorbonne_u.devs_simulation.models.time.Time;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;
import fr.sorbonne_u.devs_simulation.utils.AbstractSimulationReport;
import fr.sorbonne_u.utils.PlotterDescription;
import fr.sorbonne_u.utils.XYPlotter;
/**
 * The class <code>FridgeInnerTempController</code> implements a simple 
 * fridge controller that runs the fridge if its inner temperature is
 * too high and stop it when it's too low
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */


//	need to read the value of inner temperature of the fridge !!!
//	i think its better to include all this behavior in FridgeModel
//	on le lance en roue libre pour le moment 
//	il va falloir utiliser une horloge -> ticmodel

@ModelExternalEvents (
					exported = {
							RunFridge.class, 
							StopFridge.class
					})
public class 					FridgeInnerTempController 
extends 						AtomicHIOAwithEquations
{
	
	//----------------------------------------------------------------
	// inner class
	//----------------------------------------------------------------
	
	
	public static class 	FridgeInnerTempControllerReport
	extends 				AbstractSimulationReport
	{

		/**
		 * generated
		 */
		private static final long serialVersionUID = 6456499178238438438L;
		
		private Vector <ES_Event>				memory ;
		

		public 				FridgeInnerTempControllerReport(
						String modelURI, 
						Vector<ES_Event> memory) 
		{
			super(modelURI);
			this.memory = memory ; 
		}
		
		@Override
		public String 		toString () 
		{
			StringBuilder sb = new StringBuilder() ; 
			sb.append("\n-----------------------------------------\n") ;
			sb.append("FridgeInnerTempConroller report\n") ;
			sb.append("\n-----------------------------------------\n") ;
			sb.append("number of readings " + this.memory.size() + "\n") ;
			sb.append("values : ");
			for ( ES_Event e : this.memory ) {
				sb.append(" " + e + "\n") ; 
			}
			sb.append("\n-----------------------------------------\n") ;
			return sb.toString() ; 
		}
			
	}
	

	/**
	 * generated
	 */
	private static final long serialVersionUID = -6476690623475401970L;
	
	

	//----------------------------------------------------------------
	// const and varibales 
	//----------------------------------------------------------------
		
	/** URI of the model  */
	public static final String 						URI = "FridgeTempControl" ;
	
	
	private Duration								delay ; 
	
	
	// see output !!
	private Double 									sended_event = 0.0 ;
	
	
	
	
	

	/**
	 * 	Plotter used to plot the evolution of FridgeTempConroller
	 */
	protected XYPlotter 							decisions ; 
	
	//----------------------------------------------------------------
	// HIOA model variables
	//----------------------------------------------------------------
	
	
	// can't put it in final without initialize it !
	/** the inner temperature of the fridge, imported from the fridge model */
	@ImportedVariable (type = Double.class)
	protected Value<Double> 						fridgeInnerTemp ;
	
	
	protected Vector<ES_Event> 						memory ;
	
	private String 									SERIES ; 

	
	
	//----------------------------------------------------------------
	// constructor 
	//----------------------------------------------------------------
	public 						FridgeInnerTempController(
						String uri, 
						TimeUnit simulatedTimeUnit, 
						SimulatorI simulationEngine)
								throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine) ;
		this.memory = new Vector<ES_Event>() ; 
		this.SERIES = "standard" ;
	}
	
	//----------------------------------------------------------------
	// methods -- override 
	//----------------------------------------------------------------
	
	
	// did it as the example batterySensor 
	// in molene example
	@Override
	public void 			setSimulationRunParameters(
					Map<String, Object> simParams) 
					throws Exception 
	{
//		no need to do call super method !! 
//		super.setSimulationRunParameters(simParams);
		String vname =
				this.getURI() + ":" + PlotterDescription.PLOTTING_PARAM_NAME ;
			PlotterDescription pd = (PlotterDescription) simParams.get(vname) ;
			this.decisions = new XYPlotter(pd) ;
			this.decisions.createSeries(this.SERIES) ;
	}
	
	@Override
	public void 			initialiseState(
					Time initialTime) 
	{
		this.memory.clear() ;
		if (this.decisions != null) {
			this.decisions.initialise();
			this.decisions.showPlotter();
			Duration d = new Duration(1 , TimeUnit.SECONDS) ;
			this.delay = d ;
		}
		super.initialiseState(initialTime);
	}
	
	@Override
	protected void 			initialiseVariables (
						Time startTime)
	{
		this.logMessage("FridgeInnerTempController initializevariables");
	}

	@Override
	public Vector<EventI> 			output() {
		
		Vector<EventI> event = new Vector<>(1) ;

		Time t = this.getCurrentStateTime().
				add(this.getNextTimeAdvance()) ;
		
		
		
		if (this.fridgeInnerTemp == null) {
			return null ;
		}
		
		// add data to plotter 
		this.decisions.addData(		this.SERIES, 
									this.getCurrentStateTime().getSimulatedTime(), 
									sended_event);
		
		if (this.fridgeInnerTemp.v > FridgeModel.MAX_INNER_TEMP) {
			RunFridge ev = new RunFridge(t, null) ;
			event.add(ev) ;
			this.memory.add(ev) ; 
			sended_event = 1.0 ;
			System.err.println("send event : " + ev.getClass().getCanonicalName());
		}else if (this.fridgeInnerTemp.v < FridgeModel.MIN_INNER_TEMP) {
			StopFridge ev = new StopFridge (t , null) ;
			event.add(ev) ;
			this.memory.add(ev) ;
			sended_event = 2.0 ;
			System.err.println("send event : " + ev.getClass().getCanonicalName());
		}
		
		if (event.size() == 0 ) {
			return null ; 
		}
		
		// add data to plotter 
		this.decisions.addData(		this.SERIES, 
									this.getCurrentStateTime().getSimulatedTime(), 
									sended_event);
		
		this.logMessage(this.getCurrentStateTime() + 
						"make decision : "+
						event.get(0).getClass().getCanonicalName());
		return event;
	}

	
	

	
//	need to understand more about it
//	done with feeling very tight understanding of its utility & usage
	@Override
	public Duration 				timeAdvance() {
		this.logMessage("FridgeInnerTempController advanceTime");
		return this.delay ; 
	}
	
	
//	did it to follow the same way as it is done in examples
//	because for now i haven't external event
	@Override
	public void 					userDefinedExternalTransition 
						(Duration elapsedTime)
	{
		super.userDefinedExternalTransition(elapsedTime);
		this.logMessage("FridgeInnerTempController externalTransition");
		if (this.hasDebugLevel(1)) {
			this.logMessage(this.getCurrentStateTime() + 
								" External FridgeInnerTemps "+
								this.fridgeInnerTemp.v);
			
		}
	}
	
	@Override
	public void 					userDefinedInternalTransition 
						(Duration elapsedTime)
	{
		super.userDefinedInternalTransition(elapsedTime);
		this.logMessage("FridgeInnerTempController internaltransition");
		if (this.hasDebugLevel(1)) {
			this.logMessage(this.getCurrentStateTime() + 
								" Internal FridgeInnerTemps "+
								this.fridgeInnerTemp.v);
			
		}
	}
	
	
	@Override
	public void 					endSimulation (Time endTime) 
	throws 							Exception 
	{
		Thread.sleep(100000L);
		this.decisions.dispose() ;
		super.endSimulation(endTime);
	}
	
	@Override
	public SimulationReportI 		getFinalReport ()
	throws 						Exception
	{
		return new FridgeInnerTempControllerReport(
											this.getURI(), 
											this.memory) ;
	}
	
}
//---------------------------------------------------------------------------


























