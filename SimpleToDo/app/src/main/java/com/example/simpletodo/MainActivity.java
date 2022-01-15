package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemsAdapter.OnLongClickListener {

    List<String> items;
    ItemsAdapter itemsAdapter;

    Button addButtonView;
    EditText editTextItemView;
    RecyclerView rvItemsRecyclerView;

    private void initViewRefs() {
        addButtonView = findViewById(R.id.buttonAdd);
        editTextItemView = findViewById(R.id.editTextItem);
        rvItemsRecyclerView = findViewById(R.id.rvItems);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewRefs();

        loadItems();

        itemsAdapter = new ItemsAdapter(items, this);
        rvItemsRecyclerView.setAdapter(itemsAdapter);
        rvItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        addButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.add(editTextItemView.getText().toString());
                itemsAdapter.notifyItemInserted(items.size() - 1);
                editTextItemView.setText("");
                Toast.makeText(
                        getApplicationContext(),
                        R.string.item_was_added_toast,
                        Toast.LENGTH_SHORT
                ).show();
                saveItems();
            }
        });
    }

    @Override
    public void onItemLongClicked(int position) {
        items.remove(position);
        itemsAdapter.notifyItemRemoved(position);
        saveItems();
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}