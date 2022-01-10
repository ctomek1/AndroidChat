package com.myapplication.comunnication;

import android.provider.Settings;
import android.widget.Toast;

import com.myapplication.constants.SessionConstants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Communication {

    Socket socket;
    BufferedReader in;
    PrintWriter output;

    public Socket getSocket() {
        return socket;
    }

    public Communication() throws IOException {

        try {
            socket = new Socket("192.168.0.15", 2137);
            OutputStream out = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //input = new PrintWriter(in);
            output = new PrintWriter(out);
        } catch (Exception e) {
          //  Toast toast = Toast.makeText(SessionConstants.CONTEXT, "Connection failed", Toast.LENGTH_LONG);
           // toast.show();
        }
    }

    public String SendAndReceiveMessage(String messageToSend) {
        String receivedMessage = "";
        if (socket.isConnected()) {
            try {
                output.write(messageToSend);
                output.flush();
              //  output.close();
                //receivedMessage = "{\"";
                //char[] buffer = new char[300];
               // int count = in.read(buffer, 0, 300);
                receivedMessage = in.readLine();





            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return receivedMessage;
    }

}
