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

    public MessageSender(Message message)
    {
        this.message = message;
    }

    public String SendMessage() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, JSONException, NoSuchPaddingException, InvalidKeyException {
        String messageToSend = Send.SendPrivateMessage(SessionConstants.USER_ID, message.getMessage(), message.getReceiverId(), message.getDate());

        return messageToSend;
    }

}
