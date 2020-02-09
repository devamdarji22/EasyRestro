package com.jadd.easyrestro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCategory extends AppCompatActivity {

    EditText categoryNameEditTExt;
    Button button;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        databaseReference = FirebaseDatabase.getInstance().getReference("category");

        categoryNameEditTExt = findViewById(R.id.category_edit_text);
        button = findViewById(R.id.add_category_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategory();
            }
        });

    }

    private void addCategory(){

        String categoryName = categoryNameEditTExt.getText().toString().trim();

        if(!TextUtils.isEmpty(categoryName)){
            String id = databaseReference.push().getKey();
            Category category = new Category(categoryName);
            databaseReference.child(id).setValue(category);
            Toast.makeText(this, "Category added!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Category not addded!",Toast.LENGTH_SHORT).show();
        }
    }
}
