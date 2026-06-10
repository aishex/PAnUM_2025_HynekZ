package com.example.kafeteria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getReadableDatabase().close();

        Button btnDrinks = findViewById(R.id.btnDrinks);
        Button btnSnacks = findViewById(R.id.btnSnacks);
        Button btnLocations = findViewById(R.id.btnLocations);
        Button btnCart = findViewById(R.id.btnCart);

        btnDrinks.setOnClickListener(v -> openCategory("Napoje"));
        btnSnacks.setOnClickListener(v -> openCategory("Przekąski"));
        btnLocations.setOnClickListener(v -> openCategory("Lista lokali"));
        btnCart.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartActivity.class)));
    }

    private void openCategory(String categoryName) {
        Intent intent = new Intent(MainActivity.this, CategoryListActivity.class);
        intent.putExtra(CategoryListActivity.EXTRA_CATEGORY, categoryName);
        startActivity(intent);
    }
}
