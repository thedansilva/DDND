import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastClient implements Runnable {
    static MulticastSocket socket;
    public void run() {
        try{
            System.out.println("Thread running.");
                while(true) {
                    DatagramPacket multiPacket;
                    InetAddress address = InetAddress.getByName("230.0.0.1");
                    socket = new MulticastSocket(4446);
                    socket.joinGroup(address);
                    byte[] buf = new byte[256];
                    multiPacket = new DatagramPacket(buf, buf.length);
                    System.out.println("Waiting for packet.");
                    socket.receive(multiPacket);
                    String received = new String(multiPacket.getData(), 0, multiPacket.getLength());
                    System.out.println("BROADCAST: " + received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        DatagramPacket packet;
        InetAddress address = InetAddress.getByName("230.0.0.1");
        socket = new MulticastSocket(4446);
	    socket.joinGroup(address);
        MulticastClient listener = new MulticastClient();
        Thread thread1 = new Thread(listener);
        thread1.start();
        try {
            Scanner in = new Scanner(System.in);
            byte[] cli = new byte[256];
            String test;
            while(true) {
                System.out.print("Enter thing: ");
                test = in.nextLine();
                cli = test.getBytes();
                packet = new DatagramPacket(cli, cli.length, address, 4445);
                socket.send(packet);
                test = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	socket.leaveGroup(address);
	socket.close();
    }
    
}