package bttl10_20127473_20127582;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import bttl10_20127473_20127582.Server;

//public class ServerSocketThread implements Runnable {
//
//    Thread thrd;
//    Socket client;
//
//    ServerSocketThread(Socket client) {
//        thrd = new Thread(this);
//        this.client = client;
//        thrd.start();
//    }
//
//    // Entry point of thread.
//    @Override
//    public void run() {
//        try {
//            // Listens for a connection to be made to
//            // this socket and accepts it. The method blocks until
//            // a connection is made
//            System.out.println("Connect request is accepted...");
//            String clientHost = client.getInetAddress().getHostAddress();
//            int clientPort = client.getPort();
//            System.out.println("Client host = " + clientHost + " Client port = " + clientPort);
//            while (true) {
//
//                // Read data from the client
//                InputStream clientIn = client.getInputStream();
//                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
//                String msgFromClient = br.readLine();
//                System.out.println(String.format("Message received from %s:%d = %s", clientHost, clientPort, msgFromClient));
//
//                // Send response to the client
//                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("bye")) {
//                    for (Socket anotherClient : Server.clients) {
//                        if (client != anotherClient) {
//                            OutputStream clientOut = anotherClient.getOutputStream();
//                            PrintWriter pw = new PrintWriter(clientOut, true);
//                            String ansMsg = String.format("Message from %s:%d = %s", clientHost, clientPort, msgFromClient);
//                            pw.println(ansMsg);
//                        }
//                    }
//                }
//
//                // Close sockets
//                if (msgFromClient != null && msgFromClient.equalsIgnoreCase("bye")) {
//                    client.close();
//                    bttl10_20127473_20127582.Server.clients.remove(client);
//                    break;
//                }
//            }
//        } catch (IOException ie) {
//        }
//    }
//}
