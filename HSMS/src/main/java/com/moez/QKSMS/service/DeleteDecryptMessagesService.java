package com.moez.QKSMS.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.moez.QKSMS.common.QKPreferences;
import com.moez.QKSMS.enums.QKPreference;
import com.moez.QKSMS.ui.messagelist.MessageItem;
import com.moez.QKSMS.ui.messagelist.MessageListFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kautsar on 29/12/17.
 */

@SuppressLint("Registered")
public class DeleteDecryptMessagesService extends Service {

    private static final String TAG = "DeleteDecryptMessagesService";
    private final int INTERVAL = (60000 * Integer.parseInt(QKPreferences.getString(QKPreference.AUTO_DELETE_DECRYPT)));
    private MessageItem messageItem;
    private CountDownTimer countDownTimer;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Services is created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        messageItem = (MessageItem) intent.getExtras().getSerializable("EXTRA_MSG_ITEM");

        countDownTimer = new CountDownTimer(INTERVAL, 1000) {
            @SuppressLint("LongLogTag")
            @Override
            public void onTick(long l) {
                Log.i(TAG, "Ticking: " + l / 1000);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFinish() {
                Log.i(TAG, "Done !");
                new MessageListFragment().deleteMessageItem(messageItem);
            }
        };

        countDownTimer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDestroy() {
        Log.i(TAG, "Stop Service");
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        super.onDestroy();
    }
}
