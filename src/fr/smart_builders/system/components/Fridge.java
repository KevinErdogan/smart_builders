package fr.smart_builders.system.components;

import fr.smart_builders.system.interfaces.FridgeI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;

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

	public double imediatConsumption() throws Exception {
		 if(isOn) return consumptionPerSec; else return 0;
	}

	public double fridgeTemp() throws Exception {
		return curTemp;
	}
	
	private void apply() throws Exception {
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
	}
		
	
}
