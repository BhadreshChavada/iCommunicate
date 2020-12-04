package com.icommunicate.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.icommunicate.R;
import com.icommunicate.adapter.ChatRecyclerAdapter;
import com.icommunicate.adapter.FcmChat;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiTwilioFetchMessage;
import com.icommunicate.apiCall.requestCall.ApiTwilioSendMessage;
import com.icommunicate.apiCall.requestModels.FetchMessageRequest;
import com.icommunicate.apiCall.requestModels.SendMessageRequest;
import com.icommunicate.apiCall.responseModels.DefaultResponse;
import com.icommunicate.apiCall.responseModels.FetchMessageItem;
import com.icommunicate.apiCall.responseModels.FetchMessageResponse;
import com.icommunicate.bean.MessageItem;
import com.icommunicate.chatModule.HeaderItem;
import com.icommunicate.common.DateUtil;
import com.icommunicate.common.IntentUtils;
import com.icommunicate.common.KeyboardUtils;
import com.icommunicate.common.preferences.PreferenceUtil;
import com.icommunicate.twillio.Constants;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends AppCompatActivity {

    SwipeRefreshLayout swipe_refresh;
    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;
    @BindView(R.id.recycler_view_chat)
    RecyclerView recyclerViewChat;
    @BindView(R.id.edit_text_message)
    EditText editTextMessage;
    @BindView(R.id.send_msg_icon)
    ImageButton sendMsgIcon;
    @BindView(R.id.action_bar_back)
    LinearLayout actionBarBack;
    @BindView(R.id.custom_search_edttext)
    AppCompatEditText customSearchEdttext;
    private ChatRecyclerAdapter mChatRecyclerAdapter;
    ArrayList<FcmChat> fcmChatArrayList = new ArrayList<>();
    String contactNumber = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main_layout);
        ButterKnife.bind(this);

        swipe_refresh = (SwipeRefreshLayout)this.findViewById(R.id.swipe_container);



        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Loading more data..
                callFetchMessage();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getStringExtra("phoneNumber") != null) {
            contactNumber = getIntent().getStringExtra("phoneNumber");
        }
        actionBarTitle.setText(contactNumber);
        generateDummyData();
        callFetchMessage();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentUtils.finishActivity(MessageActivity.this);

    }

    private void generateDummyData() {
        actionBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sendMsgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = editTextMessage.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    Toast.makeText(MessageActivity.this, "Please enter valid message", Toast.LENGTH_SHORT).show();
                    return;
                }
                SendMessageRequest sendMessageRequest = new SendMessageRequest();
                sendMessageRequest.setTo(contactNumber);
                sendMessageRequest.setFrom(PreferenceUtil.byDefultDailNumber().get());
                sendMessageRequest.setBody(messageText);
                callSendMessage(sendMessageRequest);
            }
        });
        KeyboardVisibilityEvent.setEventListener(
                MessageActivity.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            if (mChatRecyclerAdapter != null) {
                                recyclerViewChat.smoothScrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);
                            }
                        }
                    }
                });
    }

    private void insertTextInstant(FcmChat fcmChat) {
        if (mChatRecyclerAdapter == null) {
            mChatRecyclerAdapter = new ChatRecyclerAdapter(MessageActivity.this, new ArrayList<FcmChat>());
            recyclerViewChat.setAdapter(mChatRecyclerAdapter);
        }
        mChatRecyclerAdapter.add(fcmChat);
        recyclerViewChat.smoothScrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);
        editTextMessage.setText("");
    }

    public void setUpAdapter(ArrayList<FcmChat> fcmChats) {
        if (fcmChats.size() > 0) {
            mChatRecyclerAdapter = new ChatRecyclerAdapter(MessageActivity.this, getChatHistoryWithHeaders(fcmChats));
            StickyLayoutManager layoutManager = new StickyLayoutManager(MessageActivity.this, mChatRecyclerAdapter);
            layoutManager.elevateHeaders(true);
            recyclerViewChat.setLayoutManager(layoutManager);
            recyclerViewChat.setAdapter(mChatRecyclerAdapter);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerViewChat.smoothScrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);
                }
            }, 1);
        } else {

        }

    }

    public ArrayList<FcmChat> getChatHistoryWithHeaders(ArrayList<FcmChat> datas) {
        ArrayList<FcmChat> dataComplete = new ArrayList<>();
        ArrayList<String> HeadersData = new ArrayList<>();
        ListMultimap<String, FcmChat> multimap = ArrayListMultimap.create();
        for (int i = 0; i < datas.size(); i++) {
            HeadersData.add(getDate(datas.get(i).getTimestamp()));
            multimap.put(getDate(datas.get(i).getTimestamp()), datas.get(i));
        }
        ArrayList<String> hearders = new ArrayList<String>(new LinkedHashSet<String>(HeadersData));
        for (int i = 0; i < hearders.size(); i++) {
            dataComplete.add(getChatASHeader(hearders.get(i).toString()));
            dataComplete.addAll(multimap.get(hearders.get(i)));

        }
        return dataComplete;
    }

    public String getDate(long timestamp) {
        long Yesterday = new Date().getTime() - 24 * 60 * 60 * 1000;
        timestamp = timestamp * 1000;
        if (DateUtils.isToday(timestamp)) {
            return "Today";
        } else if (DateUtils.formatDateTime(MessageActivity.this, Yesterday, DateUtils.FORMAT_SHOW_YEAR).equalsIgnoreCase(DateUtils.formatDateTime(MessageActivity.this, timestamp, DateUtils.FORMAT_SHOW_YEAR))) {
            return "Yesterday";
        } else {
            return DateUtil.getChatHeader(timestamp);
        }
    }

    public HeaderItem getChatASHeader(String date) {
        return new HeaderItem(Constants.MESSAGE_TYPE_HEADER, "", "", "", date, 11111);
    }

    private void addMessages(List<FetchMessageItem> arrayList) {
        fcmChatArrayList.clear();
        if (arrayList != null) {
            arrayList = arrayList.stream().filter(fetchMessageItem -> fetchMessageItem.getTo().equalsIgnoreCase(contactNumber)).collect(Collectors.toList());
            for (int i = 0; i < arrayList.size(); i++) {
                FetchMessageItem fetchMessageItem = arrayList.get(i);
                if (fetchMessageItem != null && fetchMessageItem.getBody() != null)
                    fetchMessageItem.setBody(fetchMessageItem.getBody().replace("Sent from your Twilio trial account - ", ""));
                fcmChatArrayList.add(new FcmChat(Constants.MESSAGE_TYPE_MESSAGE, fetchMessageItem.getTo(), fetchMessageItem.getFrom(), "", fetchMessageItem.getBody(), DateUtil.setDateSent(fetchMessageItem.getDateSent().getDate())));
            }
            Collections.reverse(fcmChatArrayList);
            setUpAdapter(fcmChatArrayList);
        }
        customSearchEdttext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mChatRecyclerAdapter != null && mChatRecyclerAdapter.getFilter() != null) {
                    mChatRecyclerAdapter.getFilter().filter(s.toString().trim());
                }
            }
        });
    }

    //Call Message api
    private void callSendMessage(SendMessageRequest sendMessageRequest) {
        ApiTwilioSendMessage apiTwilioSendMessage = new ApiTwilioSendMessage(MessageActivity.this, new IResult() {
            @Override
            public void notifySuccess(String requestType, Object response) {
                if (response instanceof DefaultResponse) {
                    DefaultResponse defaultResponse = (DefaultResponse) response;
                    if (!defaultResponse.isError()) {
                        editTextMessage.setText("");
                        KeyboardUtils.hideKeyboard(MessageActivity.this);
                        callFetchMessage();

                    } else {
                        Toast.makeText(MessageActivity.this, "" + defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void notifyNetworkSuccess(String requestType) {

            }
        });
        apiTwilioSendMessage.execute(sendMessageRequest);
        swipe_refresh.setRefreshing(false);
    }

    private void callFetchMessage() {
        FetchMessageRequest fetchMessageRequest = new FetchMessageRequest();
        fetchMessageRequest.setTo(contactNumber);
        fetchMessageRequest.setFrom(PreferenceUtil.byDefultDailNumber().get());
        ApiTwilioFetchMessage apiTwilioFetchMessage = new ApiTwilioFetchMessage(MessageActivity.this, new IResult() {
            @Override
            public void notifySuccess(String requestType, Object response) {
                if (response instanceof FetchMessageResponse) {
                    FetchMessageResponse defaultResponse = (FetchMessageResponse) response;
                    if (!defaultResponse.isError()) {
                        addMessages(defaultResponse.getData());
                    } else {
                        Toast.makeText(MessageActivity.this, "" + defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void notifyNetworkSuccess(String requestType) {

            }
        });
        apiTwilioFetchMessage.execute(fetchMessageRequest);
        swipe_refresh.setRefreshing(false);

    }


    @OnClick(R.id.action_bar_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
