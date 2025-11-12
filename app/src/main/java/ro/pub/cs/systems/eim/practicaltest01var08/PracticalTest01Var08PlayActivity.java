package ro.pub.cs.systems.eim.practicaltest01var08;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest01Var08PlayActivity extends AppCompatActivity {
    private TextView textView;
    private Button check, back;
    private EditText answer;
    private String TrueAnswer;
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_var08_play);

        textView = findViewById(R.id.text);
        check = findViewById(R.id.button_1);
        back = findViewById(R.id.button_2);
        answer = findViewById(R.id.editText0);

        Intent intent = getIntent();
        if (intent != null) {
            String Riddle = intent.getStringExtra("Riddle");
            textView.setText(Riddle);
            TrueAnswer = intent.getStringExtra("Answer");
        }

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Answer = answer.getText().toString();
                if (Answer.equals(TrueAnswer)) {
                    setResult(RESULT_OK, new Intent());
                    finish();
                } else {
                    setResult(RESULT_CANCELED, new Intent());
                    finish();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}