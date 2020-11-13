import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastServerThread extends Thread {
    MulticastSocket socket = null;
    BufferedReader in = null;
    InetAddress group;
    int type;
    public MulticastServerThread(int type) throws IOException {
        super("MulticastServerThread");
        this.type = type;
        socket = new MulticastSocket(4445);
        group = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(group);
    }
    public void run() {
        if(type == 1) {
            //new MulticastServerThread(2).start();
            try{new MulticastServerThread(2).start();}catch(IOException e){};
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
                    String[] str = received.split(";");
                    System.out.println("str[0] = "+str[0]+" / str[1] = "+str[1]);
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
        } else if (type == 2) {
            //Output output = new Output();
            try {
                Output output = new Output();
                output.sendText();
            } catch(InterruptedException e) {

            } catch(IOException e){};
            //try{output.sendText();}catch(InterruptedException e){};
        }
        DatagramPacket packet;
            /*try {
                byte[] empty;
                    Output output = new Output();
                while(true) {
                    try{output.sendText();}catch(InterruptedException e){}; 
                    empty = new byte[256];
                    packet = new DatagramPacket(empty, empty.length);
                    System.out.println("waiting");
                    socket.receive(packet);
                    System.out.println("Received: ");
                    String received = new String(packet.getData(), 0, packet.getLength());
                    String[] str = received.split(";");
                    System.out.println(received);
                    System.out.println("str[0] = "+str[0]+" / str[1] = "+str[1]);
                    byte[] buf = new byte[256];
                    String dString = "hello from server";
                    buf = dString.getBytes();
                    packet = new DatagramPacket(buf, buf.length, group, 4446);
                    System.out.println("Broadcasting");
                    socket.send(packet);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
	socket.close();
    }
}

class Output {
    MulticastSocket socket;
    InetAddress group;

    Output() throws IOException {
        this.socket = new MulticastSocket(4447);
        this.group = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(group);
    }

    void sendText() throws InterruptedException {
        try {
            while(true){
                byte[] empty = new byte[256];
                String txt = "This came from output class.";
                empty = txt.getBytes();
                DatagramPacket packet = new DatagramPacket(empty, empty.length, group, 4446);
                System.out.println("Sending txt");
                socket.send(packet);
                Thread.sleep(3000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}