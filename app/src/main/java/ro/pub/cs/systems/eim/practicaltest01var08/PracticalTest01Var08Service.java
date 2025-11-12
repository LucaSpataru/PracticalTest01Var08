package ro.pub.cs.systems.eim.practicaltest01var08;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Var08Service extends Service {
    private ProcessingThread processingThread = null;
    public PracticalTest01Var08Service() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String Answer = intent.getStringExtra("Answer");
            if (processingThread == null || !processingThread.isAlive()) {
                processingThread = new ProcessingThread(this, Answer);
                processingThread.start();
            }
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        if (processingThread != null) {
            processingThread.stopThread();
        }
        super.onDestroy();
    }
}