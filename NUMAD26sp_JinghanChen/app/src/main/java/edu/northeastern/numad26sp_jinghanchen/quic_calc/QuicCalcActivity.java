package edu.northeastern.numad26sp_jinghanchen.quic_calc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.northeastern.numad26sp_jinghanchen.R;

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

    }

    public void onCalcClick(View view) {
        int id = view.getId();
        Button btn = (Button) view;
        String btnText = btn.getText().toString();
        String display = txtDisplay.getText().toString();

        if (id == R.id.btnDel) {
            // del button
            if (!display.equals("CALC") && !display.isEmpty()) {
                display = display.substring(0, display.length() - 1);
                if (display.isEmpty()) {
                    display = "CALC";
                }
            }
            txtDisplay.setText(display);
        } else if (id == R.id.btnEquals) {
            // equals button
            if (!display.equals("CALC")) {
                try {
                    int result = evaluate(display);
                    txtDisplay.setText(String.valueOf(result));
                } catch (Exception e) {
                    txtDisplay.setText("CALC");
                }
            }
        } else if (id == R.id.btnPlus || id == R.id.btnMinus || btnText.matches("[0-9]") ) {
            // + or - or numbers button
            if (display.equals("CALC")) {
                txtDisplay.setText(btnText);
            } else {
                txtDisplay.append(btnText);
            }
        }
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