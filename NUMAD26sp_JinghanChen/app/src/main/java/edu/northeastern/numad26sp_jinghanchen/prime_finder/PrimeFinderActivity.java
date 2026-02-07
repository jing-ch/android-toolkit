package edu.northeastern.numad26sp_jinghanchen.prime_finder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.numad26sp_jinghanchen.R;

public class PrimeFinderActivity extends AppCompatActivity {

    private Button btnStart, btnTerminate;
    private TextView tvCurrent, tvLatest;

    private Handler handler;

    private Thread workerThread;
    private boolean isRunning = false;
    private boolean stopRequested = false;

    private int currentNumber = 3;
    private int latestPrime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_finder);

        btnStart = findViewById(R.id.btnStart);
        btnTerminate = findViewById(R.id.btnTerminate);
        tvCurrent = findViewById(R.id.tvCurrent);
        tvLatest = findViewById(R.id.tvLatestPrime);

        handler = new Handler(Looper.getMainLooper());

        // Restore state after rotation
        if (savedInstanceState != null) {
            isRunning = savedInstanceState.getBoolean("running");
            currentNumber = savedInstanceState.getInt("current");
            latestPrime = savedInstanceState.getInt("latest");
        }

        updateText();

        btnStart.setOnClickListener(v -> startSearch());
        btnTerminate.setOnClickListener(v -> stopSearch());

        updateButtons();

        // If rotation happened while running, resume automatically
        if (isRunning) {
            startWorker(currentNumber);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("running", isRunning);
        outState.putInt("current", currentNumber);
        outState.putInt("latest", latestPrime);
    }

    private void startSearch() {
        if (isRunning) return;

        // Restart from beginning after terminate
        currentNumber = 3;
        latestPrime = 0;

        updateText();
        startWorker(currentNumber);
    }

    private void startWorker(int start) {
        stopRequested = false;
        isRunning = true;
        updateButtons();

        workerThread = new Thread(() -> {
            int n = start;

            long lastUpdateTime = System.currentTimeMillis();

            while (!stopRequested) {

                currentNumber = n;

                // update current number on UI
                // update only once a while. to avoid updating too frequently and stuck.
                long now = System.currentTimeMillis();
                if (now - lastUpdateTime > 50) {
                    handler.post(this::updateText);
                    lastUpdateTime = now;
                }

                if (isPrime(n)) {
                    latestPrime = n;
                    handler.post(this::updateText);
                }

                n += 2;
            }
        });

        workerThread.start();
    }

    private void stopSearch() {
        stopRequested = true;
        isRunning = false;
        updateButtons();
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;

        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }

        return true;
    }

    private void updateText() {
        tvCurrent.setText("Current number: " + currentNumber);
        tvLatest.setText("Latest prime: " + latestPrime);
    }

    private void updateButtons() {
        btnStart.setEnabled(!isRunning);
        btnTerminate.setEnabled(isRunning);
    }
}
