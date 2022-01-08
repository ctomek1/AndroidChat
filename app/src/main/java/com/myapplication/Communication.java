package com.myapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Communication {

    Socket socket;
    BufferedReader in;
    PrintWriter output;
    BufferedWriter out;

    public Communication() throws IOException {

        try {
            socket = new Socket("192.168.0.15", 2137);
            OutputStream out = socket.getOutputStream();
            output = new PrintWriter(out);
            //this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch(Exception e){
            throw e;
        }
    }

    public String SendAndReceiveMessage(String messageToSend) {
        String receivedMessage = "";
        if(socket.isConnected())
        {
            try {
               // socket.se

                output.write(messageToSend);
                output.flush();
                receivedMessage = in.readLine();
               int i = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return receivedMessage;
    }

}
