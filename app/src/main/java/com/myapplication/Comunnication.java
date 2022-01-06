package com.myapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Comunnication {

    Socket socket;
    BufferedReader in;
    BufferedWriter out;

    public Comunnication() throws IOException {
        socket = new Socket("ip", 0);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public String SendAndReceiveMessage(String messageToSend) {
        String receivedMessage = "";
        if(socket.isConnected())
        {
            try {
                out.write(messageToSend);
                receivedMessage = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return receivedMessage;
    }

}
