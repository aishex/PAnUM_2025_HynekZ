package com.example.kafeteria;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    private TextView tvTimer;
    private CountDownTimer countDownTimer;
    private String orderSummaryStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        tvTimer = findViewById(R.id.tvTimer);
        Button btnShareEmail = findViewById(R.id.btnShareEmail);

        StringBuilder sb = new StringBuilder();
        sb.append("Zamówienie z Kafeterii:\n\n");
        for (CartItem ci : CartManager.getInstance().getItems()) {
            sb.append("- ").append(ci.getItem().getName())
              .append(" x").append(ci.getQuantity())
              .append(" (").append(ci.getItem().getDetails2()).append(")\n");
        }
        sb.append("\nŁączna kwota: ").append(String.format("%.2f PLN", CartManager.getInstance().getTotalPrice()));
        
        orderSummaryStr = sb.toString();
        CartManager.getInstance().clearCart();

        countDownTimer = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tvTimer.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                tvTimer.setText("00:00");
                tvTimer.setTextColor(android.graphics.Color.GREEN);
            }
        }.start();

        btnShareEmail.setOnClickListener(v -> shareOrderViaEmail());
    }

    private void shareOrderViaEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mój paragon z Kafeterii");
        emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummaryStr);
        
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Mój paragon z Kafeterii");
            sendIntent.putExtra(Intent.EXTRA_TEXT, orderSummaryStr);
            startActivity(Intent.createChooser(sendIntent, "Udostępnij przez:"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}
