package com.jadd.easyrestro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddItem extends AppCompatActivity {

    EditText itemName,itemPrice;
    Spinner spinner;
    Button button;
    ValueEventListener valueEventListener;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    DatabaseReference databaseReference,databaseItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        databaseReference = FirebaseDatabase.getInstance().getReference("category");

        itemName = findViewById(R.id.item_name_edit_text);
        itemPrice = findViewById(R.id.item_price_edit_text);
        button = findViewById(R.id.add_item_button);
        spinner = findViewById(R.id.spinner);

        list = new ArrayList<>();

        retrievedata();

        databaseItem = FirebaseDatabase.getInstance().getReference("item");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

    }

    private void addItem() {
        String item = itemName.getText().toString().trim();
        int price = Integer.parseInt(itemPrice.getText().toString().trim());
        String category = spinner.getSelectedItem().toString();

        if(!TextUtils.isEmpty(item)){
            String id = databaseReference.push().getKey();
            Item item1 = new Item(item,category,price);
            databaseItem.child(id).setValue(item1);
            Toast.makeText(this, "Item added!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Item not addded!",Toast.LENGTH_SHORT).show();
        }
        itemName.setText("");
        itemPrice.setText("");
    }

    public void retrievedata(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot item: dataSnapshot.getChildren()){
                    Category category = item.getValue(Category.class);
                    list.add(category.getName());

                }
                adapter = new ArrayAdapter<>(AddItem.this,android.R.layout.select_dialog_item,list);
                spinner.setAdapter(adapter);
                //Toast.makeText(AddItem.this, "Add", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
