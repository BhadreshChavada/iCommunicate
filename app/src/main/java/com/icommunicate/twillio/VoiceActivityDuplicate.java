package com.icommunicate.twillio;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.icommunicate.BuildConfig;
import com.icommunicate.R;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiCallRecords;
import com.icommunicate.apiCall.requestModels.CallRecordingRequest;
import com.icommunicate.common.IntentUtils;
import com.koushikdutta.ion.Ion;
import com.twilio.voice.Call;
import com.twilio.voice.CallException;
import com.twilio.voice.CallInvite;
import com.twilio.voice.ConnectOptions;
import com.twilio.voice.RegistrationException;
import com.twilio.voice.RegistrationListener;
import com.twilio.voice.Voice;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VoiceActivityDuplicate extends AppCompatActivity {

    private static final String TAG = "VoiceActivityDuplicate";
    private static final String identity = "alice";
    /*x
     * You must provide the URL to the publicly accessible Twilio access token server route
     *
     * For example: https://myurl.io/accessToken
     *
     * If your token server is written in PHP, TWILIO_ACCESS_TOKEN_SERVER_URL needs .php extension at the end.
     *
     * For example : https://myurl.io/accessToken.php
     */
    private static final String TWILIO_ACCESS_TOKEN_SERVER_URL = "http://icommunicate.electromech.ie/accessToken.php";
//    private static final String TWILIO_ACCESS_TOKEN_SERVER_URL = "http://3fb84be0.ngrok.io/accessToken.php";

    private static final int MIC_PERMISSION_REQUEST_CODE = 1;
    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;
    @BindView(R.id.phoneNumber)
    AppCompatTextView phoneNumber;
    @BindView(R.id.contactName)
    AppCompatTextView contactName;
    @BindView(R.id.record_action_fab)
    FloatingActionButton recordCall;
    @BindView(R.id.userProfile)
    ImageView userProfile;

    private String accessToken;
    private AudioManager audioManager;
    private int savedAudioMode = AudioManager.MODE_INVALID;

    private boolean isReceiverRegistered = false;
    private VoiceBroadcastReceiver voiceBroadcastReceiver;

    // Empty HashMap, never populated for the Quickstart
    HashMap<String, String> params = new HashMap<>();

    private LinearLayout coordinatorLayout;
    private FloatingActionButton hangupActionFab;
    private FloatingActionButton holdActionFab;
    private FloatingActionButton muteActionFab;
    private FloatingActionButton speakerActionFab;
    private Chronometer chronometer;

    private NotificationManager notificationManager;
    private AlertDialog alertDialog;
    private CallInvite activeCallInvite;
    private Call activeCall;
    private int activeCallNotificationId;

    RegistrationListener registrationListener = registrationListener();
    Call.Listener callListener = callListener();
    private String txtPhoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_duplicate_duplicate);
        ButterKnife.bind(this);


        // These flags ensure that the activity can be launched when the screen is locked.
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        coordinatorLayout = findViewById(R.id.coordinator_layout);
        hangupActionFab = findViewById(R.id.hangup_action_fab);
        holdActionFab = findViewById(R.id.hold_action_fab);
        muteActionFab = findViewById(R.id.mute_action_fab);
        speakerActionFab = findViewById(R.id.speaker_action_fab);
        chronometer = findViewById(R.id.chronometer);

        hangupActionFab.setOnClickListener(hangupActionFabClickListener());
        holdActionFab.setOnClickListener(holdActionFabClickListener());
        muteActionFab.setOnClickListener(muteActionFabClickListener());
        speakerActionFab.setOnClickListener(speakerActionFabClickListener());
        recordCall.setOnClickListener(recordActionFabClickListener());

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /*
         * Setup the broadcast receiver to be notified of FCM Token updates
         * or incoming call invite in this Activity.
         */
        voiceBroadcastReceiver = new VoiceBroadcastReceiver();
        registerReceiver();

        /*
         * Needed for setting/abandoning audio focus during a call
         */
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setSpeakerphoneOn(true);

        /*
         * Enable changing the volume using the up/down keys during a conversation
         */
        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);

        /*
         * Setup the UI
         */
        resetUI();

        /*
         * Displays a call dialog if the intent contains a call invite
         */
        handleIncomingCallIntent(getIntent());

        /*
         * Ensure the microphone permission is enabled
         */
        if (!checkPermissionForMicrophone()) {
            requestPermissionForMicrophone();
        } else {
            retrieveAccessToken();
        }

        // Record record = new Record();
       /* for(com.twilio.rest.api.v2010.account.Call record : calls) {
            System.out.println(record.getSid());
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBarTitle.setText("Dailing");
        registerReceiver();
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    @Override
    public void onDestroy() {
        SoundPoolManager.getInstance(this).release();
        super.onDestroy();
    }


    private void getIntentExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = "";
            if (bundle.containsKey("Name")) {
                name = bundle.getString("Name");
                contactName.setText(name);
                if (name != null)
                    params.put("name", name);
            } else {
                params.put("name", phoneNumber.toString());
            }
            if (bundle.containsKey("phoneNumber")) {
                txtPhoneNumber = bundle.getString("phoneNumber");
                getUserPic(this, txtPhoneNumber);
                if (txtPhoneNumber.startsWith("+")) {

                    params.put("to", txtPhoneNumber);
                    phoneNumber.setText(txtPhoneNumber);

                } else {
                    TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//                    String countryCodeValue = tm.getNetworkCountryIso();
                    params.put("to", getCountryDialCode() + txtPhoneNumber);
                    phoneNumber.setText(getCountryDialCode() + txtPhoneNumber);
                }

                ConnectOptions connectOptions = new ConnectOptions.Builder(accessToken)
                        .params(params)
                        .build();
                activeCall = Voice.connect(VoiceActivityDuplicate.this, connectOptions, callListener);
                setCallUI();
            }
        }
    }

    public String getCountryDialCode() {
        String contryId = null;
        String contryDialCode = null;

        TelephonyManager telephonyMngr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        contryId = telephonyMngr.getSimCountryIso().toUpperCase();
        String[] arrContryCode = this.getResources().getStringArray(R.array.DialingCountryCode);
        for (int i = 0; i < arrContryCode.length; i++) {
            String[] arrDial = arrContryCode[i].split(",");
            if (arrDial[1].trim().equals(contryId.trim())) {
                contryDialCode = arrDial[0];
                break;
            }
        }
        return "+" + contryDialCode;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIncomingCallIntent(intent);
    }

    private RegistrationListener registrationListener() {
        return new RegistrationListener() {
            @Override
            public void onRegistered(@NonNull String accessToken, @NonNull String fcmToken) {
                Log.d(TAG, "Successfully registered FCM " + fcmToken);
            }

            @Override
            public void onError(@NonNull RegistrationException error,
                                @NonNull String accessToken,
                                @NonNull String fcmToken) {
                String message = String.format(
                        Locale.US,
                        "Registration Error: %d, %s",
                        error.getErrorCode(),
                        error.getMessage());
                Log.e(TAG, message);
                Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        };
    }

    private String callSid = "";

    private Call.Listener callListener() {
        return new Call.Listener() {
            /*
             * This callback is emitted once before the Call.Listener.onConnected() callback when
             * the callee is being alerted of a Call. The behavior of this callback is determined by
             * the answerOnBridge flag provided in the Dial verb of your TwiML application
             * associated with this client. If the answerOnBridge flag is false, which is the
             * default, the Call.Listener.onConnected() callback will be emitted immediately after
             * Call.Listener.onRinging(). If the answerOnBridge flag is true, this will cause the
             * call to emit the onConnected callback only after the call is answered.
             * See answeronbridge for more details on how to use it with the Dial TwiML verb. If the
             * twiML response contains a Say verb, then the call will emit the
             * Call.Listener.onConnected callback immediately after Call.Listener.onRinging() is
             * raised, irrespective of the value of answerOnBridge being set to true or false
             */
            @Override
            public void onRinging(@NonNull Call call) {
                Log.d(TAG, "Ringing");
                /*
                 * When [answerOnBridge](https://www.twilio.com/docs/voice/twiml/dial#answeronbridge)
                 * is enabled in the <Dial> TwiML verb, the caller will not hear the ringback while
                 * the call is ringing and awaiting to be accepted on the callee's side. The application
                 * can use the `SoundPoolManager` to play custom audio files between the
                 * `Call.Listener.onRinging()` and the `Call.Listener.onConnected()` callbacks.
                 */
                if (BuildConfig.playCustomRingback) {
                    SoundPoolManager.getInstance(VoiceActivityDuplicate.this).playRinging();
                }
            }

            @Override
            public void onConnectFailure(@NonNull Call call, @NonNull CallException error) {
                setAudioFocus(false);
                if (BuildConfig.playCustomRingback) {
                    SoundPoolManager.getInstance(VoiceActivityDuplicate.this).stopRinging();
                }
                Log.d(TAG, "Connect failure");
                String message = String.format(
                        Locale.US,
                        "Call Error: %d, %s",
                        error.getErrorCode(),
                        error.getMessage());
                Log.e(TAG, message);
                Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
                resetUI();
            }

            @Override
            public void onConnected(@NonNull Call call) {
                setAudioFocus(true);
                if (BuildConfig.playCustomRingback) {
                    SoundPoolManager.getInstance(VoiceActivityDuplicate.this).stopRinging();
                }
                Log.d(TAG, "Connected");
                activeCall = call;
                callSid = call.getSid();
                recordCall.setClickable(true);

                Log.d("CALL", call.getSid());
            }

            @Override
            public void onReconnecting(@NonNull Call call, @NonNull CallException callException) {
                Log.d(TAG, "onReconnecting");
            }

            @Override
            public void onReconnected(@NonNull Call call) {
                Log.d(TAG, "onReconnected");
            }

            @Override
            public void onDisconnected(@NonNull Call call, CallException error) {
                setAudioFocus(false);
                if (BuildConfig.playCustomRingback) {
                    SoundPoolManager.getInstance(VoiceActivityDuplicate.this).stopRinging();
                }
                Log.d(TAG, "Disconnected");

                if (error != null) {
                    String message = String.format(
                            Locale.US,
                            "Call Error: %d, %s",
                            error.getErrorCode(),
                            error.getMessage());
                    Log.e(TAG, message);
                    Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
                }
                resetUI();
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /*
     * The UI state when there is an active call
     */
    private void setCallUI() {
        hangupActionFab.show();
        holdActionFab.show();
        muteActionFab.show();
        chronometer.setVisibility(View.VISIBLE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    /*
     * Reset UI elements
     */
    private void resetUI() {
        muteActionFab.setImageDrawable(ContextCompat.getDrawable(VoiceActivityDuplicate.this, R.drawable.ic_mic_white_24dp));
        holdActionFab.hide();
    /*    holdActionFab.setBackgroundTintList(ColorStateList
                .valueOf(ContextCompat.getColor(this, R.color.colorAccent)));*/
        muteActionFab.hide();
        hangupActionFab.hide();
        chronometer.setVisibility(View.INVISIBLE);
        chronometer.stop();
    }


    private void handleIncomingCallIntent(Intent intent) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            activeCallInvite = intent.getParcelableExtra(Constants.INCOMING_CALL_INVITE);
            activeCallNotificationId = intent.getIntExtra(Constants.INCOMING_CALL_NOTIFICATION_ID, 0);

            switch (action) {
                case Constants.ACTION_INCOMING_CALL:
                    handleIncomingCall();
                    break;
                case Constants.ACTION_INCOMING_CALL_NOTIFICATION:
                    showIncomingCallDialog();
                    break;
                case Constants.ACTION_CANCEL_CALL:
                    handleCancel();
                    break;
                case Constants.ACTION_FCM_TOKEN:
                    retrieveAccessToken();
                    break;
                case Constants.ACTION_ACCEPT:
                    answer();
                    break;
                default:
                    break;
            }
        }
    }

    private void handleIncomingCall() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            showIncomingCallDialog();
        } else {
            if (isAppVisible()) {
                showIncomingCallDialog();
            }
        }
    }

    private void handleCancel() {
        if (alertDialog != null && alertDialog.isShowing()) {
            SoundPoolManager.getInstance(this).stopRinging();
            alertDialog.cancel();
        }
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.ACTION_INCOMING_CALL);
            intentFilter.addAction(Constants.ACTION_CANCEL_CALL);
            intentFilter.addAction(Constants.ACTION_FCM_TOKEN);
            LocalBroadcastManager.getInstance(this).registerReceiver(
                    voiceBroadcastReceiver, intentFilter);
            isReceiverRegistered = true;
        }
    }

    private void unregisterReceiver() {
        if (isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(voiceBroadcastReceiver);
            isReceiverRegistered = false;
        }
    }

    private class VoiceBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && (action.equals(Constants.ACTION_INCOMING_CALL) || action.equals(Constants.ACTION_CANCEL_CALL))) {
                /*
                 * Handle the incoming or cancelled call invite
                 */
                handleIncomingCallIntent(intent);
            }
        }
    }

    private DialogInterface.OnClickListener answerCallClickListener() {
        return (dialog, which) -> {
            Log.d(TAG, "Clicked accept");
            Intent acceptIntent = new Intent(getApplicationContext(), IncomingCallNotificationService.class);
            acceptIntent.setAction(Constants.ACTION_ACCEPT);
            acceptIntent.putExtra(Constants.INCOMING_CALL_INVITE, activeCallInvite);
            acceptIntent.putExtra(Constants.INCOMING_CALL_NOTIFICATION_ID, activeCallNotificationId);
            Log.d(TAG, "Clicked accept startService");
            startService(acceptIntent);
        };
    }

    private DialogInterface.OnClickListener callClickListener() {
        return (dialog, which) -> {
            // Place a call
            EditText contact = ((AlertDialog) dialog).findViewById(R.id.contact);
            params.put("to", contact.getText().toString());
            ConnectOptions connectOptions = new ConnectOptions.Builder(accessToken)
                    .params(params)
                    .build();
            activeCall = Voice.connect(VoiceActivityDuplicate.this, connectOptions, callListener);
            setCallUI();
            alertDialog.dismiss();
        };
    }

    private DialogInterface.OnClickListener cancelCallClickListener() {
        return (dialogInterface, i) -> {
            SoundPoolManager.getInstance(VoiceActivityDuplicate.this).stopRinging();
            if (activeCallInvite != null) {
                Intent intent = new Intent(VoiceActivityDuplicate.this, IncomingCallNotificationService.class);
                intent.setAction(Constants.ACTION_REJECT);
                intent.putExtra(Constants.INCOMING_CALL_INVITE, activeCallInvite);
                startService(intent);
            }
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        };
    }

    public static AlertDialog createIncomingCallDialog(
            Context context,
            CallInvite callInvite,
            DialogInterface.OnClickListener answerCallClickListener,
            DialogInterface.OnClickListener cancelClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setIcon(R.drawable.ic_call_black_24dp);
        alertDialogBuilder.setTitle("Incoming Call");
        alertDialogBuilder.setPositiveButton("Accept", answerCallClickListener);
        alertDialogBuilder.setNegativeButton("Reject", cancelClickListener);
        alertDialogBuilder.setMessage(callInvite.getFrom() + " is calling.");
        return alertDialogBuilder.create();
    }

    /*
     * Register your FCM token with Twilio to receive incoming call invites
     */
    private void registerForCallInvites() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            String fcmToken = instanceIdResult.getToken();
            Log.i(TAG, "Registering with FCM");
            Voice.register(accessToken, Voice.RegistrationChannel.FCM, fcmToken, registrationListener);
        });
    }


    private View.OnClickListener hangupActionFabClickListener() {
        return v -> {
            SoundPoolManager.getInstance(VoiceActivityDuplicate.this).playDisconnect();
            resetUI();
            disconnect();

        };
    }

    private View.OnClickListener holdActionFabClickListener() {
        return v -> hold();
    }

    private View.OnClickListener muteActionFabClickListener() {
        return v -> mute();
    }

    private View.OnClickListener speakerActionFabClickListener() {
        return v -> {
            if (audioManager.isSpeakerphoneOn()) {
                audioManager.setSpeakerphoneOn(false);
                speakerActionFab.setImageDrawable(ContextCompat.getDrawable(VoiceActivityDuplicate.this, R.drawable.ic_phonelink_ring_white_24dp));
            } else {
                audioManager.setSpeakerphoneOn(true);
                speakerActionFab.setImageDrawable(ContextCompat.getDrawable(VoiceActivityDuplicate.this, R.drawable.ic_volume_up_white_24dp));
            }
        };
    }

    /*
     * Record Calls
     * */
    private View.OnClickListener recordActionFabClickListener() {
        return v -> {
            String ACCOUNT_SID = "AC4b79c7661e8938a314c04c0aa513cf49";
            String AUTH_TOKEN = "01c9c79425bee99bc1c76641e3894c6b";
//
//            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//            Recording recording =
//                    Recording.creator(activeCall.getSid())
//                            .create();
//
//            Log.d("recordingId", recording.getSid());

//            https://api.twilio.com/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Calls/CAXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Recordings.json


//            String url = "https://api.twilio.com/2010-04-01/Accounts/" + ACCOUNT_SID + "/Calls/" + activeCall.getSid() + "/Recordings.json";
//
//            url = ApiConstant.BASE_URL + "callRecording";
//            Log.d("url", url);
//            Ion.with(this).load(url)
//                    .asString()
//                    .setCallback((e, accessToken) -> {
//                        if (e == null) {
//                            Log.d(TAG, "Access token: " + accessToken);
////                            VoiceActivityDuplicate.this.accessToken = accessToken;
////                            registerForCallInvites();
////                            getIntentExtras();
//
//                        } else {
//                            Snackbar.make(coordinatorLayout,
//                                    "Error retrieving access token. Unable to make calls",
//                                    Snackbar.LENGTH_LONG).show();
//                        }
//                    });


            ApiCallRecords callRecords = new ApiCallRecords(this, new IResult() {
                @Override
                public void notifySuccess(String requestType, Object response) {
                    if (response instanceof Boolean) {
                        IntentUtils.finishActivity(VoiceActivityDuplicate.this);
                    }
                }

                @Override
                public void notifyNetworkSuccess(String requestType) {

                }
            });
            CallRecordingRequest callRecordingRequest = new CallRecordingRequest();
            callRecordingRequest.setCallId(callSid);
            callRecords.execute(callRecordingRequest);

        };
    }

    /*
     * Accept an incoming Call
     */
    private void answer() {
        SoundPoolManager.getInstance(this).stopRinging();
        activeCallInvite.accept(this, callListener);
        notificationManager.cancel(activeCallNotificationId);
        setCallUI();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    /*
     * Disconnect from Call
     */
    private void disconnect() {
        if (activeCall != null) {
            activeCall.disconnect();
            activeCall = null;
        }

        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hold() {
        if (activeCall != null) {
            boolean hold = !activeCall.isOnHold();
            activeCall.hold(hold);
            applyFabState(holdActionFab, hold);
        }
    }

    private void mute() {
        if (activeCall != null) {
            boolean mute = !activeCall.isMuted();
            activeCall.mute(mute);
            applyFabState(muteActionFab, mute);
        }
    }

    private void applyFabState(FloatingActionButton button, boolean enabled) {
        // Set fab as pressed when call is on hold
     /*   ColorStateList colorStateList = enabled ?
                ColorStateList.valueOf(ContextCompat.getColor(this,
                        R.color.colorPrimaryDark)) :
                ColorStateList.valueOf(ContextCompat.getColor(this,
                        R.color.colorAccent));
        button.setBackgroundTintList(colorStateList);*/
    }

    private void setAudioFocus(boolean setFocus) {
        if (audioManager != null) {
            if (setFocus) {
                savedAudioMode = audioManager.getMode();
                // Request audio focus before making any device switch.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    AudioAttributes playbackAttributes = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                            .build();
                    AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                            .setAudioAttributes(playbackAttributes)
                            .setAcceptsDelayedFocusGain(true)
                            .setOnAudioFocusChangeListener(i -> {
                            })
                            .build();
                    audioManager.requestAudioFocus(focusRequest);
                } else {
                    audioManager.requestAudioFocus(
                            focusChange -> {
                            },
                            AudioManager.STREAM_VOICE_CALL,
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                }
                /*
                 * Start by setting MODE_IN_COMMUNICATION as default audio mode. It is
                 * required to be in this mode when playout and/or recording starts for
                 * best possible VoIP performance. Some devices have difficulties with speaker mode
                 * if this is not set.
                 */
                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            } else {
                audioManager.setMode(savedAudioMode);
                audioManager.abandonAudioFocus(null);
            }
        }
    }

    private boolean checkPermissionForMicrophone() {
        int resultMic = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return resultMic == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionForMicrophone() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
            Snackbar.make(coordinatorLayout,
                    "Microphone permissions needed. Please allow in your application settings.",
                    Snackbar.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MIC_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /*
         * Check if microphone permissions is granted
         */
        if (requestCode == MIC_PERMISSION_REQUEST_CODE && permissions.length > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(coordinatorLayout,
                        "Microphone permissions needed. Please allow in your application settings.",
                        Snackbar.LENGTH_LONG).show();
            } else {
                retrieveAccessToken();
            }
        }
    }

    private void showIncomingCallDialog() {
        SoundPoolManager.getInstance(this).playRinging();
        if (activeCallInvite != null) {
            alertDialog = createIncomingCallDialog(VoiceActivityDuplicate.this,
                    activeCallInvite,
                    answerCallClickListener(),
                    cancelCallClickListener());
            alertDialog.show();
        }
    }

    private boolean isAppVisible() {
        return ProcessLifecycleOwner
                .get()
                .getLifecycle()
                .getCurrentState()
                .isAtLeast(Lifecycle.State.STARTED);
    }

    /*
     * Get an access token from your Twilio access token server
     */
    private void retrieveAccessToken() {
        Ion.with(this).load(TWILIO_ACCESS_TOKEN_SERVER_URL + "?identity=" + identity)
                .asString()
                .setCallback((e, accessToken) -> {
                    if (e == null) {
                        Log.d(TAG, "Access token: " + accessToken);
                        VoiceActivityDuplicate.this.accessToken = accessToken;
                        registerForCallInvites();
                        getIntentExtras();

                    } else {
                        Snackbar.make(coordinatorLayout,
                                "Error retrieving access token. Unable to make calls",
                                Snackbar.LENGTH_LONG).show();
                    }
                });
    }


    public void getUserPic(Context context, String number) {
        ContentResolver contentResolver = context.getContentResolver();
        String contactId = null;
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID};

        Cursor cursor =
                contentResolver.query(
                        uri,
                        projection,
                        null,
                        null,
                        null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
            }
            cursor.close();
        }


        Bitmap photo = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_user_icon);

        try {
            if (contactId != null) {
                InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                        ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactId)));

                if (inputStream != null) {
                    photo = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (photo != null)
            userProfile.setImageBitmap(photo);
        else
            userProfile.setImageResource(R.drawable.ic_user_icon);
        

    }

}
