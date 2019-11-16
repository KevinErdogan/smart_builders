package fr.smart_builders.cvm;


import fr.smart_builders.component.Controler;
import fr.smart_builders.component.Counter;
import fr.smart_builders.component.Fridge;
import fr.smart_builders.component.SolarPanel;
import fr.smart_builders.component.Tv;
import fr.smart_builders.connectors.CounterMonitorConnector;
import fr.smart_builders.connectors.CounterServiceConnector;
import fr.smart_builders.connectors.FridgeConnector;
import fr.smart_builders.connectors.SolarPConnector;
import fr.smart_builders.connectors.TvConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;
import fr.sorbonne_u.components.helpers.CVMDebugModes;

//-------------------------------------------------------------------------------------

/**
 * 
 * The class <code>CVM<code> implements the mono JVM structure.
 * 
 * <p>Created on : 2019-10-21</p>
 * 
 * @authors <p>Zaouzaou Yacine , Erdogan Kevin</p>
 *
 */


public class 		DCVM 
extends 		AbstractDistributedCVM
{
	
	/* JVM */
	protected static String CONTROLER_JVM_URI 		= "controler";
	protected static String SOLARPANEL_JVM_URI		= "solarpanel";
	protected static String FRIDGE_JVM_URI			= "fridge";
	protected static String TV_JVM_URI				= "tv";
	protected static String COUNTER_JVM_URI			= "counter";
	
	/* Components */
	protected static final String CONTROLER_URI 	= "controler-1";
	
	protected static final String SOLARPANEL_URI 	= "solar_panel-1";
	
	protected static final String FRIDGE_URI 		= "fridge-1";
	
	protected static final String TV_URI 			= "tv-1";
	
	protected static final String COUNTER_URI 		= "counter-1";
	
	
	/*Ports*/
	
	//inbount
	protected static final String FRIDGEIBP_URI		= "fridgeibp-1";
	
	protected static final String TVIBP_URI			= "tvibp-1";
	
	protected static final String SOLARPIBP_URI		= "solarpibp-1";
	
	protected static final String COUNTERIBP_URI	= "counteribp-1";
	
	protected static final String MONITORTVIBP_URI	= "monitortvibp-1";
	
	protected static final String MONITORFRIDGEIBP_URI	= "monitorfridgeibp-1";
	
	protected static final String MONITORSPIBP_URI	= "monitorspibp-1";
	
	//outbound
	
	protected static final String FRIDGEOBP_uri 	= "fridgeobp-1";
	
	protected static final String TVOBP_uri 		= "tvobp-1";
	
	protected static final String SOLARPOBP_uri 	= "solarpobp-1";
	
	protected static final String COUNTEROBP_uri 	= "counterobp-1";
	
	protected static final String CPTFRIDOBP_uri 	= "counterfridgeobp-1";
	
	protected static final String CPTFTVOBP_uri 	= "countertveobp-1";
	
	protected static final String CPTFSPOBP_uri 	= "countersolarpanelobp-1";
	
	
	
	
	/* references to components*/
	protected String controler;
	protected String fridge;
	protected String solarPanel;
	protected String tv;
	protected String counter;
	
	
	
	
	public DCVM(String [] args , int xLayout , int yLayout) throws Exception {
		super(args , xLayout , yLayout);
	}
	
	
	@Override
	public void			initialise() throws Exception
	{
		// debugging mode configuration; comment and uncomment the line to see
		// the difference
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.PUBLIHSING) ;
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.CONNECTING) ;
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.COMPONENT_DEPLOYMENT) ;

		super.initialise() ;
		// any other application-specific initialisation must be put here

	}
	
	
	@Override
	public void			instantiateAndPublish() throws Exception
	{
		if (thisJVMURI.equals(CONTROLER_JVM_URI)) {
			this.controler = 
					AbstractComponent.createComponent(
							Controler.class.getCanonicalName(),
							new Object [] {
									CONTROLER_URI,
									SOLARPOBP_uri,
									COUNTEROBP_uri,
									FRIDGEOBP_uri,
									TVOBP_uri
									}
							);
			
			assert 	this.isDeployedComponent(this.controler);
			
			this.toggleTracing(this.controler);
			this.toggleLogging(this.controler);
			assert	this.controler != null && this.fridge == null && this.solarPanel == null && this.tv == null && this.counter == null;

		} else if (thisJVMURI.equals(COUNTER_JVM_URI)) {
			this.counter = 
					AbstractComponent.createComponent(
							Counter.class.getCanonicalName(), 
							new Object [] {
									COUNTER_URI,
									COUNTERIBP_URI,
									CPTFRIDOBP_uri, 
									CPTFTVOBP_uri, 
									CPTFSPOBP_uri
							});
			
			assert 	this.isDeployedComponent(this.counter);
			
			this.toggleTracing(this.counter);
			this.toggleLogging(this.counter);
			assert	this.counter != null && this.controler == null && this.fridge == null && this.solarPanel == null && this.tv == null ;

		} else if (thisJVMURI.equals(FRIDGE_JVM_URI)) {
			this.fridge = 
					AbstractComponent.createComponent(
							Fridge.class.getCanonicalName(), 
							new Object [] {
								FRIDGE_URI,
								FRIDGEIBP_URI,
								MONITORFRIDGEIBP_URI
							});
			
			assert 	this.isDeployedComponent(this.fridge);
			
			this.toggleTracing(this.fridge);
			this.toggleLogging(this.fridge);
			assert	this.fridge != null && this.counter == null && this.controler == null &&  this.solarPanel == null && this.tv == null ;
		}else if (thisJVMURI.equals(TV_JVM_URI)) {
			this.tv = 
					AbstractComponent.createComponent(
							Tv.class.getCanonicalName(), 
							new Object [] {
								TV_URI, 
								TVIBP_URI, 
								MONITORTVIBP_URI
							});
			assert 	this.isDeployedComponent(this.tv);
			
			this.toggleTracing(this.tv);
			this.toggleLogging(this.tv);
			
			assert	this.tv != null  && this.fridge == null && this.counter == null && this.controler == null &&  this.solarPanel == null;
			
		}else if (thisJVMURI.equals(SOLARPANEL_JVM_URI)) {
			this.solarPanel = 
					AbstractComponent.createComponent(
							SolarPanel.class.getCanonicalName(), 
							new Object [] {
								SOLARPANEL_URI, 
								SOLARPIBP_URI,
								MONITORSPIBP_URI
							});
			assert 	this.isDeployedComponent(this.solarPanel);
			
			this.toggleTracing(this.solarPanel);
			this.toggleLogging(this.solarPanel);
			assert	this.solarPanel != null && this.tv == null  && this.fridge == null && this.counter == null && this.controler == null;
			
			
		}else {
			
			System.out.println("Unknown JVM URI... " + thisJVMURI) ;
			System.out.println("tv jvm uri at init : "+TV_JVM_URI);
			System.out.println("is it tv ? : "+(thisJVMURI.equals(TV_JVM_URI)));

		}

		super.instantiateAndPublish();
	}
	
	@Override
	public void			interconnect() throws Exception
	{
		assert	this.isIntantiatedAndPublished() ;

		if (thisJVMURI.equals(CONTROLER_JVM_URI)) {

			assert	this.controler != null && this.fridge == null && this.solarPanel == null && this.tv == null && this.counter == null;
			//Connect controler to solar panel
			this.doPortConnection(
								this.controler, 
								SOLARPOBP_uri, 
								SOLARPIBP_URI, 
								SolarPConnector.class.getCanonicalName());
			
			//Connect controler to fridge
			this.doPortConnection(
								this.controler, 
								FRIDGEOBP_uri, 
								FRIDGEIBP_URI, 
								FridgeConnector.class.getCanonicalName());
			
			//Connect controler to tv
			this.doPortConnection(
								this.controler, 
								TVOBP_uri, 
								TVIBP_URI, 
								TvConnector.class.getCanonicalName());
			
			//Connect controler to counter 
			this.doPortConnection(
								this.controler, 
								COUNTEROBP_uri, 
								COUNTERIBP_URI, 
								CounterServiceConnector.class.getCanonicalName());
			

		}else if (thisJVMURI.equals(COUNTER_JVM_URI)) {
			
			assert	this.counter != null && this.controler == null && this.fridge == null && this.solarPanel == null && this.tv == null;
			
			//Connect counter to fridge
			this.doPortConnection(
								this.counter, 
								CPTFRIDOBP_uri, 
								MONITORFRIDGEIBP_URI, 
								CounterMonitorConnector.class.getCanonicalName());
			
			//Connect counter to tv
			this.doPortConnection(
								this.counter, 
								CPTFTVOBP_uri, 
								MONITORTVIBP_URI, 
								CounterMonitorConnector.class.getCanonicalName());
			
			//Connect counter to solar panel
			this.doPortConnection(
								this.counter, 
								CPTFSPOBP_uri, 
								MONITORSPIBP_URI, 
								CounterMonitorConnector.class.getCanonicalName());	
			
			
		}else if (thisJVMURI.equals(FRIDGE_JVM_URI)) {
			
			assert	this.fridge != null && this.counter == null && this.controler == null && this.solarPanel == null && this.tv == null;
			
			
		}else if (thisJVMURI.equals(TV_JVM_URI)) {
			
			assert	this.tv != null && this.fridge == null && this.counter == null && this.controler == null && this.solarPanel == null;
			
		} else if (thisJVMURI.equals(SOLARPANEL_JVM_URI)) {

			assert	this.solarPanel != null && this.tv == null && this.fridge == null && this.counter == null && this.controler == null;

		} else {

			System.out.println("Unknown JVM URI... " + thisJVMURI) ;
			System.out.println("tv jvm uri at intercon : "+TV_JVM_URI);
			System.out.println("is it tv ? : "+(thisJVMURI.equals(TV_JVM_URI)));

		}

		super.interconnect();
	}
	
	
	@Override
	public void 			finalise () throws Exception
	{
		
		if (thisJVMURI.equals(CONTROLER_JVM_URI)) {

			assert	this.controler != null && this.fridge == null && this.solarPanel == null && this.tv == null && this.counter == null;
			
			
			//disconnect controler from panel solar
			this.doPortDisconnection(this.controler, SOLARPOBP_uri);
			
			//disconnect conroler from fridge
			this.doPortDisconnection(this.controler, FRIDGEOBP_uri);
			
			//disconnect controler from tv
			this.doPortDisconnection(this.controler, TVOBP_uri);
			
			//disconnect crontroler from counter
			this.doPortDisconnection(this.controler, COUNTEROBP_uri);
			

		}else if (thisJVMURI.equals(COUNTER_JVM_URI)) {
			
			assert	this.counter != null && this.controler == null && this.fridge == null && this.solarPanel == null && this.tv == null;

			
			//disconnect counter from fridge
			this.doPortDisconnection(this.counter, CPTFRIDOBP_uri);
			
			//disconnect counter from tv
			this.doPortDisconnection(this.counter, CPTFTVOBP_uri);
			
			//disconnect counter from solar panel
			this.doPortDisconnection(this.counter, CPTFSPOBP_uri);
			
			
		}else if (thisJVMURI.equals(FRIDGE_JVM_URI)) {
			
			assert	this.fridge != null && this.counter == null && this.controler == null && this.solarPanel == null && this.tv == null;
			
			
		}else if (thisJVMURI.equals(TV_JVM_URI)) {
			
			assert	this.tv != null && this.fridge == null && this.counter == null && this.controler == null && this.solarPanel == null;
			
		} else if (thisJVMURI.equals(SOLARPANEL_JVM_URI)) {

			assert	this.solarPanel != null && this.tv == null && this.fridge == null && this.counter == null && this.controler == null;

		} else {

			System.out.println("Unknown JVM URI... " + thisJVMURI) ;
			System.out.println("tv jvm uri at fina: "+this.TV_JVM_URI);
			System.out.println("is it tv ? : "+(thisJVMURI.equals(this.TV_JVM_URI)));
		}

		
		super.finalise();
	}
	
	@Override
	public void 			shutdown() throws Exception
	{
		if (thisJVMURI.equals(CONTROLER_JVM_URI)) {

			assert	this.controler != null && this.fridge == null && this.solarPanel == null && this.tv == null && this.counter == null;


		}else if (thisJVMURI.equals(COUNTER_JVM_URI)) {
			
			assert	this.counter != null && this.controler == null && this.fridge == null && this.solarPanel == null && this.tv == null;
			
			
		}else if (thisJVMURI.equals(FRIDGE_JVM_URI)) {
			
			assert	this.fridge != null && this.counter == null && this.controler == null && this.solarPanel == null && this.tv == null;
			
			
		}else if (thisJVMURI.equals(TV_JVM_URI)) {
			
			assert	this.tv != null && this.fridge == null && this.counter == null && this.controler == null && this.solarPanel == null;
			
		} else if (thisJVMURI.equals(SOLARPANEL_JVM_URI)) {

			assert	this.solarPanel != null && this.tv == null && this.fridge == null && this.counter == null && this.controler == null;

		} else {

			System.out.println("Unknown JVM URI... " + thisJVMURI) ;
			System.out.println("tv jvm uri : at shutdown "+this.TV_JVM_URI);
			System.out.println("is it tv ? : "+(thisJVMURI.equals(this.TV_JVM_URI)));

		}	
		super.shutdown();
	}
	
	
	public static void 		main (String [] args) 
	{
		try {
			DCVM da  = new DCVM(args, 2, 5) ;
			da.startStandardLifeCycle(200000L) ;
			
			Thread.sleep(50000L) ;
			
			System.exit(0) ;
		}catch (Exception e) {
			throw new RuntimeException (e);
		}
	}
	

}
