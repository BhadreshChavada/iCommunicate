package com.icommunicate.fragment.subFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.icommunicate.R;
import com.icommunicate.activity.MessageActivity;
import com.icommunicate.adapter.MessagesAdapter;
import com.icommunicate.adapterActions.MessageAdapterListener;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiTwilioFetchMessage;
import com.icommunicate.apiCall.requestModels.FetchMessageRequest;
import com.icommunicate.apiCall.responseModels.FetchMessageItem;
import com.icommunicate.apiCall.responseModels.FetchMessageResponse;
import com.icommunicate.bean.MessageItem;
import com.icommunicate.common.IntentUtils;
import com.icommunicate.common.preferences.PreferenceUtil;
import com.icommunicate.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentMessage extends BaseFragment {

    SwipeRefreshLayout swipe_refresh;
    final public static String TAG = FragmentMessage.class.getName();
    protected View root;
    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;
    @BindView(R.id.msg_recycle)
    RecyclerView msgRecycle;
    MessagesAdapter messagesAdapter;
    @BindView(R.id.custom_search_edttext)
    AppCompatEditText customSearchEdttext;

    public FragmentMessage() {
    }

    public static Fragment newInstance(Context context, String page_name, int count) {
        FragmentMessage f = new FragmentMessage();
        Bundle bundle = new Bundle();
        bundle.putString("page_name", page_name);
        bundle.putInt("count", count);
        f.setArguments(bundle);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_message_list, container, false);
        ButterKnife.bind(this, root);
        getBundleArguments();

        swipe_refresh = (SwipeRefreshLayout)root.findViewById(R.id.swipe_container);



        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Loading more data..
                callFetchMessage();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        callFetchMessage();
    }

    private void setUpAdapter(List<MessageItem> messageItemList) {
        messagesAdapter = new MessagesAdapter(getContext(), messageItemList, new MessageAdapterListener() {
            @Override
            public void selected(MessageItem messageItem, int position) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("phoneNumber", messageItemList.get(position).getContactName());

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });
        msgRecycle.setAdapter(messagesAdapter);
        msgRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        messagesAdapter.notifyDataSetChanged();
        customSearchEdttext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (messagesAdapter != null && messagesAdapter.getFilter() != null) {
                    messagesAdapter.getFilter().filter(s.toString().trim());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getBundleArguments() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.containsKey("page_name")) {
                try {
                    actionBarTitle.setText(bundle.getString("page_name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void callFetchMessage() {
        FetchMessageRequest fetchMessageRequest = new FetchMessageRequest();
        fetchMessageRequest.setTo("+0000");
        fetchMessageRequest.setFrom(PreferenceUtil.byDefultDailNumber().get());
        ApiTwilioFetchMessage apiTwilioFetchMessage = new ApiTwilioFetchMessage(getContext(), new IResult() {
            @Override
            public void notifySuccess(String requestType, Object response) {
                if (response instanceof FetchMessageResponse) {
                    FetchMessageResponse defaultResponse = (FetchMessageResponse) response;
                    List<MessageItem> messageItemList = new ArrayList<>();
                    if (defaultResponse.getData().size() > 0) {
                        for (int i = 0; i < defaultResponse.getData().size(); i++) {
                            FetchMessageItem fetchMessageItem = defaultResponse.getData().get(i);
                            messageItemList.add(new MessageItem(fetchMessageItem.getTo(), fetchMessageItem.getBody(), fetchMessageItem.getDateSent().getDate()));
                        }
                        messageItemList = messageItemList.stream().filter(distinctByKey(MessageItem::getContactName)).collect(Collectors.toList());
                        swipe_refresh.setRefreshing(false);
                        setUpAdapter(messageItemList);
                    }

                }
            }

            @Override
            public void notifyNetworkSuccess(String requestType) {

            }
        });
        apiTwilioFetchMessage.execute(fetchMessageRequest);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
