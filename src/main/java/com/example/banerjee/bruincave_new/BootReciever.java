package com.example.banerjee.bruincave_new;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Vikrant Banerjee on 3/5/2017.
 */
public class BootReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Service", "Stops");
        context.startService(new Intent(context, NotificationService.class));
    }
}
