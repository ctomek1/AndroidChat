package com.myapplication;

import com.myapplication.constants.SessionConstants;

import org.json.JSONException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MessageSender {

    Message message;
    Send send = new Send();


    public MessageSender(Message message)
    {
        this.message = message;
    }

    public String SendMessage() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, JSONException, NoSuchPaddingException, InvalidKeyException {
        String messageToSend = send.SendPrivateMessage(SessionConstants.USER_ID, message.getMessage(), message.getReceiverId(), message.getDate());

        return messageToSend;
    }

}
