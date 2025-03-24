package sfwe302.assignment8.part1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try {
                    new ClientHandler(serverSocket.accept()).start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try (PrintWriter sockOut = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader sockIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            IceCreamProtocol icp = new IceCreamProtocol();
            sockOut.println(icp.processInput(null));

            String inputLine;
            while ((inputLine = sockIn.readLine()) != null) {
                if (inputLine.toLowerCase().contains("bye")) {
                    break;
                }
                sockOut.println(icp.processInput(inputLine));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}