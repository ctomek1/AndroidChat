package com.myapplication;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.adapters.ChatAdapter;

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
    ArrayList<Message> messagesList = new ArrayList<>();

    public ChatActivity(Socket socket)
    {
        this.socket = socket;
    }

    public ChatActivity() throws IOException {
        socket = new Socket("192.168.0.15", 2137);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setAdapter(chatAdapter);
        messageBox = findViewById(R.id.message);
        messageList = new ArrayList<>();
        sendButton = findViewById(R.id.imageView);
        sendButton.setClickable(true);

       sendButton.setOnClickListener(
            v -> {
                Message message = new Message();
                message.setMessage(messageBox.getText().toString());
                messagesList.add(message);
                messageSender = new MessageSender(message);
                messageReceiver = new MessageReceiver();
                String chatMessage = null;
                try {
                    chatMessage = messageSender.SendMessage();
                } catch (BadPaddingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | JSONException | IllegalBlockSizeException e) {
                    e.printStackTrace();
                }

                if(socket.isConnected())
                {
                    try {
                        out.write(chatMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                chatAdapter = new ChatAdapter(this, messageList);
            });

    }
}
