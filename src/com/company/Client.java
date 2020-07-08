package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {
        Connection con = new Connection();
        String userInput;
        Parser parser = new Parser();

        // Read data from the keyboard
        BufferedReader keyboardInput1 = new BufferedReader(new InputStreamReader(System.in));

        // Repeat as long as exit
        while(!(userInput = keyboardInput1.readLine()).equals("exit")) {
            if (con.checkConnection(userInput).equals("connect")) {
                Socket s;
                try {
                    s = new Socket("localhost", 4999);
                } catch (Exception e) {
                    throw new Exception("Cannot connect with this server.");
                }

                // Send data to the server
                DataOutputStream os = new DataOutputStream(s.getOutputStream());

                // Read data coming from the server
                BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));

                String serverResponse;

                // Repeat as long as disconnect
                while (!(userInput = keyboardInput1.readLine()).equals("disconnect")) {
                    // Send to the server
                    os.writeBytes(userInput + "\n");

                    // Receive from the server
                    serverResponse = bf.readLine();
                    if(serverResponse != null && !serverResponse.isEmpty()) {
                        System.out.println(serverResponse);
                    }
                }

                // Close connection.
                os.close();
                bf.close();
                s.close();
            } else {
                try {
                    String result = parser.parse(userInput);

                    if (result != null && !result.isEmpty()) {
                        System.out.println(result);
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        }

        keyboardInput1.close();
    }
}
