package edu.northeastern.numad26sp_jinghanchen.prime_finder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.numad26sp_jinghanchen.R;

public class PrimeFinderActivity extends AppCompatActivity {

    // part 1 finder: approach 1 init
    private Button btnStart1, btnStop1;
    private TextView tvCurrent1, tvPrime1;
    private Handler handler;
    private Thread thread1;
    private boolean running1 = false;
    private boolean stop1 = false;
    private int current1 = 3;
    private int prime1 = 0;


    // part 1 finder: approach 2 init
    private Button btnStart2, btnStop2;
    private TextView tvCurrent2, tvPrime2;
    private Thread thread2;
    private boolean running2 = false;
    private boolean stop2 = false;
    private int current2 = 3;
    private int prime2 = 0;

    // part 2: greatest prime init
    private Button btnFindGreatestPrime;
    private TextView tvGreatestPrime;
    private int greatestPrime = 0;

    // part 3: pacifier switch
    private CheckBox cbPacifier;
    private boolean pacifierChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_finder);

        handler = new Handler(Looper.getMainLooper());

        // bind views
        // part 1 finder: approach 1
        btnStart1 = findViewById(R.id.btnStart1);
        btnStop1  = findViewById(R.id.btnStop1);
        tvCurrent1 = findViewById(R.id.tvCurrent1);
        tvPrime1   = findViewById(R.id.tvPrime1);
        // part 1 finder: approach 2
        btnStart2 = findViewById(R.id.btnStart2);
        btnStop2  = findViewById(R.id.btnStop2);
        tvCurrent2 = findViewById(R.id.tvCurrent2);
        tvPrime2   = findViewById(R.id.tvPrime2);
        // part 2: greatest prime
        btnFindGreatestPrime = findViewById(R.id.btnFindGreatestPrime);
        tvGreatestPrime = findViewById(R.id.tvGreatestPrime);
        // part 3: pacifier switch
        cbPacifier = findViewById(R.id.cbPacifier);



        // restore after configuration change
        if (savedInstanceState != null) {
            // part 1 finder: approach 1
            running1 = savedInstanceState.getBoolean("running1");
            current1 = savedInstanceState.getInt("current1");
            prime1   = savedInstanceState.getInt("prime1");
            // part 1 finder: finder approach 2
            running2 = savedInstanceState.getBoolean("running2");
            current2 = savedInstanceState.getInt("current2");
            prime2   = savedInstanceState.getInt("prime2");
            // part 2: greatest prime
            greatestPrime = savedInstanceState.getInt("greatestPrime");
            // part 3: pacifier switch
            cbPacifier.setChecked(savedInstanceState.getBoolean("pacifierChecked", false));
        }

        // handling events with click listeners
        // part 1 finder approach 1 buttons
        btnStart1.setOnClickListener(v -> startHandlerSearch());
        btnStop1.setOnClickListener(v -> stop1 = true);
        // part 1 finder approach 2 buttons
        btnStart2.setOnClickListener(v -> startRunOnUiSearch());
        btnStop2.setOnClickListener(v -> stop2 = true);
        // part 2: greatest prime
        btnFindGreatestPrime.setOnClickListener(v -> findGreatestPrime());
        // part 3: pacifier switch
        cbPacifier.setOnClickListener(v -> pacifierChecked = cbPacifier.isChecked());


        // resume after configuration change
        // part 1 finder: approach 1
        if (running1) startHandlerSearchFromCurrent();
        // part 1 finder: approach 2
        if (running2) startRunOnUiSearchFromCurrent();
        // part 2: greatest prime
        if (greatestPrime > 0) findGreatestPrime();
        // part 3: pacifier switch
        if (pacifierChecked) cbPacifier.setChecked(true);

    }


    // handling configuration change
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // part 1 finder: approach 1
        outState.putBoolean("running1", running1);
        outState.putInt("current1", current1);
        outState.putInt("prime1", prime1);
        // part 1 finder: approach 2
        outState.putBoolean("running2", running2);
        outState.putInt("current2", current2);
        outState.putInt("prime2", prime2);
        // part 2: greatest prime
        outState.putInt("greatestPrime", greatestPrime);
        // part 3: pacifier switch
        outState.putBoolean("pacifierChecked", cbPacifier.isChecked());
    }


    // finder approach 1
    private void startHandlerSearch() {
        if (running1) return;

        current1 = 3;
        prime1 = 0;
        stop1 = false;
        running1 = true;

        startThread1(current1);
    }

    private void startHandlerSearchFromCurrent() {
        stop1 = false;
        startThread1(current1);
    }

    private void startThread1(int start) {

        thread1 = new Thread(() -> {

            long lastUpdate = System.currentTimeMillis();
            int n = start;

            while (!stop1) {

                current1 = n;

                if (isPrime(n)) {
                    prime1 = n;
                }

                long now = System.currentTimeMillis();
                if (now - lastUpdate > 50) {
                    handler.post(this::updateText1);
                    lastUpdate = now;
                }

                n += 2;
            }

            running1 = false;
        });

        thread1.start();
    }

    private void updateText1() {
        tvCurrent1.setText("Current: " + current1);
        tvPrime1.setText("Latest prime: " + prime1);
    }


    // part 1 finder: approach 2
    private void startRunOnUiSearch() {
        if (running2) return;

        current2 = 3;
        prime2 = 0;
        stop2 = false;
        running2 = true;

        startThread2(current2);
    }

    private void startRunOnUiSearchFromCurrent() {
        stop2 = false;
        startThread2(current2);
    }

    private void startThread2(int start) {

        thread2 = new Thread(() -> {

            long lastUpdate = System.currentTimeMillis();
            int n = start;

            while (!stop2) {

                current2 = n;

                if (isPrime(n)) {
                    prime2 = n;
                }

                long now = System.currentTimeMillis();
                if (now - lastUpdate > 50) {
                    runOnUiThread(this::updateText2);
                    lastUpdate = now;
                }

                n += 2;
            }

            running2 = false;
        });

        thread2.start();
    }

    private void updateText2() {
        tvCurrent2.setText("Current: " + current2);
        tvPrime2.setText("Latest prime: " + prime2);
    }

    // part 1 finder: this method is shared by approach 1 and 2.
    private boolean isPrime(int n) {
        if (n < 2) return false;

        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // part 2 greatest prime
    private void findGreatestPrime() {
        greatestPrime = Math.max(prime1, prime2);
        tvGreatestPrime.setText("Greatest prime: " + greatestPrime);
    }

}
