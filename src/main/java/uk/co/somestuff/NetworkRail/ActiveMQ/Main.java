package uk.co.somestuff.NetworkRail.ActiveMQ;

import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

public class Main {

    public static JSONObject sop = null;
    public static String signalingArea;

    public static void main(String[] args) throws Exception {

        if (args.length < 4) {
            System.out.println("[uk.co.somestuff.NetworkRail.ActiveMQ] Please specify SOP Table, Signaling Area, Topic, Username and Password in the form: Main.jar BP TD_ALL_SIG_AREA example@email.com password ./sopTable.json");
            System.out.println("[uk.co.somestuff.NetworkRail.ActiveMQ] To create a Network Rail Data Feed account please visit https://datafeeds.networkrail.co.uk/ntrod/login");
            System.exit(0);
        }

        if (args.length >= 5) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(args[4]));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(), "An error has occurred while looking for the SOP.json configuration file, Please try again", "Network Rail ActiveMQ - Exception", JOptionPane.ERROR_MESSAGE);
            }

            sop = new JSONObject(reader.lines().collect(Collectors.joining()));
        }

        signalingArea = args[0];

        System.out.println("[uk.co.somestuff.NetworkRail] Signaling area: " + signalingArea);

        new MyClient().connect(args[2], args[3], args[1]);

    }
}
