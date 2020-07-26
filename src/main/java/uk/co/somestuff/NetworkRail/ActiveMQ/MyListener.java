package uk.co.somestuff.NetworkRail.ActiveMQ;

import net.ser1.stomp.Listener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class MyListener implements Listener {

    JSONArray db = new JSONArray();

    @Override
    public void message(Map header, String body) {

        JSONArray Jbody = new JSONArray(body);

        for (int i = 0; i < Jbody.length(); i++) {
            JSONObject localJObjectF = Jbody.getJSONObject(i);
            JSONObject localJObjectO = localJObjectF.getJSONObject(localJObjectF.names().getString(0));
            if (localJObjectO.getString("area_id").equals(Main.signalingArea)) {
                if (localJObjectO.getString("msg_type").equals("SF") || localJObjectO.getString("msg_type").equals("SG")) {

                    /** Process the signals status **/

                    /** Set signal data to 8 bits long as well as reversing it **/

                    JSONArray signalData = new JSONArray();

                    /** The 'data' from the S-Class message is made into 8 bits and then flipped so that the 0th item from the received message (on the left) is the 0th in the array which makes it all easier **/

                    String dataBinary = Integer.toBinaryString(Integer.parseInt(localJObjectO.getString("data"),16));
                    for (int r = 0; r < dataBinary.length(); r++) {
                        signalData.put(String.valueOf(dataBinary.charAt(r)));
                    }
                    for (int r = 0; r < 8-dataBinary.length(); r++) {
                        signalData.put("0");
                    }

                    JSONArray revSignalData = new JSONArray();
                    for (int r = signalData.length()-1; r>=0; r--) {
                        revSignalData.put(signalData.get(r));
                    }

                    int address = Integer.parseInt(localJObjectO.getString("address"),16);

                    System.out.println("[uk.co.somestuff.NetworkRail] Address " + address + " (" + localJObjectO.getString("address") + ") data " + signalData.toString());

                    if (Main.sop != null) {
                        for (int r = 0; r < revSignalData.length(); r++) {

                            boolean present = false;

                            for (int e = 0; e < db.length(); e++) {
                                if (db.getJSONObject(e).getString("sig").equals(Main.sop.getJSONObject("SOP").getJSONObject(String.valueOf(address)).getString(String.valueOf(r)))) {
                                    present = true;
                                    if (!db.getJSONObject(e).getString("val").equals(revSignalData.getString(r))) {
                                        /** Signal has changed **/
                                        db.getJSONObject(e).put("val", revSignalData.getString(r));
                                        System.out.println("[uk.co.somestuff.NetworkRail] Signal " + Main.sop.getJSONObject("SOP").getJSONObject(String.valueOf(address)).getString(String.valueOf(r)) + " changed too " + revSignalData.getString(r) + " " + (revSignalData.getString(r).equals("1") ? "(G)" : "(R)"));
                                    }
                                }
                            }
                            if (!present) {
                                /** The Signal isn't in the array so we're adding it **/
                                db.put(new JSONObject().put("sig", Main.sop.getJSONObject("SOP").getJSONObject(String.valueOf(address)).getString(String.valueOf(r))).put("val", revSignalData.getString(r)));
                                System.out.println("[uk.co.somestuff.NetworkRail] Signal " + Main.sop.getJSONObject("SOP").getJSONObject(String.valueOf(address)).getString(String.valueOf(r)) + " set too " + revSignalData.getString(r) + " " + (revSignalData.getString(r).equals("1") ? "(G)" : "(R)"));
                            }
                        }
                    }
                } else if (localJObjectO.getString("msg_type").equals("CA") || localJObjectO.getString("msg_type").equals("CB") || localJObjectO.getString("msg_type").equals("CC")) {

                    /** Train has moved between berths **/

                    String to = "empty";
                    String from = "empty";

                    if (localJObjectO.has("to")) {
                        to = localJObjectO.getString("to");
                    }

                    if (localJObjectO.has("from")) {
                        from = localJObjectO.getString("from");
                    }

                    System.out.println("[uk.co.somestuff.NetworkRail] Train " + localJObjectO.getString("descr") + " has moved from " + from + " to " + to);

                }
            }
        }
    }
}