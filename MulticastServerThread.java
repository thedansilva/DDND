import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastServerThread extends Thread {
    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    public MulticastServerThread() throws IOException {
        super("MulticastServerThread");
        socket = new DatagramSocket(4445);
    }
    public void run() {
        for (int x = 0; x < 5; x++) {
            try {
                byte[] buf = new byte[256];
                String dString = null;
                dString = new Date().toString();
                buf = dString.getBytes();
                InetAddress group = InetAddress.getByName("230.0.0.1");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	socket.close();
    }
}