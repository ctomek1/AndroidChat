package com.myapplication;

import java.util.Calendar;
import java.util.Date;

public class Message {
    String message;
    int id;
    Date dateOfSend;
    Calendar calendar = Calendar.getInstance();

    public Message(String message) {
        this.message = message;
        this.id = 1;
        this.dateOfSend = calendar.getTime();
    };

    public Message(String message, int id) {
        this.message = message;
        this.id = id;
        this.dateOfSend = calendar.getTime();
    };


}


