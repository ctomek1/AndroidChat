package com.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.R;
import com.myapplication.adapters.ChatAdapter;
import com.myapplication.comunnication.Communication;
import com.myapplication.comunnication.CreateJSONsWithData;
import com.myapplication.constants.SessionConstants;
import com.myapplication.dialog.AlertDialogClass;
import com.myapplication.jsonparser.JsonParse;
import com.myapplication.models.Message;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageBox;
    private ChatAdapter chatAdapter;
    private Button sendButton;
    private Button refreshButton;
    private TextView nameChat;
    private Context context;
    private ArrayList<Message> messagesList = new ArrayList<>();

    public ChatActivity() {
        this.context = this;
    }

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SessionConstants.IS_USER_CHAT) {
            setContentView(R.layout.users_chat);
        } else {
            setContentView(R.layout.group_chat);
        }

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setHasFixedSize(true);

        messageBox = findViewById(R.id.message);
        sendButton = findViewById(R.id.sendButton);
        refreshButton = findViewById(R.id.refreshButton);
        nameChat = findViewById(R.id.nameChat);

        if (SessionConstants.IS_USER_CHAT) {
            nameChat.setText(SessionConstants.CURRENT_RECEIVER_NAME);

            Thread thread = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    Communication communication = new Communication();
                    if (communication.getSocket().isConnected()) {
                        String privateMessages = communication.SendAndReceiveMessage(CreateJSONsWithData.GetAllPrivateMessages(SessionConstants.CURRENT_RECEIVER_ID, SessionConstants.USER_ID));
                        JsonParse.toMessageList(privateMessages, messagesList);
                    } else {
                        openAlertDialog(getResources().getString(R.string.connectionFailed), getResources().getString(R.string.failure));
                    }
                }
            });
            thread.start();

        } else {
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

                    } else {
                        openAlertDialog(getResources().getString(R.string.connectionFailed), getResources().getString(R.string.failure));
                    }
                }
            });
            thread.start();
        }
       // TimeUnit.MILLISECONDS.sleep(100);
        setMessagesToAdapter();
        chatRecyclerView.smoothScrollToPosition(messagesList.size());

        sendButton.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {

                Message message = new Message();
                message.setMessage(messageBox.getText().toString());
                messageBox.setText("");
                message.setAuthorId(SessionConstants.USER_ID);
                message.setDate(Calendar.getInstance().getTime());

                Thread thread = new Thread(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        Communication communication = new Communication();
                        if (communication.getSocket().isConnected()) {

                            if (SessionConstants.IS_USER_CHAT) {

                                message.setReceiverId(SessionConstants.CURRENT_RECEIVER_ID);

                                String result = communication.SendAndReceiveMessage(CreateJSONsWithData.SendPrivateMessage(SessionConstants.USER_ID, message.getMessage(), message.getReceiverId(), message.getDate()));
                                JSONObject jsonResult = new JSONObject(result);

                                if (jsonResult.getBoolean("result")) {
                                    messagesList.add(message);
                                    chatAdapter.notifyItemRangeChanged(0, messagesList.size());
                                    chatRecyclerView.smoothScrollToPosition(messagesList.size());
                                } else {

                                    openAlertDialog(v.getResources().getString(R.string.sendingMessageFailed), v.getResources().getString(R.string.failure));
                                }

                            } else {

                                message.setReceiverId(SessionConstants.CURRENT_GROUP_ID);

                                String result = communication.SendAndReceiveMessage(CreateJSONsWithData.SendGroupMessage(SessionConstants.USER_ID, message.getMessage(), message.getReceiverId(), message.getDate()));
                                JSONObject jsonResult = new JSONObject(result);

                                if (jsonResult.getBoolean("result")) {
                                    messagesList.add(message);
                                    chatAdapter.notifyItemRangeChanged(0, messagesList.size());
                                    chatRecyclerView.smoothScrollToPosition(messagesList.size());
                                } else {

                                    openAlertDialog(v.getResources().getString(R.string.sendingMessageFailed), v.getResources().getString(R.string.failure));
                                }
                            }
                        } else {
                            openAlertDialog(getResources().getString(R.string.connectionFailed), getResources().getString(R.string.failure));
                        }
                    }
                });
                thread.start();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {

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
                                if (!isMessageLatest(message)) {
                                    messagesList.add(message);
                                    chatAdapter.notifyItemRangeChanged(0, messagesList.size());
                                }

                            } else {

                                String result = communication.SendAndReceiveMessage(CreateJSONsWithData.GetRecentGroupMessage(SessionConstants.CURRENT_GROUP_ID));
                                Message message = new Message();
                                JsonParse.toRecentGroupMessage(result, message);
                                if (!isMessageLatest(message)) {
                                    messagesList.add(message);
                                    chatAdapter.notifyItemRangeChanged(0, messagesList.size());
                                }
                            }
                            chatRecyclerView.smoothScrollToPosition(messagesList.size());
                        } else {
                            openAlertDialog(v.getResources().getString(R.string.connectionFailed), v.getResources().getString(R.string.failure));
                        }
                    }
                });
                thread.start();
            }
        });
    }

    private void setAddUserButton() {

        Button addUserButton = findViewById((R.id.addUserButton));
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), AddUserToGroupActivity.class));
            }
        });
    }

    @SneakyThrows
    private void setMessagesToAdapter() {
        TimeUnit.MILLISECONDS.sleep(1000);
        chatAdapter = new ChatAdapter(this, messagesList);
        chatRecyclerView.setAdapter(chatAdapter);
    }

    private Boolean isMessageLatest(Message message) {

        Message latestMessage = messagesList.get(messagesList.size() - 1);
        if (message == latestMessage) {
            return true;
        } else {
            return false;
        }
    }

    private void openAlertDialog(String message, String title) {

        AlertDialogClass alertDialogClass = new AlertDialogClass(message, title);
        alertDialogClass.show(getSupportFragmentManager(), "AlertDialogCreator");
    }
}
