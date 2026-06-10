package com.example.kafeteria;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.Toast;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM = "item_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ivDetailImage = findViewById(R.id.ivDetailImage);
        TextView tvDetailName = findViewById(R.id.tvDetailName);
        TextView tvDetailDesc = findViewById(R.id.tvDetailDesc);
        TextView tvDetailPrice = findViewById(R.id.tvDetailPrice);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);

        Item item = (Item) getIntent().getSerializableExtra(EXTRA_ITEM);

        if (item != null) {
            Glide.with(this)
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.logo_kafeteria)
                    .into(ivDetailImage);
            
            tvDetailName.setText(item.getName());
            tvDetailDesc.setText(item.getDetails1());
            tvDetailPrice.setText(item.getDetails2());

            if (item.getDetails2() != null && !item.getDetails2().contains("PLN")) {
                btnAddToCart.setVisibility(android.view.View.GONE);
            }

            btnAddToCart.setOnClickListener(v -> {
                CartManager.getInstance().addItem(item);
                Toast.makeText(DetailActivity.this, "Dodano do koszyka!", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
