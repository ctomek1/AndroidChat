package com.myapplication.models;


import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Message {
    private String message;
    private Integer authorId;
    private Integer receiverId;
    private Date date;
}


