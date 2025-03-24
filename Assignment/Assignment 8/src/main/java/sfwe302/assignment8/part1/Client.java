package sfwe302.assignment8.part1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String hostName = args[0];
        int port = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(hostName, port);
             PrintWriter client = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader server = new BufferedReader( new InputStreamReader(socket.getInputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            String fromServer, fromUser;

            while ((fromServer = server.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                System.out.print("Client: ");
                fromUser = in.readLine();
                if (fromUser != null) {
                    client.println(fromUser);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

