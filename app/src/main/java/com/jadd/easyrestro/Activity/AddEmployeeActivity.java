package com.jadd.easyrestro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jadd.easyrestro.R;

public class AddEmployeeActivity extends AppCompatActivity {

    EditText editText;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        editText = findViewById(R.id.add_employee_edit_text);
        addButton = findViewById(R.id.add_employee_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editText.getText().toString();
            }
        });

    }
}
