package fr.smart_builders.component;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import fr.smart_builders.interfaces.MonitorI;
import fr.smart_builders.interfaces.OvenI;
import fr.smart_builders.port.MonitorInboundPort;
import fr.smart_builders.port.OvenInboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.ports.PortI;

//----------------------------------------------------------------------------------------------------------------

/**
 * 
 * one temperature for now
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */


@OfferedInterfaces (offered = {OvenI.class})
public class 			Oven
extends 				AbstractComponent
{
	
	private static double MAX_NORMAL_MODE_RUN_CONSUMPTION 	= 800;
	private static double MAX_ECO_MODE_RUN_CONSUMPTION 		= 500;
	private static double STOP_CONSUMPTION 					= 0;
	private static double MAX_TEMP 							= 1000;
	private static double ECO_TEMP							= 500;
	

	
	private boolean canStart 			= true;
	private boolean isOn 				= false;
	private double temperature 			= 0;
	private double run_consumption 		= 0;
	private double maximum_consuption	= MAX_NORMAL_MODE_RUN_CONSUMPTION;
	private double maximum_temp 		= MAX_TEMP;
	private Calendar starttime			= null;
	
	protected Oven(	String uri , 
					String inboundport , 
					String minboundport) 
					throws Exception
	{
		super(uri , 0 , 2);
		assert uri != null;
		assert inboundport != null;
		assert minboundport != null;
		
		PortI pibp = new OvenInboundPort(inboundport, this);
		pibp.publishPort();
		
		assert 	pibp.isPublished();
		
		PortI mibp = new MonitorInboundPort(minboundport, this);
		mibp.publishPort();
		
		assert	mibp.isPublished();
		
		if (AbstractCVM.isDistributed) {
			this.executionLog.setDirectory(System.getProperty("user.dir")) ;
		} else {
			this.executionLog.setDirectory(System.getProperty("user.home")) ;
		}
		
		this.tracer.setTitle("Smart Owen") ;
		this.tracer.setRelativePosition(2, 2) ;
	}

	public boolean forbidStart () {
		this.canStart = false;
		return ! this.canStart;
	}
	
	public boolean switchOn () {
		if (! this.isOn && this.canStart) {
			this.isOn = true;
			this.run_consumption = this.maximum_consuption;
			this.temperature = this.maximum_temp;
		}
		return this.isOn;
	}
	
	public boolean switchOff () {
		if (this.isOn) {
			this.isOn = false;
			this.temperature = 0;
			this.run_consumption = 0;
		}
		return ! this.isOn;
	}
	
	public boolean allowStart () {
		this.canStart = true;
		return this.canStart;
	}

	public double getConsumption () {
		if (isOn) {
			return this.run_consumption;
			
		}
		return Oven.STOP_CONSUMPTION;
	}

	public boolean switchToEcoMode () {
		this.maximum_consuption = Oven.MAX_ECO_MODE_RUN_CONSUMPTION;
		this.maximum_temp = Oven.ECO_TEMP;
		return this.maximum_consuption == Oven.MAX_ECO_MODE_RUN_CONSUMPTION 
						&& this.maximum_temp == Oven.ECO_TEMP;
	}
	
	public boolean switchToNormalMode () {
		this.maximum_consuption = Oven.MAX_NORMAL_MODE_RUN_CONSUMPTION;
		this.maximum_temp = Oven.MAX_TEMP;
		return this.maximum_consuption == Oven.MAX_NORMAL_MODE_RUN_CONSUMPTION
						&& this.maximum_temp == Oven.MAX_TEMP;
	}
	
	public boolean startAt (Calendar starttime) {
		this.starttime = starttime;
		return this.starttime == starttime;
	}

	
	private void lifeCycle () {
		if (canStart) {
			if (time_to_start ()) {
				this.switchOn();
				this.starttime = null;
			}
		}
		
		String state = (isOn)?"on":"off";
		String temp = (isOn)?(""+this.temperature):("0");
		String consumption = (isOn)?(""+this.maximum_consuption):"0";
		String startScheduled = (this.starttime != null)?"mustrun at "+this.starttime.getTime():"no start scheduled";
		this.logMessage("i'm "+state+"\nmy temperature "+temp+"\nmyconsumption "
						+consumption+"\n"+startScheduled+"\n");
		
		this.scheduleTask (
				new AbstractComponent.AbstractTask () {
					@Override
					public void run () {
						try {
							((Oven) this.getTaskOwner()).lifeCycle();
						}catch (Exception e) {
							throw new RuntimeException ();
						}
					}
				}, 
				1000 , TimeUnit.MILLISECONDS);
	}
	
	
	@Override
	public void start () throws ComponentStartException {
		super.start();
		System.out.println("running oven");
		this.logMessage("starting smart owen");
		
		this.scheduleTask(new AbstractComponent.AbstractTask() {
			@Override
			public void run() {
				try {
					((Oven) this.getTaskOwner()).lifeCycle();
				}catch (Exception e) {
					throw new RuntimeException (e);
				}
			}
		}, 1000, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void 				finalise () throws Exception 
	{
		this.logMessage("stopping smart owen");
		this.printExecutionLogOnFile("owen");
		
		super.finalise();
	}

	@Override
	public void 				shutdown () throws ComponentShutdownException 
	{
		try {
			PortI [] pf = this.findPortsFromInterface(OvenI.class);
			pf[0].unpublishPort();
			
			PortI [] pm = this.findPortsFromInterface(MonitorI.class);
			pm[0].unpublishPort();
			
		}catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	@Override
	public void 				shutdownNow() throws ComponentShutdownException
	{
		try {
			PortI[] pf = this.findPortsFromInterface(OvenI.class);
			pf[0].unpublishPort();
			
			PortI [] pm = this.findPortsFromInterface(MonitorI.class);
			pm[0].unpublishPort();
		}catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
	}
	
		
	private boolean 			time_to_start () {
		if (this.starttime == null)
			return false;
		Calendar now = Calendar.getInstance();
		if (this.starttime.before(now))
			return true;
		return false;
	}
	
	
}


















