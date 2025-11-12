package ro.pub.cs.systems.eim.practicaltest01var08;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest01Var08MainActivity extends AppCompatActivity {
    private Button play;
    private EditText riddle, answer;
    private static final int SECONDARY_ACTIVITY_REQUEST_CODE = 1001;
    private static final String ANSWER_KEY = "ro.pub.cs.systems.eim.TEST.answer";
    private String State;
    private MyReceiver broadcastReciever;
    private IntentFilter intentFilter;

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ANSWER_KEY, State);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_var08_main);

        play = findViewById(R.id.button_0);
        riddle = findViewById(R.id.editText1);
        answer = findViewById(R.id.editText2);

        broadcastReciever = new MyReceiver();
        intentFilter = new IntentFilter("Broadcast");

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ANSWER_KEY)) {
                State = savedInstanceState.getString(ANSWER_KEY);
            }
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PracticalTest01Var08MainActivity.this, PracticalTest01Var08PlayActivity.class);
                String Riddle = riddle.getText().toString();
                String Answer = answer.getText().toString();
                intent.putExtra("Answer", Answer);
                intent.putExtra("Riddle", Riddle);
                startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
                Intent serviceIntent = new Intent(PracticalTest01Var08MainActivity.this, PracticalTest01Var08Service.class);
                serviceIntent.putExtra("Answer", Answer);
                startService(serviceIntent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Rezultat: OK", Toast.LENGTH_SHORT).show();
                State = "OK";
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Rezultat: FAIL", Toast.LENGTH_SHORT).show();
                State = "FAIL";
            }
        }
    }
    @Override
    protected void onDestroy() {
        Toast.makeText(this, "Raspuns: " + State, Toast.LENGTH_SHORT).show();
        Log.d("Debug", "Raspuns: " + State);
        Intent serviceIntent = new Intent(PracticalTest01Var08MainActivity.this, PracticalTest01Var08Service.class);
        stopService(serviceIntent);
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(broadcastReciever, intentFilter, Context.RECEIVER_EXPORTED);
        }
    }

    @Override
    protected void onPause() {
        unregisterReceiver(broadcastReciever);
        super.onPause();
    }
}