package com.jadd.easyrestro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    EditText nameField, phoneNumberField;
    Spinner tableNumberSpinner;
    LinearLayout cartLayout;
    Button addItemButton,checkOutButton;
    DatabaseReference databaseTableNumber;
    ArrayList<String> spinnerList;
    ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        databaseTableNumber = FirebaseDatabase.getInstance().getReference("Table");
        nameField = findViewById(R.id.order_name_field);
        phoneNumberField = findViewById(R.id.order_phone_number_field);
        tableNumberSpinner = findViewById(R.id.order_table_number_spinner);
        addItemButton = findViewById(R.id.order_add_item_button);
        checkOutButton = findViewById(R.id.order_checkout_button);
        cartLayout = findViewById(R.id.order_cart);

        spinnerList = new ArrayList<>();

        spinnerData();

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this,CartAddItemActivity.class);
                startActivity(intent);
            }
        });

    }

    private void spinnerData() {

        databaseTableNumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item: dataSnapshot.getChildren()){
                    //Category category = item.getKey();
                    spinnerList.add(item.getKey());

                }
                spinnerAdapter = new ArrayAdapter<>(OrderActivity.this,android.R.layout.select_dialog_item,spinnerList);
                tableNumberSpinner.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
