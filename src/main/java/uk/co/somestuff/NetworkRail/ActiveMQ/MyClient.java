package uk.co.somestuff.NetworkRail.ActiveMQ;

import net.ser1.stomp.Client;
import net.ser1.stomp.Listener;
import org.json.JSONObject;

/**
 * Example client that connects to the Network Rail ActiveMQ
 * and subscribes a listener to receive real-time messages
 *
 * @author Martin.Swanson@blackkitetechnology.com
 */

public class MyClient {

    public static JSONObject BP_SOP;

    //Network Rail ActiveMQ server
    private static final String SERVER = "datafeeds.networkrail.co.uk";

    // Server port for STOMP clients
    private static final int PORT = 61618;

    public void connect (String USERNAME, String PASSWORD, String TOPIC) throws Exception {
        System.out.println("| Connecting...");
        Client client = new Client(SERVER, PORT, USERNAME, PASSWORD);
        if (client.isConnected()) {
            System.out.println("| Connected to " + SERVER + ":" + PORT);
        } else {
            System.out.println("| Could not connect");
            return;
        }
        System.out.println("| Subscribing...");
        Listener listener = new MyListener();
        client.subscribe("/topic/" + TOPIC , listener);
        System.out.println("| Subscribed to " + TOPIC);
        System.out.println("| Waiting for message...");
    }
}