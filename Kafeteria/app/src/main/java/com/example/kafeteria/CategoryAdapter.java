package com.example.kafeteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Item> {

    public CategoryAdapter(Context context, List<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, parent, false);
        }

        Item item = getItem(position);

        ImageView ivThumb = convertView.findViewById(R.id.ivItemThumb);
        TextView tvTitle = convertView.findViewById(R.id.tvItemTitle);
        TextView tvDesc = convertView.findViewById(R.id.tvItemDesc);
        TextView tvPrice = convertView.findViewById(R.id.tvItemPrice);

        tvTitle.setText(item.getName());
        tvDesc.setText(item.getDetails1());
        tvPrice.setText(item.getDetails2());

        Glide.with(getContext())
                .load(item.getImageUrl())
                .placeholder(R.drawable.logo_kafeteria)
                .into(ivThumb);

        return convertView;
    }
}
