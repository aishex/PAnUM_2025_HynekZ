package com.example.bieganie;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etPaceMin, etPaceSec, etCustomDistance1;
    private Button btnCalcFromPace;

    private EditText etSpeed, etCustomDistance2;
    private Button btnCalcFromSpeed;

    private EditText etTargetDistance, etTargetTimeMin;
    private Button btnCalcRequired;

    private TextView tvResults;

    private static final double CONST_MARATHON = 42.195;
    private static final double CONST_HALF_MARATHON = 21.0975;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPaceMin = findViewById(R.id.etPaceMin);
        etPaceSec = findViewById(R.id.etPaceSec);
        etCustomDistance1 = findViewById(R.id.etCustomDistance1);
        btnCalcFromPace = findViewById(R.id.btnCalcFromPace);

        etSpeed = findViewById(R.id.etSpeed);
        etCustomDistance2 = findViewById(R.id.etCustomDistance2);
        btnCalcFromSpeed = findViewById(R.id.btnCalcFromSpeed);

        etTargetDistance = findViewById(R.id.etTargetDistance);
        etTargetTimeMin = findViewById(R.id.etTargetTimeMin);
        btnCalcRequired = findViewById(R.id.btnCalcRequired);

        tvResults = findViewById(R.id.tvResults);

        btnCalcFromPace.setOnClickListener(v -> {
            String minStr = etPaceMin.getText().toString().trim();
            String secStr = etPaceSec.getText().toString().trim();

            if (minStr.isEmpty()) {
                Toast.makeText(this, "Podaj tempo biegu", Toast.LENGTH_SHORT).show();
                return;
            }

            int minutes = Integer.parseInt(minStr);
            int seconds = secStr.isEmpty() ? 0 : Integer.parseInt(secStr);

            int totalPaceInSeconds = (minutes * 60) + seconds;
            if (totalPaceInSeconds == 0) {
                Toast.makeText(this, "Tempo nie może wynosić 0", Toast.LENGTH_SHORT).show();
                return;
            }

            double speedKmh = 3600.0 / totalPaceInSeconds;

            double customDist = 0;
            String cDistStr = etCustomDistance1.getText().toString().trim();
            if (!cDistStr.isEmpty()) {
                customDist = Double.parseDouble(cDistStr);
            }

            displayStandardResults(totalPaceInSeconds, speedKmh, customDist);
        });

        btnCalcFromSpeed.setOnClickListener(v -> {
            String speedStr = etSpeed.getText().toString().trim();
            if (speedStr.isEmpty()) {
                Toast.makeText(this, "Podaj prędkość w km/h", Toast.LENGTH_SHORT).show();
                return;
            }

            double speedKmh = Double.parseDouble(speedStr);
            if (speedKmh <= 0) {
                Toast.makeText(this, "Prędkość musi być większa od 0", Toast.LENGTH_SHORT).show();
                return;
            }

            int totalPaceInSeconds = (int) Math.round(3600.0 / speedKmh);

            double customDist = 0;
            String cDistStr = etCustomDistance2.getText().toString().trim();
            if (!cDistStr.isEmpty()) {
                customDist = Double.parseDouble(cDistStr);
            }

            displayStandardResults(totalPaceInSeconds, speedKmh, customDist);
        });

        btnCalcRequired.setOnClickListener(v -> {
            String distStr = etTargetDistance.getText().toString().trim();
            String timeStr = etTargetTimeMin.getText().toString().trim();

            if (distStr.isEmpty() || timeStr.isEmpty()) {
                Toast.makeText(this, "Podaj dystans oraz docelowy czas", Toast.LENGTH_SHORT).show();
                return;
            }

            double distance = Double.parseDouble(distStr);
            double timeInMinutes = Double.parseDouble(timeStr);

            if (distance <= 0 || timeInMinutes <= 0) {
                Toast.makeText(this, "Wartości muszą być większe od 0", Toast.LENGTH_SHORT).show();
                return;
            }

            double requiredSpeed = distance / (timeInMinutes / 60.0);

            double totalPaceSec = (timeInMinutes * 60.0) / distance;
            int paceMin = (int) (totalPaceSec / 60);
            int paceSec = (int) Math.round(totalPaceSec % 60);

            String resultText = String.format(Locale.getDefault(),
                    "Dla dystansu %.2f km w czasie %.1f min:\n\n" +
                            "• Wymagane tempo: %d:%02d min/km\n" +
                            "• Wymagana prędkość: %.2f km/h",
                    distance, timeInMinutes, paceMin, paceSec, requiredSpeed);

            tvResults.setText(resultText);
        });
    }

    private void displayStandardResults(int paceSeconds, double speedKmh, double customDistance) {
        int paceMin = paceSeconds / 60;
        int paceSec = paceSeconds % 60;

        int marathonSeconds = (int) Math.round(paceSeconds * CONST_MARATHON);
        String marathonTime = formatTime(marathonSeconds);

        int halfMarathonSeconds = (int) Math.round(paceSeconds * CONST_HALF_MARATHON);
        String halfMarathonTime = formatTime(halfMarathonSeconds);

        String customDistanceResult = "";
        if (customDistance > 0) {
            int customSeconds = (int) Math.round(paceSeconds * customDistance);
            customDistanceResult = String.format(Locale.getDefault(),
                    "\n• Czas na dystansie %.2f km: %s", customDistance, formatTime(customSeconds));
        }

        String report = String.format(Locale.getDefault(),
                "• Obliczone tempo: %d:%02d min/km\n" +
                        "• Obliczona prędkość: %.2f km/h\n\n" +
                        "• Przewidywany czas ukończenia:\n" +
                        "  - Maraton (42.195 km): %s\n" +
                        "  - Półmaraton (21.0975 km): %s" +
                        "%s",
                paceMin, paceSec, speedKmh, marathonTime, halfMarathonTime, customDistanceResult);

        tvResults.setText(report);
    }

    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format(Locale.getDefault(), "%dg %dmin %ds", hours, minutes, seconds);
    }
}