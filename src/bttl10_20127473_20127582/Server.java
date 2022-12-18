package bttl10_20127473_20127582;

/* SERVER – may enhance to work for multiple clients */
import java.net.*;
import java.io.*;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    static volatile HashSet<Socket> clients = new HashSet<Socket>();

    public static void main(String[] args) {
        ServerSocket server = null;

        // Default port number we are going to use
        int portnumber = 1234;
        if (args.length >= 1) {
            portnumber = Integer.parseInt(args[0]);
        }

        // Create Server side socket
        try {
            server = new ServerSocket(portnumber);
        } catch (IOException ie) {
            System.out.println("Cannot open socket." + ie);
            System.exit(1);
        }
        System.out.println("ServerSocket is created " + server);
        while (true) {
            try {
                Socket client;
                System.out.println("Waiting for connect request...");
                client = server.accept();
                clients.add(client);
                // Read data from the client
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                ServerSocketThread mt1 = new ServerSocketThread(client);

            } catch (IOException ie) {
            }
        }
    }
}

class ServerSocketThread implements Runnable {

    Thread thrd;
    Socket client;
    String clientName = "";

    ServerSocketThread(Socket client) {
        thrd = new Thread(this);
        this.client = client;
        thrd.start();
    }

    // Entry point of thread.
    @Override
    public void run() {
        try {
            // Listens for a connection to be made to
            // this socket and accepts it. The method blocks until
            // a connection is made
            System.out.println("Connect request is accepted...");
            String clientHost = client.getInetAddress().getHostAddress();
            int clientPort = client.getPort();
            System.out.println("Client host = " + clientHost + " Client port = " + clientPort);

            while (true) {
                // Read data from the client
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));

                String msgFromClient = br.readLine();
                System.out.println(String.format("Message received from %s:%d = %s", clientHost, clientPort, msgFromClient));

                // Send response to the client
                
                String[] arrOfMsg = msgFromClient.split(" ", 2);
                
                if (arrOfMsg.length > 1 && !arrOfMsg[1].equalsIgnoreCase("bye")) {
                    for (Socket anotherClient : Server.clients) {
                        if (client != anotherClient) {
                            OutputStream clientOut = anotherClient.getOutputStream();
                            PrintWriter pw = new PrintWriter(clientOut, true);
                            String ansMsg = msgFromClient;
                            pw.println(ansMsg);
                        }
                    }
                }

                // Close sockets
                if (arrOfMsg.length > 1 && arrOfMsg[1].equalsIgnoreCase("bye")) {
                    System.out.println(String.format("client %s:%s closed!", clientHost, clientPort));
                    bttl10_20127473_20127582.Server.clients.remove(client);
                    client.close();
                    break;
                }
            }
        } catch (IOException ie) {
        }
    }
}
