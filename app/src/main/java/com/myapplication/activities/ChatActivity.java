package com.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.comunnication.Communication;
import com.myapplication.models.Message;
import com.myapplication.R;
import com.myapplication.comunnication.CreateJSONsWithData;
import com.myapplication.adapters.ChatAdapter;
import com.myapplication.constants.SessionConstants;
import com.myapplication.jsonparser.JsonParse;

import org.json.JSONObject;

import java.util.ArrayList;

import lombok.SneakyThrows;

public class ChatActivity extends AppCompatActivity {

    RecyclerView chatRecyclerView;
    EditText messageBox;
    ChatAdapter chatAdapter;
    Button sendButton;
    Button refreshButton;
    TextView nameChat;

    public ChatActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Message> messagesList = new ArrayList<>();

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setAdapter(chatAdapter);
        messageBox = findViewById(R.id.message);
        sendButton = findViewById(R.id.sendButton);
        refreshButton = findViewById(R.id.refreshButton);
        nameChat = findViewById(R.id.nameChat);

        if (SessionConstants.IS_USER_CHAT) {
            setContentView(R.layout.users_chat);
            nameChat.setText(SessionConstants.CURRENT_RECEIVER_NAME);

            Thread thread = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    Communication communication = new Communication();
                    if (communication.getSocket().isConnected()) {
                        String privateMessages = communication.SendAndReceiveMessage(CreateJSONsWithData.GetAllPrivateMessages(SessionConstants.CURRENT_RECEIVER_ID, SessionConstants.USER_ID));
                        JsonParse.toMessageList(privateMessages, messagesList);
                    }
                }
            });
            thread.start();

        } else {
            setContentView(R.layout.group_chat);
            setAddUserButton();
            nameChat.setText(SessionConstants.CURRENT_GROUP_NAME);

            Thread thread = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    Communication communication = new Communication();
                    if (communication.getSocket().isConnected()) {
                        String groupMessages = communication.SendAndReceiveMessage(CreateJSONsWithData.GetAllGroupMessages(SessionConstants.CURRENT_GROUP_ID));
                        JsonParse.toMessageList(groupMessages, messagesList);
                    }
                }
            });
            thread.start();
        }

        sendButton.setOnClickListener(
                v -> {


                    Thread thread = new Thread(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            Communication communication = new Communication();
                            if (communication.getSocket().isConnected()) {

                                if (SessionConstants.IS_USER_CHAT) {

                                    Message message = new Message();
                                    message.setMessage(messageBox.getText().toString());
                                    message.setAuthorId(SessionConstants.USER_ID);
                                    message.setReceiverId(SessionConstants.CURRENT_RECEIVER_ID);
                                    //     message.setDate(java.time.LocalDate.Now());

                                    String result = communication.SendAndReceiveMessage(CreateJSONsWithData.SendPrivateMessage(SessionConstants.USER_ID, message.getMessage(), message.getReceiverId(), message.getDate()));
                                    JSONObject jsonResult = new JSONObject(result);
                                    Boolean isMessageHasBeenSend = jsonResult.getBoolean("result") == true;

                                    if (isMessageHasBeenSend) {
                                        ArrayList<Message> newMessages = new ArrayList<>();
                                        newMessages.add(message);

                                        chatAdapter = new ChatAdapter(SessionConstants.CONTEXT, newMessages);
                                    }

                                } else {

                                    Message message = new Message();
                                    message.setMessage(messageBox.getText().toString());
                                    message.setAuthorId(SessionConstants.USER_ID);
                                    message.setReceiverId(SessionConstants.CURRENT_GROUP_ID);
                                    //     message.setDate(java.time.LocalDate.Now());

                                    String result = communication.SendAndReceiveMessage(CreateJSONsWithData.SendGroupMessage(SessionConstants.USER_ID, message.getMessage(), message.getReceiverId(), message.getDate()));
                                    JSONObject jsonResult = new JSONObject(result);
                                    Boolean isMessageHasBeenSend = jsonResult.getBoolean("result") == true;

                                    if (isMessageHasBeenSend) {
                                        ArrayList<Message> newMessages = new ArrayList<>();
                                        newMessages.add(message);

                                        chatAdapter = new ChatAdapter(SessionConstants.CONTEXT, newMessages);
                                    }
                                }


                            }
                        }
                    });
                    thread.start();
                });

        refreshButton.setOnClickListener(
                v -> {
                    ArrayList<Message> newMessage = new ArrayList<>();

                    Thread thread = new Thread(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            Communication communication = new Communication();
                            if (communication.getSocket().isConnected()) {
                                if (SessionConstants.IS_USER_CHAT) {

                                    String result = communication.SendAndReceiveMessage(CreateJSONsWithData.GetRecentPrivateMessage(SessionConstants.CURRENT_RECEIVER_ID, SessionConstants.USER_ID));
                                    Message message = new Message();
                                    JsonParse.toRecentPrivateMessage(result, message);
                                    newMessage.add(message);

                                } else {

                                    String result = communication.SendAndReceiveMessage(CreateJSONsWithData.GetRecentGroupMessage(SessionConstants.CURRENT_GROUP_ID));
                                    Message message = new Message();
                                    JsonParse.toRecentGroupMessage(result, message);
                                    newMessage.add(message);
                                }

                                chatAdapter = new ChatAdapter(SessionConstants.CONTEXT, newMessage);
                            }
                        }
                    });
                    thread.start();
                });

    }

    private void setAddUserButton() {

        Button addUserButton = findViewById((R.id.addUserButton));
        addUserButton.setOnClickListener(
                v -> {
                    startActivity(new Intent(getApplicationContext(), AddUserToGroupActivity.class));
                });
    }

}
