package com.myapplication;

import com.myapplication.Send;

import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.net.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Client {

    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;
    private final static String AES_KEY = "p3s6v8y/B?E(H+MbQeThWmZq4t7w!z$C";
    private final static byte[] KEY_IN_BYTES = AES_KEY.getBytes();

    public Client(Socket socket)
    {
        try {
            clientSocket = socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {}
    }

    public void sendMessage(String message)
    {
        try
        {
            while (clientSocket.isConnected())
            {
                out.write(message);
                out.newLine();
            }
        } catch (Exception e) {}
    }



    public void getMessage()
    {
        try
        {
            String msgFromChat = in.readLine();
            byte[] messageReceivedInByte = msgFromChat.getBytes();
            msgFromChat = Decryptor(messageReceivedInByte, KEY_IN_BYTES).toString();
            JSONObject jsonResult = new JSONObject(msgFromChat);
            System.out.println((String) jsonResult.get("id"));
            System.out.println((String) jsonResult.get("login"));
            System.out.println((String) jsonResult.get("password"));

        } catch (Exception e) {}
    }


    public byte[] Decryptor(byte[] encryptedMessage, byte[] keyBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedMessage);
    }

}