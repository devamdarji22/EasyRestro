package com.jadd.easyrestro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartAddItemActivity extends AppCompatActivity {

    Spinner spinner;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    String category;
    DatabaseReference databaseReference,dataBase;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    ArrayList<Cart> items = new ArrayList<Cart>();
    String itemName;
    Long itemPrice;
    int quan;
    Item item1;
    long id;
    Cart cart;
    Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_add_item);

        spinner = findViewById(R.id.category_spinner_add_item_cart);

        goButton = findViewById(R.id.go_button);
        list = new ArrayList<>();
        spinnerData();


        recyclerView = findViewById(R.id.category_recyclerView_add_cart);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = String.valueOf(spinner.getSelectedItem());
                databaseReference = FirebaseDatabase.getInstance()
                        .getReference("Category").child(category).child("Items");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot item: dataSnapshot.getChildren()){
                            //Category category = item.getKey();
                            //item1 = (Item) item.getValue();
                            itemName = (String) item.child("name").getValue();
                            itemPrice = Long.valueOf((Long) item.child("price").getValue());
                            String TAG = "Hello";
                            Log.d(TAG, "onDataChange: " + itemPrice);
                            id = (long) item.child("id").getValue();
                            item1 = new Item(itemName,itemPrice,id);
                            cart = new Cart(category,item1,0);
                            //item1 = new Item("Devam",100,1);
                            //cart = new Cart("Human",item1,0);
                            items.add(cart);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                item1 = new Item("Devam",100,1);
                cart = new Cart("Human",item1,0);
                items.add(cart);
                items.add(cart);

            }
        });






        recyclerViewAdapter = new RecyclerViewAdapter(CartAddItemActivity.this,items,"abc");
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
}
