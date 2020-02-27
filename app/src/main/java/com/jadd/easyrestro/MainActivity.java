package com.jadd.easyrestro;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MainRecyclerViewAdapter.OnTableNumberListner {

    RecyclerView recyclerView;
    MainRecyclerViewAdapter adapter;
    ArrayList<String> tableNumber;
    DatabaseReference databaseTableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseTableNumber = FirebaseDatabase.getInstance().getReference("Table");

        recyclerView = findViewById(R.id.recycler_view_main);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,4));
        tableNumber = new ArrayList<String>();
        databaseTableNumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item: dataSnapshot.getChildren()){
                    //Category category = item.getKey();
                    if((boolean) item.child("empty").getValue().equals(false)) {
                        tableNumber.add(item.getKey());
                    }
                }
                //spinnerAdapter = new ArrayAdapter<>(NewOrderActivity.this,android.R.layout.select_dialog_item,spinnerList);
                //tableNumberSpinner.setAdapter(spinnerAdapter);

                adapter = new MainRecyclerViewAdapter(MainActivity.this,tableNumber,MainActivity.this);

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NewOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTableNumberClick(int position) {
        Intent intent = new Intent(this,OrderActivity.class);
        String table_number = tableNumber.get(position);
        //Toast.makeText(this, table_number, Toast.LENGTH_SHORT).show();

        intent.putExtra("TABLE_NUMBER",table_number);
        startActivity(intent);
    }
}
