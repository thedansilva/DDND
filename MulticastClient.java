import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastClient {
    public static void main(String[] args) throws IOException {
        MulticastSocket socket = new MulticastSocket(4446);
        InetAddress address = InetAddress.getByName("230.0.0.1");
	    socket.joinGroup(address);
        DatagramPacket packet;
    	for (int i = 0; i < 100; i++) {
	        byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println(received);
	}
	socket.leaveGroup(address);
	socket.close();
    }

}