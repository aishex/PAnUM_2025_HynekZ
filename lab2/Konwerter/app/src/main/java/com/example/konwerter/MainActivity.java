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
            String text = input.getText().toString().trim().toUpperCase();

            if (text.isEmpty()) {
                result.setText("Wpisz wartość");
                return;
            }

            if (text.matches("\\d+")) {
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
            }
            else if (text.matches("^[IVXLCDM]+$")) {
                int arabicNum = toArabic(text);
                result.setText(String.valueOf(arabicNum));
            }
            else {
                result.setText("Niepoprawny format. Wpisz cyfry lub znaki rzymskie.");
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

    private int toArabic(String roman) {
        int total = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = getRomanValue(roman.charAt(i));

            if (currentValue < prevValue) {
                total -= currentValue;
            } else {
                total += currentValue;
            }
            prevValue = currentValue;
        }
        return total;
    }

    private int getRomanValue(char romanChar) {
        switch (romanChar) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }
}