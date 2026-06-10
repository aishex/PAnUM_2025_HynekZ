package com.example.kafeteria;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY = "category_extra";

    private ListView listView;
    private TextView tvCategoryTitle;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        listView = findViewById(R.id.listView);
        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        dbHelper = new DatabaseHelper(this);

        String category = getIntent().getStringExtra(EXTRA_CATEGORY);
        if (category == null) category = "Napoje";
        
        tvCategoryTitle.setText(category);

        List<Item> items = loadDataFromDatabase(category);

        CategoryAdapter adapter = new CategoryAdapter(this, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Item selectedItem = (Item) parent.getItemAtPosition(position);
            Intent intent = new Intent(CategoryListActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_ITEM, selectedItem);
            startActivity(intent);
        });
    }

    private List<Item> loadDataFromDatabase(String category) {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            if (category.equals("Napoje")) {
                cursor = db.query(DatabaseHelper.TABLE_NAPOJE, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
                    String nazwa = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAZWA));
                    String opis = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_OPIS));
                    double cena = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CENA));
                    String urlZdjecia = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_URL_ZDJECIA));
                    list.add(new Item(id, nazwa, opis, String.format("%.2f PLN", cena), urlZdjecia));
                }
            } else if (category.equals("Przekąski")) {
                cursor = db.query(DatabaseHelper.TABLE_PRZEKASKI, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
                    String nazwa = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAZWA));
                    String opis = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_OPIS));
                    double cena = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CENA));
                    String urlZdjecia = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_URL_ZDJECIA));
                    list.add(new Item(id, nazwa, opis, String.format("%.2f PLN", cena), urlZdjecia));
                }
            } else if (category.equals("Lista lokali")) {
                cursor = db.query(DatabaseHelper.TABLE_LOKALE, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
                    String nazwa = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAZWA));
                    String adres = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ADRES));
                    String godziny = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_GODZINY));
                    String urlZdjecia = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_URL_ZDJECIA));
                    list.add(new Item(id, nazwa, adres, "Otwarte: " + godziny, urlZdjecia));
                }
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }
}
