package com.myapplication;

import org.json.JSONException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MessageReceiver {

    Message message;
    Send send = new Send();

    public MessageReceiver()
    {
    }

    public String ReceiveMessage() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, JSONException, NoSuchPaddingException, InvalidKeyException {
        String messagesReceived = send.GetAllPrivateMessages(message.getReceiverId(), message.getAuthorId());

        return messagesReceived;
    }

}