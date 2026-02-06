package edu.northeastern.numad26sp_jinghanchen.main;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.content.Intent;

import edu.northeastern.numad26sp_jinghanchen.about_me.AboutMeActivity;
import edu.northeastern.numad26sp_jinghanchen.link_collector.LinkCollectorActivity;
import edu.northeastern.numad26sp_jinghanchen.quic_calc.QuicCalcActivity;
import edu.northeastern.numad26sp_jinghanchen.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnAboutMe) {
            Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnQuicCalc) {
            Intent intent = new Intent(MainActivity.this, QuicCalcActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnLinkCollector) {
            Intent intent = new Intent(MainActivity.this, LinkCollectorActivity.class);
            startActivity(intent);
        }
    }
}