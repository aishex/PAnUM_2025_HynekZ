package com.example.kafeteria;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> items;

    private CartManager() {
        items = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(Item item) {
        for (CartItem cartItem : items) {
            if (cartItem.getItem().getId() == item.getId() && cartItem.getItem().getName().equals(item.getName())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                return;
            }
        }
        items.add(new CartItem(item, 1));
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void clearCart() {
        items.clear();
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem cartItem : items) {
            String priceStr = cartItem.getItem().getDetails2();
            if (priceStr != null && priceStr.contains("PLN")) {
                try {
                    String numberStr = priceStr.replace(" PLN", "").replace(",", ".");
                    double price = Double.parseDouble(numberStr);
                    total += price * cartItem.getQuantity();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return total;
    }
}
