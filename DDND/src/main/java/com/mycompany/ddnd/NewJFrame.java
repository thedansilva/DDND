/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddnd;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author dan
 */
public class NewJFrame extends javax.swing.JFrame implements Runnable {

    static MulticastSocket socket;
    static InetAddress address;
    static DatagramPacket sendPacket;
    static DatagramPacket recvPacket;
    String username = "";
    String usernameRequest = "";
    boolean loggedIn = false;
    boolean unregistered = true;
    boolean listenerUp = false;

    // USED FOR PARSING DATA FROM SERVER
    public void run() {
        try {
            System.out.println("Thread running.");
            InetAddress address = InetAddress.getByName("230.0.0.1");
            socket = new MulticastSocket(4446);
            socket.joinGroup(address);
            while (true) {
                String received = recvString();
                System.out.println("BROADCAST (njf): " + received);
                String[] str = new String[3];
                str = received.split(";");

                //System.out.println("str[0] = "+str[0]+" / str[1] = "+str[1]);
                //login checker
                if (clientFrame.loggedIn == false) {
                    if (str[0].equals("full")) {
                        clientFrame.OutputLogArea.setText("The game is currently full.");
                    } else if (str[0].equals("taken")) {
                        clientFrame.OutputLogArea.setText("That username is already taken. Enter a new username");
                    } else if (str[0].equals("login")) {
                        clientFrame.OutputLogArea.setText("Successfully logged in as " + str[2] + ".\n");
                        clientFrame.username = str[2];
                        clientFrame.loggedIn = true;
                    }
                }
                if (clientFrame.loggedIn) {
                    switch (str[0]) {
                        case "heartbeat":
                            sendString("heartbeat;" + clientFrame.username + ";alive");
                            break;
                        case "stats":
                            clientFrame.statsArea.setText(str[1]);
                            break;
                            
                        case "output":
                            //clientFrame.OutputLogArea.setText(str[1]);
                            clientFrame.OutputLogArea.append(str[1]);
                            clientFrame.OutputLogArea.setCaretPosition(clientFrame.OutputLogArea.getText().length());
                            break;
                            
                        case "map":
                            System.out.println("Setting map area");
                            //clientFrame.mapTextArea.setText(str[1]);
                            clientFrame.mapTextArea.setText("");

                            String[] mapStr = new String[9];
                            mapStr[0] = str[1].substring(0, 20);
                            mapStr[1] = str[1].substring(20, 40);
                            mapStr[2] = str[1].substring(40, 60);
                            mapStr[3] = str[1].substring(60, 80);
                            mapStr[4] = str[1].substring(80, 100);
                            mapStr[5] = str[1].substring(100, 120);
                            mapStr[6] = str[1].substring(120, 140);
                            mapStr[7] = str[1].substring(140, 160);
                            mapStr[8] = str[1].substring(160, 180);
                            String formatMap =  mapStr[0] + "\n"
                                    + mapStr[1] + "\n"
                                    + mapStr[2] + "\n"
                                    + mapStr[3] + "\n"
                                    + mapStr[4] + "\n"
                                    + mapStr[5] + "\n"
                                    + mapStr[6] + "\n"
                                    + mapStr[7] + "\n"
                                    + mapStr[8];
                            /*
                            for (int i = 0; i < 9; i++) {
                                //clientFrame.mapTextArea.append(mapStr[i]+"\n");
                                for (int z = 0; z < 20; z++) {
                                    // this is supposed to break up str[1] by 20 x 9 and have it display in the textbox instead of what line 46 is doing
                                }
                            }
                            */
                            clientFrame.mapTextArea.setText(formatMap);
                            break;

                        default:
                            //System.out.println(str[0]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mapTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        statsArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        OutputLogArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        mapTextArea.setEditable(false);
        mapTextArea.setBackground(new java.awt.Color(0, 0, 0));
        mapTextArea.setColumns(20);
        mapTextArea.setFont(new java.awt.Font("Monospaced", 0, 35)); // NOI18N
        mapTextArea.setForeground(new java.awt.Color(255, 255, 255));
        mapTextArea.setLineWrap(true);
        mapTextArea.setRows(8);
        mapTextArea.setAutoscrolls(false);
        mapTextArea.setCaretColor(new java.awt.Color(240, 240, 240));
        mapTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mapTextAreaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mapTextAreaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(mapTextArea);

        statsArea.setBackground(new java.awt.Color(0, 0, 0));
        statsArea.setColumns(20);
        statsArea.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        statsArea.setForeground(new java.awt.Color(255, 255, 255));
        statsArea.setLineWrap(true);
        statsArea.setRows(6);
        statsArea.setWrapStyleWord(true);
        statsArea.setFocusable(false);
        jScrollPane2.setViewportView(statsArea);

        logArea.setBackground(new java.awt.Color(0, 0, 0));
        logArea.setColumns(20);
        logArea.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        logArea.setForeground(new java.awt.Color(255, 255, 255));
        logArea.setLineWrap(true);
        logArea.setRows(1);
        logArea.setWrapStyleWord(true);
        logArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                logAreaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                logAreaKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(logArea);

        OutputLogArea.setBackground(new java.awt.Color(0, 0, 0));
        OutputLogArea.setColumns(20);
        OutputLogArea.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        OutputLogArea.setForeground(new java.awt.Color(255, 255, 255));
        OutputLogArea.setLineWrap(true);
        OutputLogArea.setRows(5);
        OutputLogArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        OutputLogArea.setFocusable(false);
        jScrollPane4.setViewportView(OutputLogArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mapTextAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mapTextAreaKeyPressed
        try {
            if (loggedIn) {
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        sendString("command;" + username + ";up");
                        break;
                    case KeyEvent.VK_DOWN:
                        sendString("command;" + username + ";down");
                        break;
                    case KeyEvent.VK_LEFT:
                        sendString("command;" + username + ";left");
                        break;
                    case KeyEvent.VK_RIGHT:
                        sendString("command;" + username + ";right");
                        break;
                }
            } else {
                System.out.println("User not connected to any session.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Username not submitted yet.");
        }
    }//GEN-LAST:event_mapTextAreaKeyPressed

    private void mapTextAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mapTextAreaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_mapTextAreaKeyReleased

    // USED TO SEND INPUT TO THE SERVER IN THE FORM OF WORDS
    private void logAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_logAreaKeyPressed

    }//GEN-LAST:event_logAreaKeyPressed

    private void logAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_logAreaKeyReleased
        try {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    if (!listenerUp) {
                        try {
                            listenerUp = true; // disable socket joining multiple times
                            address = InetAddress.getByName("230.0.0.1");
                            socket = new MulticastSocket(4446);
                            socket.joinGroup(address);
                            NewJFrame listener = new NewJFrame();
                            Thread thread1 = new Thread(listener);
                            thread1.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (loggedIn == false) { // assume the user is trying to register to play
                        String tempUsername = logArea.getText().replaceAll("\n", ""); //get username from client
                        String toServer = "username;" + tempUsername + ";null";
                        this.sendString(toServer);
                        System.out.println("Sent username to server");
                    }

                    if (loggedIn) {
                        try {
                            String[] input = logArea.getText().replaceAll("\n", "").split(" ");
                            switch (input[0]) { // logarea past login
                                case "start":
                                    this.sendString("start;" + username + ";null");
                                    System.out.println("Sent start request on behalf of " + username + ".");
                                    break;
                                case "attack":
                                    this.sendString("attack;" + username + ";" + input[1]); // input[1] is assumed to be the direction
                                    break;
                                case "spell":
                                    this.sendString("spell;" + username + ";" + input[1]); // input[1] is assumed to be the direction
                                    break;
                                case "potion":
                                    this.sendString("potion;" + username + ";null");
                                    break;
                                case "wait":
                                    this.sendString("wait;" + username + ";null");
                                    break;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Invalid log data was attempted to be sent.");
                        }
                    }
                    this.logArea.setText(""); // reset log input lines
                    this.logArea.setCaretPosition(0); // reset log input lines
            }
        } catch (IOException e) {
            System.out.println("CLIENT ERROR ON ENTER KEYSTROKE");
            e.printStackTrace();
        }
    }//GEN-LAST:event_logAreaKeyReleased

    public void outputLogListener(String string) {
        this.OutputLogArea.setText(string);
    }

    /**
     * @param args the command line arguments
     */
    // method for sending a string packet to the UDP server. works globally (mainly using for the formKeyReleased method).
    public void sendString(String string) throws IOException { //i love java
        byte[] byteArray = new byte[256];
        byteArray = string.getBytes();
        sendPacket = new DatagramPacket(byteArray, byteArray.length, address, 4445);
        socket.send(sendPacket);
        System.out.println("Sent packet.");
    }

    /* receive a string from the server. The string is going to contain ALL information necessary to provide the server with the following:
    - 
     */
    public String recvString() throws IOException {
        byte[] buf = new byte[256];
        recvPacket = new DatagramPacket(buf, buf.length);
        System.out.println("Waiting for packet.");
        socket.receive(recvPacket);
        System.out.println("Received packet.");
        String received = new String(recvPacket.getData(), 0, recvPacket.getLength());
        return received;
    }

    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*NewJFrame*/ clientFrame = new NewJFrame();
                clientFrame.setLocationRelativeTo(null);
                clientFrame.setVisible(true);
                clientFrame.OutputLogArea.setText("Welcome to DDND!\nPlease enter your username in the log area followed by a class:\n- cleric\n- barbarian\n- mage\n- rogue");
                //try to login to server

                //socket.leaveGroup(address);
                //socket.close(); 
            }
        });
    }

    static NewJFrame clientFrame;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea OutputLogArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea logArea;
    private javax.swing.JTextArea mapTextArea;
    private javax.swing.JTextArea statsArea;
    // End of variables declaration//GEN-END:variables
}
