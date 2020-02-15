package com.jadd.easyrestro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteTableLockedException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AddTable extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText tableNumberEditText;
    Button button;
    Table table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_table);

        tableNumberEditText = findViewById(R.id.table_number);

        databaseReference = FirebaseDatabase.getInstance().getReference("Table");

        button = findViewById(R.id.add_table_button);
        //Toast.makeText(AddTable.this, "Table Added", Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tableN = tableNumberEditText.getText().toString().trim();
                if(!TextUtils.isEmpty(tableN)) {
                    int tableNumber = Integer.parseInt(tableNumberEditText.getText().toString());
                    table = new Table(tableNumber, true);
                    databaseReference.child(Integer.toString(tableNumber)).setValue(table);
                    tableNumberEditText.setText("");
                    Toast.makeText(AddTable.this, "Table Added", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddTable.this, "Table not Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
