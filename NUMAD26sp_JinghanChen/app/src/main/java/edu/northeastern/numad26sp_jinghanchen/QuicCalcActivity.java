package edu.northeastern.numad26sp_jinghanchen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuicCalcActivity extends AppCompatActivity {

    private TextView txtDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quic_calc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtDisplay = findViewById(R.id.txtDisplay);

        // declare buttons
        int[] numberButtons = {
                R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6,
                R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btn0
        };
        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(v -> {
                txtDisplay.append(((Button)v).getText().toString());
            });
        }

        findViewById(R.id.btnPlus).setOnClickListener(v -> txtDisplay.append("+"));
        findViewById(R.id.btnMinus).setOnClickListener(v -> txtDisplay.append("-"));

        findViewById(R.id.btnDel).setOnClickListener(v -> {
            String text = txtDisplay.getText().toString();
            if (!text.isEmpty()) {
                txtDisplay.setText(text.substring(0, text.length() - 1));
            }
        });

        // Evaluate expression
        findViewById(R.id.btnEquals).setOnClickListener(v -> {
            try {
                String expr = txtDisplay.getText().toString();
                int result = evaluate(expr);
                txtDisplay.setText(String.valueOf(result));
            } catch (Exception e) {
                txtDisplay.setText("Error");
            }
        });
    }

    private int evaluate(String expr) {
        expr = expr.replaceAll("\\s+",""); // remove spaces
        int result = 0;
        char op = '+';
        int num = 0;

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }
            if (!Character.isDigit(c) || i == expr.length() - 1) {
                if (op == '+') result += num;
                else if (op == '-') result -= num;
                op = c;
                num = 0;
            }
        }
        return result;
    }
}