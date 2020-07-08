package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        ServerSocket ss = new ServerSocket(4999);
        System.out.println("Servers is waiting for connection...");
        Socket s = ss.accept();
        System.out.println("Connection established!");

        // Send data to the client
        PrintStream ps = new PrintStream(s.getOutputStream());

        // Read data from the client
        BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));

        // Read data from the keyboard
        BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));

        // Server executes continuously
        while (true) {
            String userInput, serverInput = "";

            // Read from client
            while ((userInput = bf.readLine()) != null) {
                System.out.println(userInput);

                try {
                    ps.println(parser.parse(userInput));
                } catch (Exception e) {
                    // Send to client
                    ps.println(e.toString());
                }
            }
            // Close connection
            ps.close();
            bf.close();
            keyboardInput.close();
            ss.close();
            s.close();

            // Terminate application
            System.exit(0);
        }
    }
}
