package org.ojothepojo.lifx.event;


import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import org.ojothepojo.lifx.LifxBulb;
import org.ojothepojo.lifx.message.Message;
import org.ojothepojo.lifx.message.device.response.StatePower;
import org.ojothepojo.lifx.message.light.response.StateColor;


/**
 * This class can be used to create a log statement for each event bulb message that is received.
 */
public class DiscoveryEventHandler {
	
	public List<LifxBulb> bulbs = new ArrayList<LifxBulb>();

    @Subscribe
    public void handleEvent(MessageReceivedEvent event) {
        if (event.getMessage() != null) {
        	Message msg = event.getMessage();
        	if(msg instanceof StateColor) {
        		StateColor state = (StateColor)msg;
        		LifxBulb bulb = new LifxBulb();
        		bulb.ipAddress = state.getSource();
        		bulb.macAddress = state.getTargetAsString();
        		bulb.brightness = state.brightness;
        		bulb.hue = state.hue;
        		bulb.saturation = state.saturation;
        		bulb.kelvin = state.kelvin;
        		bulb.power = state.power;
        		bulbs.add(bulb);
        	}else if(msg instanceof StatePower) {
	        	StatePower state = (StatePower)msg;
	    		LifxBulb bulb = new LifxBulb();
	    		bulb.ipAddress = state.getSource();
	    		bulb.macAddress = state.getTargetAsString();
	    		System.out.println(state.getTargetAsString());
	    		bulb.power = (short) (state.level ? 100 : 0);
	    		bulbs.add(bulb);
        	}
        }
    }
}