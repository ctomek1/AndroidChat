package com.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ChatActivity extends AppCompatActivity {

    RecyclerView chatRecyclerView;
    EditText messageBox;
    ChatAdapter chatAdapter;
    ArrayList<Message> messageList;
    ImageView sendButton;
    MessageSender messageSender;
    MessageReceiver messageReceiver;
    Socket socket;
    BufferedReader in;
    BufferedWriter out;
    ArrayList<Message> messagesList = new ArrayList<Message>();

    public ChatActivity(Socket socket)
    {
        this.socket = socket;
    }

    public ChatActivity() throws IOException {
        socket = new Socket("ip", 0000);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageBox = findViewById(R.id.message);
        messageList = new ArrayList<Message>();
        chatAdapter = new ChatAdapter(this, messageList);
        sendButton = findViewById(R.id.imageView);

        sendButton.setOnClickListener(
                v -> {
                    Message message = new Message(messageBox.getText().toString());
                    messagesList.add(message);
                    messageSender = new MessageSender(message);
                    messageReceiver = new MessageReceiver();
                    String chatMessage = null;
                    try {
                        chatMessage = messageSender.SendMessage();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    }
                    if(socket.isConnected())
                    {
                        String messageToSend = chatMessage;
                        try {
                            out.write(messageToSend);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
