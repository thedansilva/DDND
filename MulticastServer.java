import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastServer {
    public static void main(String[] args) throws java.io.IOException {
        new MulticastServerThread(1).start();
    }
}