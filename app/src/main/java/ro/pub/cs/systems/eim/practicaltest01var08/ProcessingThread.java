package ro.pub.cs.systems.eim.practicaltest01var08;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread{
    private Context context;
    private String Answer;
    private boolean isRunning = true;
    private int num = 0;
    // Constructor care primește contextul și numerele
    public ProcessingThread(Context context, String Answer) {
        this.context = context;
        this.Answer = Answer;
        Random random = new Random();
        num = random.nextInt(Answer.length());
    }

    public void stopThread() {
        isRunning = false;
    }

    public void run() {
        Log.d("SERVICE", "Thread-ul de procesare a pornit...");
        while (isRunning) {
            sendMessage();
            try {
                // Așteaptă 10 secunde
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                isRunning = false;
            }
        }
        Log.d("SERVICE", "Thread-ul de procesare s-a oprit.");
    }

    private void sendMessage() {
        String s = null;
        for (int i = 0; i < Answer.length(); i++){
            if (i == num) {
                char c = Answer.charAt(i);
                s += c;
            } else {
                s += "*";
            }
        }
        Intent intent = new Intent("Broadcast");
        intent.putExtra("String", s);
        context.sendBroadcast(intent);

        Log.d("SERVICE", s);
    }
}
