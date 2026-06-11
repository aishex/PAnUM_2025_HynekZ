package com.example.kafeteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends ArrayAdapter<CartItem> {

    private Runnable onChange;

    public CartAdapter(Context context, List<CartItem> items, Runnable onChange) {
        super(context, 0, items);
        this.onChange = onChange;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart, parent, false);
        }

        CartItem item = getItem(position);

        TextView tvName = convertView.findViewById(R.id.tvCartItemName);
        TextView tvQuantity = convertView.findViewById(R.id.tvCartItemQuantity);
        Button btnMinus = convertView.findViewById(R.id.btnMinus);
        Button btnPlus = convertView.findViewById(R.id.btnPlus);

        tvName.setText(item.getItem().getName() + " (" + item.getItem().getDetails2() + ")");
        tvQuantity.setText(String.valueOf(item.getQuantity()));

        btnMinus.setOnClickListener(v -> {
            int q = item.getQuantity();
            if (q > 1) {
                item.setQuantity(q - 1);
            } else {
                remove(item);
            }
            notifyDataSetChanged();
            if (onChange != null) onChange.run();
        });

        btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyDataSetChanged();
            if (onChange != null) onChange.run();
        });

        return convertView;
    }
}
