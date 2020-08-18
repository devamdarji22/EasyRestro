package com.jadd.easyrestro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.jadd.easyrestro.LoginAndSignUp.FirstActivity;
import com.jadd.easyrestro.R;

public class EmployeeWaitActivity extends AppCompatActivity {

    boolean ownerFlag;
    Button id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_wait);
        ownerFlag = getIntent().getBooleanExtra("OWNER_FLAG",false);
        id = findViewById(R.id.employee_copy_id);
        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("ID", FirebaseAuth.getInstance().getUid());
                clipboard.setPrimaryClip(clip);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.owner_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_owner_sign_out){
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(EmployeeWaitActivity.this, FirstActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
