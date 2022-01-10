package com.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.Communication;
import com.myapplication.Message;
import com.myapplication.MessageReceiver;
import com.myapplication.MessageSender;
import com.myapplication.R;
import com.myapplication.Send;
import com.myapplication.adapters.AddUserToGroupAdapter;
import com.myapplication.adapters.ChatAdapter;
import com.myapplication.constants.SessionConstants;
import com.myapplication.json_parser.JsonParse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import lombok.SneakyThrows;

public class ChatActivity extends AppCompatActivity {

    RecyclerView chatRecyclerView;
    EditText messageBox;
    ChatAdapter chatAdapter;
    Button sendButton;
    Button refreshButton;
    MessageSender messageSender;
    MessageReceiver messageReceiver;

    public ChatActivity() {
    }

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Communication communication = new Communication();

        ArrayList<Message> messagesList = new ArrayList<>();

        if (SessionConstants.IS_USER_CHAT) {
            setContentView(R.layout.users_chat);
            String privateMessages = communication.SendAndReceiveMessage(Send.GetAllPrivateMessages(SessionConstants.CURRENT_RECEIVER_ID, SessionConstants.USER_ID));
            JsonParse.toPrivateMessageList(privateMessages, messagesList);

        } else {
            setContentView(R.layout.group_chat);
            setAddUserButton();
            String groupMessages = communication.SendAndReceiveMessage(Send.GetAllGroupMessages(SessionConstants.CURRENT_GROUP_ID));
            JsonParse.toPrivateMessageList(groupMessages, messagesList);
        }

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setAdapter(chatAdapter);
        messageBox = findViewById(R.id.message);
        sendButton = findViewById(R.id.sendButton);
        refreshButton = findViewById((R.id.refreshButton));

        sendButton.setOnClickListener(
                v -> {
                    Boolean isMessageHasBeenSend = false;
                    String result = communication.SendAndReceiveMessage(messageSender.SendMessage());
                    JSONObject jsonResult = new JSONObject(result);
                    isMessageHasBeenSend = jsonResult.getBoolean("result") == true;

                    if (isMessageHasBeenSend) {
                        Message message = new Message();
                        message.setMessage(messageBox.getText().toString());
                        message.setAuthorId(SessionConstants.USER_ID);
                        message.setReceiverId(SessionConstants.CURRENT_RECEIVER_ID);
                        //     message.setDate(java.time.LocalDate.Now());

                        ArrayList<Message> newMessages = new ArrayList<>();
                        newMessages.add(message);

                        messageSender = new MessageSender(message);
                        messageReceiver = new MessageReceiver();

                        chatAdapter = new ChatAdapter(this, newMessages);
                    }
                });

        refreshButton.setOnClickListener(
                v -> {
                    ArrayList<Message> newMessage = new ArrayList<>();

                    if (SessionConstants.IS_USER_CHAT) {
                        String result = communication.SendAndReceiveMessage(Send.GetRecentPrivateMessage(SessionConstants.CURRENT_RECEIVER_ID, SessionConstants.USER_ID));
                        JSONObject jsonResult = new JSONObject(result);
                        newMessage.add(getPrivateMessage(jsonResult));

                    } else {
                        String result = communication.SendAndReceiveMessage(Send.GetRecentGroupMessage(SessionConstants.CURRENT_GROUP_ID));
                        JSONObject jsonResult = new JSONObject(result);
                        newMessage.add(getGroupMessage(jsonResult));
                    }

                    chatAdapter = new ChatAdapter(this, newMessage);
                });

    }

    private void setAddUserButton() {

        Button addUserButton = findViewById((R.id.addUserButton));
        addUserButton.setOnClickListener(
                v -> {
                    startActivity(new Intent(getApplicationContext(), AddUserToGroupActivity.class));
                });
    }

    private Message getGroupMessage(JSONObject jsonResult) throws JSONException {

        Message message = new Message();

        message.setAuthorId(jsonResult.getInt("groupId"));
        message.setMessage(jsonResult.getString("message"));
        message.setDate((Date) jsonResult.get("date"));
        message.setReceiverId(SessionConstants.USER_ID);

        return message;
    }

    private Message getPrivateMessage(JSONObject jsonResult) throws JSONException {

        Message message = new Message();

        message.setAuthorId(jsonResult.getInt("receiverId"));
        message.setMessage(jsonResult.getString("message"));
        message.setDate((Date) jsonResult.get("date"));
        message.setReceiverId(SessionConstants.USER_ID);

        return message;
    }
}
