package org.ojothepojo.lifx;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import org.ojothepojo.lifx.event.DiscoveryEventHandler;
import org.ojothepojo.lifx.message.device.request.GetPower;
import org.ojothepojo.lifx.message.device.request.SetPower;
import org.ojothepojo.lifx.message.light.request.GetColor;
import org.ojothepojo.lifx.message.light.request.SetColor;

public class LifxWrapper {
	
	public static List<LifxBulb> discoverBulbs(int timeout)
	{
		LifxClient client = null;
		 try {
				client = new LifxClient();
				DiscoveryEventHandler discovery = new DiscoveryEventHandler();
				client.startListenerThread();
				client.getEventBus().register(discovery);
				client.sendMessage(new GetColor());
				Thread.sleep(timeout); // wait a bit for the bulb to respond.	
				client.getEventBus().unregister(discovery);
				client.stop();
				Thread.sleep(200); // Give the thread some time for the socket to close
				return discovery.bulbs;
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 finally
		 {
			 if(client != null)
				 client.cleanup();
		 }		 
		 return null;		
	}
	
	public static boolean refreshBulbState(LifxBulb bulb)
	{
		LifxClient client = null;
		 try {
				client = new LifxClient();
				DiscoveryEventHandler discovery = new DiscoveryEventHandler();
				client.startListenerThread();
				client.getEventBus().register(discovery);
				client.sendMessage(new GetColor(bulb.macAddress));
				Thread.sleep(500); // wait a bit for the bulb to respond.	
				client.getEventBus().unregister(discovery);
				client.stop();
				Thread.sleep(200); // Give the thread some time for the socket to close
				for(LifxBulb b : discovery.bulbs) {
					if(b.macAddress.equalsIgnoreCase(bulb.macAddress)) {
						bulb.brightness = b.brightness;
						bulb.power = b.power;
						return true;
					}
				}
				
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 finally
		 {
			 if(client != null)
				 client.cleanup();
		 }		 
		 return false;
	}
	
	public static boolean refreshBulbPower(LifxBulb bulb)
	{
		LifxClient client = null;
		 try {
				client = new LifxClient();
				DiscoveryEventHandler discovery = new DiscoveryEventHandler();
				client.startListenerThread();
				client.getEventBus().register(discovery);
				client.sendMessage(new GetPower(bulb.macAddress));
				Thread.sleep(500); // wait a bit for the bulb to respond.	
				//client.getEventBus().unregister(discovery);
				client.stop();
				Thread.sleep(200); // Give the thread some time for the socket to close
				for(LifxBulb b : discovery.bulbs) {
					if(b.macAddress.equalsIgnoreCase(bulb.macAddress)) {
						bulb.power = b.power;
						return true;
					}
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 finally
		 {
			 if(client != null)
				 client.cleanup();
		 }		 
		 return false;
	}
		
	
	public static boolean updateBulbColor(LifxBulb bulb, long duration)
	{
		LifxClient client = null;
		 try {
				client = new LifxClient();
				//client.startListenerThread();
				client.sendMessage(new SetColor(bulb.macAddress, bulb.hue, bulb.saturation, bulb.brightness, bulb.kelvin, duration));
				Thread.sleep(500); // wait a bit for the bulb to respond.		        
				client.stop();
				Thread.sleep(200); // Give the thread some time for the socket to close
				return true;
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 finally
		 {
			 if(client != null)
				 client.cleanup();
		 }
		 return false;
	}
	
	public static boolean updateBulbPower(LifxBulb bulb)
	{
		LifxClient client = null;
		 try {
				client = new LifxClient();
				//client.startListenerThread();
				client.sendMessage(new SetPower(bulb.macAddress, bulb.power > 0 ? true : false));
				Thread.sleep(500); // wait a bit for the bulb to respond.		        
				client.stop();
				Thread.sleep(200); // Give the thread some time for the socket to close
				return true;
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 finally
		 {
			 if(client != null)
				 client.cleanup();
		 }		 
		 return false;
	}


}
