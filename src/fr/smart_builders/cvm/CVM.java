package fr.smart_builders.cvm;


import fr.smart_builders.component.Battery;
import fr.smart_builders.component.Controler;
import fr.smart_builders.component.Counter;
import fr.smart_builders.component.Fridge;
import fr.smart_builders.component.Owen;
import fr.smart_builders.component.SolarPanel;
import fr.smart_builders.component.Tv;
import fr.smart_builders.connectors.BatteryConnector;
import fr.smart_builders.connectors.CounterMonitorConnector;
import fr.smart_builders.connectors.CounterServiceConnector;
import fr.smart_builders.connectors.FridgeConnector;
import fr.smart_builders.connectors.OwenConnector;
import fr.smart_builders.connectors.SolarPConnector;
import fr.smart_builders.connectors.TvConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

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


public class 		CVM 
extends 		AbstractCVM
{
	
	/* Components */
	protected static final String CONTROLER_URI 	= "controler-1";
	
	protected static final String SOLARPANEL_URI 	= "solar_panel-1";
	
	protected static final String FRIDGE_URI 		= "fridge-1";
	
	protected static final String TV_URI 			= "tv-1";
	
	protected static final String COUNTER_URI 		= "counter-1";
	
	protected static final String OWEN_URI			= "owen-1";
	
	protected static final String BATTERY_URI		= "battery-1";
	
	
	/*Ports*/
	
	//inbount
	protected static final String FRIDGEIBP_URI		= "fridgeibp-1";
	
	protected static final String TVIBP_URI			= "tvibp-1";
	
	protected static final String SOLARPIBP_URI		= "solarpibp-1";
	
	protected static final String COUNTERIBP_URI	= "counteribp-1";
	
	protected static final String MONITORTVIBP_URI	= "monitortvibp-1";
	
	protected static final String MONITORFRIDGEIBP_URI	= "monitorfridgeibp-1";
	
	protected static final String MONITORSPIBP_URI	= "monitorspibp-1";
	
	protected static final String OWENIBP_URI		= "owenibp-1";
	
	protected static final String MONITOROWENIBP_URI = "monitorowenibp-1";
	
	protected static final String BATTERYIBP_URI 	= "batteryibp-1";
	
	protected static final String MONITORBATIBP_URI = "monitorbatteryibp-1";
	
	//outbound
	
	protected static final String FRIDGEOBP_uri 	= "fridgeobp-1";
	
	protected static final String TVOBP_uri 		= "tvobp-1";
	
	protected static final String SOLARPOBP_uri 	= "solarpobp-1";
	
	protected static final String COUNTEROBP_uri 	= "counterobp-1";
	
	protected static final String CPTFRIDOBP_uri 	= "counterfridgeobp-1";
	
	protected static final String CPTFTVOBP_uri 	= "countertveobp-1";
	
	protected static final String CPTFSPOBP_uri 	= "countersolarpanelobp-1";
	
	protected static final String CPTOWENOBP_uri 	= "counterowenobp-1";
	
	protected static final String OWENOBP_uri 		= "owenobp-1";
	
	protected static final String BATTERYOBP_uri	= "batteryobp-1";
	
	protected static final String CPTBATOBP_uri 	= "counterbattery-1";
	
	
	
	
	/* references to components*/
	protected String controler;
	protected String fridge;
	protected String solarPanel;
	protected String tv;
	protected String counter;
	protected String owen;
	protected String battery;
	
	
	
	
	public CVM() throws Exception {
		super();
	}
	
	@Override
	public void 			deploy () throws Exception
	{
		assert ! this.deploymentDone() ;
		
		
		//create smart fridge component
		this.fridge = 
				AbstractComponent.createComponent(
						Fridge.class.getCanonicalName(), 
						new Object [] {
							FRIDGE_URI,
							FRIDGEIBP_URI,
							MONITORFRIDGEIBP_URI
						});
		assert	this.isDeployedComponent(this.fridge);
		
		this.toggleTracing(this.fridge);
		this.toggleLogging(this.fridge);
		
		//create battery
		this.battery = 
				AbstractComponent.createComponent(
						Battery.class.getCanonicalName(),
						new Object [] {
								BATTERY_URI,
								BATTERYIBP_URI,
								MONITORBATIBP_URI
						});
	
		assert this.isDeployedComponent(this.battery);
		
		this.toggleTracing(this.battery);
		this.toggleLogging(this.battery);
		
		
		//create smart tv component
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
		
		//create solar panel component
		this.solarPanel = 
				AbstractComponent.createComponent(
						SolarPanel.class.getCanonicalName(), 
						new Object [] {
							SOLARPANEL_URI, 
							SOLARPIBP_URI,
							MONITORSPIBP_URI
						});
		assert	this.isDeployedComponent(this.solarPanel);
		
		this.toggleTracing(this.solarPanel);
		this.toggleLogging(this.solarPanel);
		
		//create counter component
		this.counter = 
				AbstractComponent.createComponent(
						Counter.class.getCanonicalName(), 
						new Object [] {
								COUNTER_URI,
								COUNTERIBP_URI,
								CPTFRIDOBP_uri, 
								CPTFTVOBP_uri, 
								CPTFSPOBP_uri,
								CPTOWENOBP_uri, 
								CPTBATOBP_uri
						});
		
		assert 	this.isDeployedComponent(this.counter);
		
		this.toggleTracing(this.counter);
		this.toggleLogging(this.counter);
		
		
		//create controler component
		this.controler = 
				AbstractComponent.createComponent(
						Controler.class.getCanonicalName(),
						new Object [] {
								CONTROLER_URI,
								SOLARPOBP_uri,
								COUNTEROBP_uri,
								FRIDGEOBP_uri,
								TVOBP_uri,
								OWENOBP_uri, 
								BATTERYOBP_uri
								}
						);
		
		assert	this.isDeployedComponent(this.controler);
		
		this.toggleTracing(this.controler);
		this.toggleLogging(this.controler);
		
		//create owen component 
		this.owen = 
				AbstractComponent.createComponent(
						Owen.class.getCanonicalName(), 
						new Object [] {
							OWEN_URI,
							OWENIBP_URI,
							MONITOROWENIBP_URI
						});
		
		assert this.isDeployedComponent(this.owen);

		this.toggleTracing(this.owen);
		this.toggleLogging(this.owen);
		
		
		//---------------------------------------------------------------------------------------------------
		// Connection phase
		//----------------------------------------------------------------------------------------------------
		
		
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
		
		//Connect controler to owen
		this.doPortConnection(
							this.controler,
							OWENOBP_uri,
							OWENIBP_URI,
							OwenConnector.class.getCanonicalName());
		
		//Connect controler to battery
		this.doPortConnection(
							this.controler, 
							BATTERYOBP_uri, 
							BATTERYIBP_URI, 
							BatteryConnector.class.getCanonicalName());
		
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
		
		//Connect counter to fridge
		this.doPortConnection(
							this.counter, 
							CPTFRIDOBP_uri, 
							MONITORFRIDGEIBP_URI, 
							CounterMonitorConnector.class.getCanonicalName());
		
		//Connect counter to owen 
		this.doPortConnection(
							this.counter,
							CPTOWENOBP_uri,
							MONITOROWENIBP_URI, 
							CounterMonitorConnector.class.getCanonicalName());
		
		//Connect counter to tv
		this.doPortConnection(
							this.counter, 
							CPTFTVOBP_uri, 
							MONITORTVIBP_URI, 
							CounterMonitorConnector.class.getCanonicalName());
		
		//Connect counter to battery
		this.doPortConnection(
							this.counter, 
							CPTBATOBP_uri, 
							MONITORBATIBP_URI, 
							CounterMonitorConnector.class.getCanonicalName());
		
		//Connect counter to solar panel
		this.doPortConnection(
							this.counter, 
							CPTFSPOBP_uri, 
							MONITORSPIBP_URI, 
							CounterMonitorConnector.class.getCanonicalName());	
		
		super.deploy();
		assert this.deploymentDone();
	}
	
	@Override
	public void 			finalise () throws Exception
	{
		
		//disconnect controler from panel solar
		this.doPortDisconnection(this.controler, SOLARPOBP_uri);
		
		//disconnect conroler from fridge
		this.doPortDisconnection(this.controler, FRIDGEOBP_uri);
		
		//disconnect controler trom owen
		this.doPortDisconnection(this.controler ,  OWENOBP_uri);
		
		//disconnect controler from tv
		this.doPortDisconnection(this.controler, TVOBP_uri);
		
		//disconnect crontroler from counter
		this.doPortDisconnection(this.controler, COUNTEROBP_uri);
		
		//diconnect controler from battery
		this.doPortDisconnection(this.controler, BATTERYOBP_uri);
		
		//disconnect counter from fridge
		this.doPortDisconnection(this.counter, CPTFRIDOBP_uri);
		
		//disconnect counter from owen 
		this.doPortDisconnection(this.counter ,  CPTOWENOBP_uri);
		
		//disconnect counter from tv
		this.doPortDisconnection(this.counter, CPTFTVOBP_uri);
		
		//disconnect counter from solar panel
		this.doPortDisconnection(this.counter, CPTFSPOBP_uri);
		
		//disconnect counter from battery
		this.doPortDisconnection(this.counter, CPTBATOBP_uri);
		
		super.finalise();
	}
	
	@Override
	public void 			shutdown() throws Exception
	{
		assert 	this.allFinalised();
		
		super.shutdown();
	}
	
	
	public static void 		main (String [] args) 
	{
		try {
			CVM cvm = new CVM();
			cvm.startStandardLifeCycle(200000L);
			
			Thread.sleep(50000L);
			
			System.exit(0);
		}catch (Exception e) {
			throw new RuntimeException (e);
		}
	}
	
}
