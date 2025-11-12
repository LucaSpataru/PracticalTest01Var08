package ro.pub.cs.systems.eim.practicaltest01var08;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BROADCAST", "Un mesaj de difuzare a fost primit!");
        String date = intent.getStringExtra("String");
        Toast.makeText(context, date, Toast.LENGTH_SHORT).show();
    }
}