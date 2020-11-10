import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastServerThread extends Thread {
    MulticastSocket socket = null;
    BufferedReader in = null;
    InetAddress group;
    public MulticastServerThread() throws IOException {
        super("MulticastServerThread");
        socket = new MulticastSocket(4445);
        group = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(group);
    }
    public void run() {
        DatagramPacket packet;
            try {
                byte[] empty;
                while(true) {
                    empty = new byte[256];
                    packet = new DatagramPacket(empty, empty.length);
                    System.out.println("waiting");
                    socket.receive(packet);
                    System.out.println("Received: ");
                    String received = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(received);
                    byte[] buf = new byte[256];
                    String dString = "hello from server";
                    buf = dString.getBytes();
                    packet = new DatagramPacket(buf, buf.length, group, 4446);
                    System.out.println("Broadcasting");
                    socket.send(packet);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
	socket.close();
    }
}