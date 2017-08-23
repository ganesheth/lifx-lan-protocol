package org.ojothepojo.lifx;

import com.google.common.eventbus.EventBus;

import lombok.Getter;
import org.ojothepojo.lifx.event.PacketListenerThread;
import org.ojothepojo.lifx.message.Message;
import org.ojothepojo.lifx.message.device.request.GetPower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Main class to start sending and receiving bulb messages.
 */
public class LifxClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(LifxClient.class);
    private static final int PORT = 56700;
    private Thread listenerThread;
    private final DatagramSocket socket;

    @Getter
    private final EventBus eventBus;
    
    private String broadcastAddress = "192.168.0.255";


    public LifxClient() throws SocketException {
        this.eventBus = new EventBus();
        this.socket = new DatagramSocket(PORT);
        // You might not want to broadcast, but address specific ip's for specific messages.
        // This exercise is left to the reader.
        this.socket.setBroadcast(true);
    }
    
    public LifxClient(String broadcastAddress) throws SocketException {
    	this();
    	this.broadcastAddress = broadcastAddress;
    }
    
    public EventBus getEventBus(){
    	return this.eventBus;
    }

    /**
     * Creates a thread that start listening for packets send by the bulbs.
     */
    public void startListenerThread() throws InterruptedException {
        listenerThread = new Thread(new PacketListenerThread(eventBus, socket));
        listenerThread.start();
    }

    public void stop() throws InterruptedException, IOException {
        if (listenerThread != null && listenerThread.isAlive()) {
            listenerThread.interrupt();
            LOGGER.info("Closing socket");
            socket.close();
            //LOGGER.info("Sending one last packet to interrupt the PacketListenerThread");
            //sendMessage(new GetPower());
        }
    }
    
    public void cleanup() {
    	if(socket != null && !socket.isClosed())
    		socket.close();
    }

    public void sendMessage(Message message) throws IOException {
        LOGGER.debug("Sending message: " + message.toString());
        // Here, we could limit the address to the single ip of the target bulb. But why does it only work with 255
        // at the end???
        InetAddress address = InetAddress.getByName(broadcastAddress);

        DatagramPacket sendPacket = new DatagramPacket(
                message.toBytes().array(),
                message.toBytes().array().length,
                address,
                PORT);
        socket.send(sendPacket);
    }
    

    static List<InetAddress> getNetworkBroadcastAddresses() {
        List<InetAddress> result = new ArrayList<InetAddress>();
        try {
            List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                    List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
                    for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                        InetAddress broadcast = interfaceAddress.getBroadcast();
                        if (broadcast != null) {
                            result.add(broadcast);
                        }
                    }
                }
            }
        } catch (SocketException e) {

        }
        return result;
    }    
}
