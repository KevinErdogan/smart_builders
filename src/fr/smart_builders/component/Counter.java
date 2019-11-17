package fr.smart_builders.component;

import java.util.concurrent.TimeUnit;

import fr.smart_builders.interfaces.CounterI;
import fr.smart_builders.interfaces.MonitorI;
import fr.smart_builders.port.CounterInboundPort;
import fr.smart_builders.port.MonitorOutboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.ports.PortI;



/**
 * 
 * <p>Created on : 2019-10-20</p>
 * 
 * @authors <p>Zaouzaou Yacine , Erdogan Kevin</p>
 */
@RequiredInterfaces (required = {MonitorI.class})
@OfferedInterfaces (offered = {CounterI.class})
public class 			Counter 
extends			AbstractComponent
{
	
	
	// static way to do it we can also use an arraylist to be more dynamique
	
	protected MonitorOutboundPort 			fridgeobp;
	protected MonitorOutboundPort 			tvobp;
	protected MonitorOutboundPort 			solarpobp;
	protected MonitorOutboundPort 			owenobp;
	protected MonitorOutboundPort			batteryobp;
	
	
	protected double 				total;
	
	
	
	
	
	protected 	Counter (
				String uri, 
				String inboundport, 
				String fridgeoutboundport,
				String tvoutboundport, 
				String spoutboundport, 
				String owenoutboundport,
				String batteryoutboundport
			) throws Exception
	{
		super (uri , 0 , 1);
		
		PortI p = new CounterInboundPort(
					inboundport, 
					this);
		p.publishPort();
		
		assert 	p.isPublished();
		
		this.fridgeobp = new MonitorOutboundPort(
					fridgeoutboundport, 
					this);
		this.fridgeobp.localPublishPort();
		
		assert 	this.fridgeobp.isPublished();
		
		this.owenobp = new MonitorOutboundPort(
					owenoutboundport, 
					this);
		
		this.owenobp.localPublishPort();
		
		assert this.owenobp.isPublished();
		
		
		this.tvobp = new MonitorOutboundPort(
					tvoutboundport, 
					this);
		this.tvobp.localPublishPort();
		
		assert	this.tvobp.isPublished();
		
		
		
		this.solarpobp = new MonitorOutboundPort(
					spoutboundport, 
					this);
		this.solarpobp.localPublishPort();
		
		assert 	this.solarpobp.isPublished();
		
		
		this.batteryobp = new MonitorOutboundPort(
					batteryoutboundport, 
					this);
		
		this.batteryobp.localPublishPort();
		
		assert this.batteryobp.isPublished();
		
		
		
		if (AbstractCVM.isDistributed) {
			this.executionLog.setDirectory(System.getProperty("user.dir")) ;
		} else {
			this.executionLog.setDirectory(System.getProperty("user.home")) ;
		}
		
		this.tracer.setTitle("counter") ;
		this.tracer.setRelativePosition(1, 1) ;
		
	}
	
	
	public double getTotal () {
		return this.total;
	}
	
	
	protected void 			doSum () throws Exception {
		double sum = 0;
		sum += this.fridgeobp.immediateConsumption();
		sum += this.tvobp.immediateConsumption();
		sum += this.owenobp.immediateConsumption();
		sum += this.batteryobp.immediateConsumption();
		this.total = sum;
	}
	
	
	public void 			life_cycle () throws Exception 
	{
		this.doSum();
		this.logMessage("total consumption : "+this.total+"\n");
		
		
		this.scheduleTask(
				new AbstractComponent.AbstractTask() {
					@Override
					public void 	run () {
						try {
							((Counter)this.getTaskOwner ()).life_cycle ();
						}catch (Exception e) {
							throw new RuntimeException (e);
						}
					}
				}, 
				1000, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void 			start () throws ComponentStartException 
	{
		super.start();
		this.logMessage("start counter");
		this.scheduleTask(
				new AbstractComponent.AbstractTask() {
					@Override
					public void 	run () {
						try {
							((Counter) this.getTaskOwner()).life_cycle();
						}catch (Exception e) {
							throw new RuntimeException (e);
						}
					}
				}, 
				1000, TimeUnit.MILLISECONDS);
	}
	
	@Override 
	public void 			finalise () throws Exception 
	{
		this.logMessage("stopping counter component");
		this.printExecutionLogOnFile("counter");
		
		this.fridgeobp.unpublishPort();
		this.tvobp.unpublishPort();
		this.solarpobp.unpublishPort();
		this.owenobp.unpublishPort();
		this.batteryobp.unpublishPort();
		
		super.finalise();
	}

	
	@Override
	public void 			shutdown () throws ComponentShutdownException 
	{
		try {
			PortI [] p = this.findPortsFromInterface(CounterI.class);
			p[0].unpublishPort();
		}catch (Exception e) {
			throw new ComponentShutdownException (e);
		}
		super.shutdown();
	}
	
	
	@Override
	public void 			shutdownNow () throws ComponentShutdownException 
	{
		try {
			PortI [] p = this.findPortsFromInterface(CounterI.class);
			p[0].unpublishPort();
		}catch (Exception e) {
			throw new ComponentShutdownException (e);
		}
		super.shutdownNow();
	}
	
	

}
