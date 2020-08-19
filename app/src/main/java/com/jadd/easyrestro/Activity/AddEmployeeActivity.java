package com.jadd.easyrestro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jadd.easyrestro.R;
import com.jadd.easyrestro.classes.Employee;

public class AddEmployeeActivity extends AppCompatActivity {

    EditText editText;
    Button addButton;
    String restroName;
    Employee employee;
    DatabaseReference employeeReference, ownerReference,getEmployeeReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        restroName = getIntent().getStringExtra("RESTAURANT_NAME");
        editText = findViewById(R.id.add_employee_edit_text);
        addButton = findViewById(R.id.add_employee_button);
        employeeReference = FirebaseDatabase.getInstance().getReference("Users").child("Employee");
        getEmployeeReference = FirebaseDatabase.getInstance().getReference("Users").child("Employee");
        ownerReference = FirebaseDatabase.getInstance().getReference("Users").child("Owner").child(FirebaseAuth.getInstance().getUid()).child("Restaurants").child(restroName);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = editText.getText().toString();
                employeeReference.child(id).child("assigned").setValue(true);
                ownerReference.child("Employee").child(id).child("uid").setValue(id);
                getEmployeeReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        employee = dataSnapshot.getValue(Employee.class);
                        if(employee!=null){
                            employee.setOwnerUid(FirebaseAuth.getInstance().getUid());
                            ownerReference.child("Employee").child(id).child("name").setValue(employee.getName());
                            getEmployeeReference.child(id).setValue(employee);
                            getEmployeeReference.child(id).child("Restaurants").child(restroName).child("name").setValue(restroName);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
