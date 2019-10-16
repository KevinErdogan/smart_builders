package fr.smart_builders.system.components;

import java.util.concurrent.TimeUnit;

import fr.smart_builders.system.interfaces.FridgeI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

@OfferedInterfaces(offered= {FridgeI.class})
public class Fridge extends AbstractComponent{
	
	private boolean isOn=false;
	private final double maxTemp=7.007;
	private final double minTemp=2.002;
	private double curTemp=25;
	private double consumptionPerSec = 100;
	private boolean imAuthorized = false;
	
	public Fridge(String uri, String inboundPortURI) throws Exception {
		super(uri,1,0);
		assert	this.fridgeTemp() == 25 ;
	}

	public boolean authorize() {
		return imAuthorized=true;
	}
	public boolean notAuthorize() {
		return imAuthorized=false;
	}
	
	protected boolean switchOn() throws Exception {
		return isOn=true;
	}

	protected boolean switchOff() throws Exception {
		return isOn=false;
	}

	public double fridgeTemp() throws Exception {
		return curTemp;
	}
	
	public double consumption() throws Exception{
		 if(isOn) return consumptionPerSec; else return 0;
	}

	@Override
	public void			start() throws ComponentStartException
	{
		super.start() ;
		this.logMessage("starting consumer component.") ;
		// Schedule the first service method invocation in one second.
		this.scheduleTask(
			new AbstractComponent.AbstractTask() {
				@Override
				public void run() {
					try {
						while(true) {
							if(isOn) {
								curTemp-=0.1;
								if(curTemp<=minTemp)
									switchOff();
							}
							else {
								curTemp+=0.05;
								if(curTemp>=maxTemp && imAuthorized)
									switchOn();
							}
								
							Thread.sleep(1000);
						}
					} catch (Exception e) {
						throw new RuntimeException(e) ;
					}
				}
			},
			1000, TimeUnit.MILLISECONDS);
	}
		
	
}
