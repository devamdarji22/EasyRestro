package com.jadd.easyrestro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    TextView menuText , addCategoryText, addItemText, addTableText;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        context = getApplicationContext();

        menuText = findViewById(R.id.menu);
        addItemText = findViewById(R.id.addItem);
        addCategoryText = findViewById(R.id.addCategory);
        addTableText = findViewById(R.id.addTable);

        menuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , MenuActivity.class);
                startActivity(intent);
            }
        });

        addItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , AddItem.class);
                startActivity(intent);
            }
        });

        addCategoryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , AddCategory.class);
                startActivity(intent);
            }
        });

        addTableText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , AddTable.class);
                startActivity(intent);
            }
        });

    }
}
