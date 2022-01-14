package com.myapplication.comunnication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Communication {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter output;

    public Socket getSocket() {
        return socket;
    }

    public Communication() throws IOException {

        try {
            socket = new Socket("192.168.0.15", 2137);
            OutputStream out = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(out);
        } catch (Exception e) {
            throw e;
        }
    }

    public String SendAndReceiveMessage(String messageToSend) {
        String receivedMessage = "";
        if (socket.isConnected()) {
            try {
                output.write(messageToSend);
                output.flush();
                receivedMessage = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return receivedMessage;
    }

}
