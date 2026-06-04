package com.example.konwerter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private Button btn;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.inputNumber);
        btn = findViewById(R.id.convertButton);
        result = findViewById(R.id.resultText);

        btn.setOnClickListener(v -> {
            String text = input.getText().toString().trim();

            if (text.isEmpty()) {
                result.setText("Wpisz liczbę");
                return;
            }

            try {
                int num = Integer.parseInt(text);

                if (num < 1 || num > 3999) {
                    result.setText("Zakres: 1 - 3999");
                    return;
                }

                result.setText(toRoman(num));
            } catch (NumberFormatException e) {
                result.setText("Niepoprawna liczba");
            }
        });
    }

    private String toRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                sb.append(symbols[i]);
            }
        }
        return sb.toString();
    }
}