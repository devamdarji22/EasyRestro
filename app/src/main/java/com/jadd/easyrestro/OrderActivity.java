package com.jadd.easyrestro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements RecyclerViewAdapter.OnQuanListner{


    RecyclerView cartRecyclerView;
    RecyclerViewAdapter cartRecyclerViewAdapter;
    Button addItemButton,checkOutButton;
    TextView cartSubTotal,cartTotal,cartTax,cartDiscount;
    DatabaseReference databaseTableNumber,tableNumberReference,ref;
    String tableNumber;
    ArrayList<Cart> items = new ArrayList<Cart>();
    Cart cart;
    int subTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        tableNumber = getIntent().getStringExtra("TABLE_NUMBER");

        databaseTableNumber = FirebaseDatabase.getInstance().getReference("Table").child(tableNumber).child("Cart");
        ref = FirebaseDatabase.getInstance().getReference("Table");

        cartDiscount = findViewById(R.id.cart_discount);
        cartTax = findViewById(R.id.cart_tax);
        cartTotal = findViewById(R.id.amount_total);
        cartSubTotal = findViewById(R.id.amount_subtotal);
        addItemButton = findViewById(R.id.order_add_item_button);
        checkOutButton = findViewById(R.id.order_checkout_button);
        cartRecyclerView = findViewById(R.id.order_cart_recycler_view);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));


        databaseTableNumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    items.clear();
                    subTotal = 0;
                    if(dataSnapshot==null){
                        return;
                    }
                    else {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            if(item.exists()) {
                                for (DataSnapshot item1 : item.getChildren()) {
                                    if (!item1.getValue().equals(null)||!item1.getValue().equals("0")) {
                                        cart = item1.getValue(Cart.class);
                                        items.add(cart);
                                    }
                                }
                            }
                        }
                        cartRecyclerViewAdapter = new RecyclerViewAdapter(OrderActivity.this, items, OrderActivity.this);
                        cartRecyclerView.setAdapter(cartRecyclerViewAdapter);
                    }
                    if(items.size()==0){
                        cartSubTotal.setText("SubTotal - 0");
                    }
                    else {
                        for(int i = 0;i<items.size();i++){
                            subTotal = (int) (subTotal + ((items.get(i).getQuantity())*(items.get(i).getItem().getPrice())));
                            cartSubTotal.setText("SubTotal - "+subTotal);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this,CartAddItemActivity.class);
                intent.putExtra("TABLE_NUMBER",tableNumber);
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //i have changed
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            ref.child(tableNumber).child("empty").setValue(true);
            databaseTableNumber.child(tableNumber).child("Cart").removeValue();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onQuanClick(int position, int quan) {
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
