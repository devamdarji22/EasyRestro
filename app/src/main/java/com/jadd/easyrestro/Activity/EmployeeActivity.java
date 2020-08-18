package com.jadd.easyrestro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jadd.easyrestro.Adapter.EmployeeRecyclerViewAdapter;
import com.jadd.easyrestro.LoginAndSignUp.SignUpActivity;
import com.jadd.easyrestro.R;

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity implements EmployeeRecyclerViewAdapter.OnEmployeeNameClickListner {

    ArrayList<String> names;
    private RecyclerView recyclerView;
    private String restroName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        restroName = getIntent().getStringExtra("RESTAURANT_NAME");
        names = new ArrayList<>();
        recyclerView = findViewById(R.id.employee_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(EmployeeActivity.this));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_employee_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeActivity.this, AddEmployeeActivity.class);
                intent.putExtra("OWNER_FLAG" ,false);
                intent.putExtra("RESTAURANT_NAME",restroName);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onEmployeeClick(int pos) {

    }
}
