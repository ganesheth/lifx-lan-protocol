import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.Random;

import org.ojothepojo.lifx.LifxBulb;
import org.ojothepojo.lifx.LifxClient;
import org.ojothepojo.lifx.LifxWrapper;
import org.ojothepojo.lifx.event.DiscoveryEventHandler;
import org.ojothepojo.lifx.event.LoggingEventHandler;
import org.ojothepojo.lifx.message.light.request.GetColor;
import org.ojothepojo.lifx.message.light.request.SetColor;

public class Main {

	public static void main(String[] args) {

		List<LifxBulb> bulbs = LifxWrapper.discoverBulbs(2000);
		for(LifxBulb bulb : bulbs) {
			bulb.power = 0;
			LifxWrapper.updateBulbPower(bulb);
			LifxWrapper.refreshBulbPower(bulb);
			System.out.println(bulb.power);
			
			bulb.power = 100;
			LifxWrapper.updateBulbPower(bulb);
			LifxWrapper.refreshBulbPower(bulb);
			System.out.println(bulb.power);
			
			bulb.brightness = 30000;
			LifxWrapper.updateBulbColor(bulb, 0);			
			LifxWrapper.refreshBulbState(bulb);
			System.out.println(bulb.brightness);
			
			bulb.brightness = 15000;
			LifxWrapper.updateBulbColor(bulb, 0);			
			LifxWrapper.refreshBulbState(bulb);
			System.out.println(bulb.brightness);
		}
	}

}
