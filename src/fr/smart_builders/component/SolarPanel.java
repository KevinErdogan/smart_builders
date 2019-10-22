package fr.smart_builders.component;

import fr.smart_builders.interfaces.MonitorI;
import fr.smart_builders.interfaces.SolarPanelI;
import fr.smart_builders.port.MonitorInboundPort;
import fr.smart_builders.port.PanelInboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.ports.PortI;

//--------------------------------------------------------------------


/**
 * 
 * <p>Created on : 2019-20-20</p>
 * 
 * @authors <p>Zaouzaou Yacine , Erdogan Kevin</p>
 *
 */

@OfferedInterfaces (offered = {SolarPanelI.class})
public class 			SolarPanel 
extends 		AbstractComponent 
{
	
	private double energyproduced = 1000;
	
	
	protected 				SolarPanel (
				String uri, 
				String inboundport, 
				String minboudport
			)throws Exception
	{
		super (uri , 0 , 1);
		
		
		//create ports 
		
		PortI ibp = new PanelInboundPort(
						inboundport, 
						this);
		ibp.publishPort();
		
		assert	ibp.isPublished();
		
		PortI mibp = new MonitorInboundPort(
						minboudport, 
						this);
		mibp.publishPort();
		
		assert 	mibp.isPublished();
		
		
		if (AbstractCVM.isDistributed) {
			this.executionLog.setDirectory(System.getProperty("user.dir")) ;
		} else {
			this.executionLog.setDirectory(System.getProperty("user.home")) ;
		}
		
		this.tracer.setTitle("solar panel") ;
		this.tracer.setRelativePosition(2, 0) ;
		
		
	}
	
	
	// get a function that computes the value of the production
	public double energy () {
		this.logMessage("I produced "+this.energyproduced+" now !");
		return energyproduced;
	}
	
	
	@Override
	public void 			start () throws ComponentStartException 
	{
		this.logMessage("starting solar panel");
		super.start();
	}
	
	@Override
	public void 			finalise () throws Exception 
	{
		this.logMessage("stopping solar panel");
		this.printExecutionLogOnFile("solar panel");
		super.finalise();
	}
	
	
	@Override
	public void 			shutdown () throws ComponentShutdownException 
	{
		try {
			PortI [] ps = this.findPortsFromInterface(SolarPanelI.class);
			ps[0].unpublishPort();
			
			PortI [] pm = this.findPortsFromInterface(MonitorI.class);
			pm[0].unpublishPort();
		}catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	
	@Override
	public void 			shutdownNow () throws ComponentShutdownException 
	{
		try {
			PortI [] ps = this.findPortsFromInterface(SolarPanelI.class);
			ps[0].unpublishPort();
			
			PortI [] pm = this.findPortsFromInterface(MonitorI.class);
			pm[0].unpublishPort();
		}catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
	}

	
}
