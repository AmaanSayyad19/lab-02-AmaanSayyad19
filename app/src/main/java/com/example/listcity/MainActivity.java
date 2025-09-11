package com.example.listcity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        Button addButton = findViewById(R.id.button);
        Button deleteButton = findViewById(R.id.button2);

        String[] cities = {"Edmonton","Vancouver","Calgary","Ottawa","Montreal","Moscow","Sydney","Berlin","Tokyo","Toronto"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, R.id.content_view, dataList);
        cityList.setAdapter(cityAdapter);

        // Save which item is selected
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            Toast.makeText(this, "Selected: " + dataList.get(position), Toast.LENGTH_SHORT).show();
        });

        // ADD city
        addButton.setOnClickListener(v -> showAddCityDialog());

        // DELETE city
        deleteButton.setOnClickListener(v -> {
            if (selectedPosition >= 0) {
                dataList.remove(selectedPosition);
                cityAdapter.notifyDataSetChanged();
                selectedPosition = -1; // reset selection
            } else {
                Toast.makeText(this, "Select a city first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddCityDialog() {
        EditText input = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add City")
                .setMessage("Enter city name:")
                .setView(input)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    String cityName = input.getText().toString().trim();
                    if (!cityName.isEmpty()) {
                        dataList.add(cityName);
                        cityAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
