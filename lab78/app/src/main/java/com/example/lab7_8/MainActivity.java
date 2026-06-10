package com.example.lab7_8;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout viewStopwatch, viewTimer;
    private Button btnShowStopwatch, btnShowTimer;

    private TextView tvStopwatch;
    private Button btnSwStart, btnSwPause, btnSwReset;
    private Handler swHandler = new Handler(Looper.getMainLooper());
    private long swStartTime = 0L, swTimeInMilliseconds = 0L, swTimeSwapBuff = 0L, swUpdateTime = 0L;
    private boolean isSwRunning = false;

    private Runnable swRunnable = new Runnable() {
        public void run() {
            swTimeInMilliseconds = SystemClock.uptimeMillis() - swStartTime;
            swUpdateTime = swTimeSwapBuff + swTimeInMilliseconds;
            int secs = (int) (swUpdateTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int hundredths = (int) ((swUpdateTime % 1000) / 10);
            tvStopwatch.setText(String.format("%02d:%02d:%02d", mins, secs, hundredths));
            swHandler.postDelayed(this, 10);
        }
    };

    private TextView tvTimer;
    private EditText etTimerInput;
    private Button btnTmStart, btnTmPause, btnTmReset;
    private CountDownTimer countDownTimer;
    private long tmTimeLeftInMillis;
    private boolean isTmRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewStopwatch = findViewById(R.id.viewStopwatch);
        viewTimer = findViewById(R.id.viewTimer);
        btnShowStopwatch = findViewById(R.id.btnShowStopwatch);
        btnShowTimer = findViewById(R.id.btnShowTimer);

        tvStopwatch = findViewById(R.id.tvStopwatch);
        btnSwStart = findViewById(R.id.btnSwStart);
        btnSwPause = findViewById(R.id.btnSwPause);
        btnSwReset = findViewById(R.id.btnSwReset);

        tvTimer = findViewById(R.id.tvTimer);
        etTimerInput = findViewById(R.id.etTimerInput);
        btnTmStart = findViewById(R.id.btnTmStart);
        btnTmPause = findViewById(R.id.btnTmPause);
        btnTmReset = findViewById(R.id.btnTmReset);

        btnShowStopwatch.setOnClickListener(v -> {
            viewStopwatch.setVisibility(View.VISIBLE);
            viewTimer.setVisibility(View.GONE);
        });

        btnShowTimer.setOnClickListener(v -> {
            viewStopwatch.setVisibility(View.GONE);
            viewTimer.setVisibility(View.VISIBLE);
        });

        btnSwStart.setOnClickListener(v -> {
            if (!isSwRunning) {
                swStartTime = SystemClock.uptimeMillis();
                swHandler.postDelayed(swRunnable, 0);
                isSwRunning = true;
            }
        });

        btnSwPause.setOnClickListener(v -> {
           if (isSwRunning) {
                 swTimeSwapBuff += swTimeInMilliseconds;
                swHandler.removeCallbacks(swRunnable);
                isSwRunning = false;
            }
        });

        btnSwReset.setOnClickListener(v -> {
            swTimeSwapBuff = 0L;
            swStartTime = 0L;
            swTimeInMilliseconds = 0L;
            swUpdateTime = 0L;
            tvStopwatch.setText("00:00:00");
            swHandler.removeCallbacks(swRunnable);
            isSwRunning = false;
        });

        btnTmStart.setOnClickListener(v -> {
            if (!isTmRunning) {
                if (tmTimeLeftInMillis == 0) {
                    String input = etTimerInput.getText().toString();
                    if (!input.isEmpty()) {
                        tmTimeLeftInMillis = Long.parseLong(input) * 1000;
                    } else {
                        return;
                    }
                }
                startTimer();
            }
        });

        btnTmPause.setOnClickListener(v -> {
            if (isTmRunning) {
                countDownTimer.cancel();
                isTmRunning = false;
            }
        });

        btnTmReset.setOnClickListener(v -> {
            if (countDownTimer != null) countDownTimer.cancel();
            isTmRunning = false;
            tmTimeLeftInMillis = 0;
            tvTimer.setText("00:00");
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(tmTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tmTimeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                isTmRunning = false;
                tmTimeLeftInMillis = 0;
                updateTimerText();

                String text = "Komunikat!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(MainActivity.this, text, duration);
                toast.show();

                ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_INCALL_LITE, 200);
            }
        }.start();
        isTmRunning = true;
    }

    private void updateTimerText() {
        int minutes = (int) (tmTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (tmTimeLeftInMillis / 1000) % 60;
        tvTimer.setText(String.format("%02d:%02d", minutes, seconds));
    }
}