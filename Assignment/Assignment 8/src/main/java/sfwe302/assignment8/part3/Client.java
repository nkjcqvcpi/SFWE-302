package sfwe302.assignment8.part3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONArray;

public class Client {
    public static void main(String[] args) {
        String hostName = args[0];
        int port = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(hostName, port);
             PrintWriter client = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            String fromServer, fromUser;

            while ((fromServer = server.readLine()) != null) {
                try {
                    if (fromServer.trim().startsWith("[")) {
                        JSONArray ja = new JSONArray(fromServer);
                        System.out.println("Number of universities: " + ja.length());
                        System.out.println("List of universities: ");
                        for (int i = 0; i < ja.length(); i++) {
                            System.out.println(ja.getJSONObject(i).get("name"));
                        }
                    } else {
                        System.out.println("Server: " + fromServer);
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    System.out.println("Server: " + fromServer);
                }
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
