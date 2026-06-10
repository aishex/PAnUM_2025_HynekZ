package com.example.kafeteria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ListView lvCartItems = findViewById(R.id.lvCartItems);
        TextView tvCartTotal = findViewById(R.id.tvCartTotal);
        Button btnOrder = findViewById(R.id.btnOrder);

        List<CartItem> cartItems = CartManager.getInstance().getItems();
        List<String> displayList = new ArrayList<>();
        
        for (CartItem ci : cartItems) {
            displayList.add(ci.getItem().getName() + " x" + ci.getQuantity() + " - " + ci.getItem().getDetails2());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        lvCartItems.setAdapter(adapter);

        double total = CartManager.getInstance().getTotalPrice();
        tvCartTotal.setText(String.format("Łączna kwota: %.2f PLN", total));

        btnOrder.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Koszyk jest pusty!", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(CartActivity.this, TimerActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
