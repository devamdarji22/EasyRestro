package com.jadd.easyrestro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartAddItemActivity extends AppCompatActivity implements RecyclerViewAdapter.OnQuanListner {

    Spinner spinner;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    String category;
    DatabaseReference databaseReference,dataBase,tableNumberReference;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    ArrayList<Cart> items = new ArrayList<Cart>();
    String itemName;
    Long itemPrice;
    //int quan;
    Item item1;
    long id;
    Cart cart;
    Button goButton;
    private String tableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_add_item);
        tableNumber = getIntent().getStringExtra("TABLE_NUMBER");
        spinner = findViewById(R.id.category_spinner_add_item_cart);

        goButton = findViewById(R.id.go_button);
        list = new ArrayList<>();
        spinnerData();

        recyclerView = findViewById(R.id.category_recyclerView_add_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartAddItemActivity.this));

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.clear();
                Toast.makeText(CartAddItemActivity.this, "Go Clicked!", Toast.LENGTH_SHORT).show();
                category = String.valueOf(spinner.getSelectedItem());
                databaseReference = FirebaseDatabase.getInstance()
                        .getReference("Category").child(category).child("Items");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot item: dataSnapshot.getChildren()){
                            item1 = item.getValue(Item.class);

                            cart = new Cart(category,item1);

                            items.add(cart);
                        }
                        recyclerViewAdapter = new RecyclerViewAdapter(CartAddItemActivity.this,items,category,CartAddItemActivity.this);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    private void spinnerData() {

        dataBase = FirebaseDatabase.getInstance().getReference("Category");
        dataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot item: dataSnapshot.getChildren()){
                    //Category category = item.getKey();
                    list.add(item.getKey());

                }
                adapter = new ArrayAdapter<>(CartAddItemActivity.this,android.R.layout.select_dialog_item,list);
                spinner.setAdapter(adapter);
                //Toast.makeText(AddItem.this, "Add", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onQuanClick(int position,int quan) {
        tableNumberReference = FirebaseDatabase.getInstance().getReference("Table").child(tableNumber).child("Cart");
        String category = items.get(position).getCategory();
        String itemName = items.get(position).getItem().getName();
        if(quan==0){
            tableNumberReference.child(category).child(itemName).removeValue();
            return;
        }
        items.get(position).setQuantity(quan);

        tableNumberReference.child(category).child(itemName).setValue(items.get(position));
    }
}
