package com.jadd.easyrestro.LoginAndSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jadd.easyrestro.Activity.EmployeeRestaurantActivity;
import com.jadd.easyrestro.Activity.EmployeeWaitActivity;
import com.jadd.easyrestro.Activity.MainActivity;
import com.jadd.easyrestro.Activity.RestaurantActivity;
import com.jadd.easyrestro.R;

public class LoginActivity extends AppCompatActivity {

    EditText nameField,passwordField;
    TextView signUpView;
    Button loginButton,signUpButton;
    String ownerName, ownerPassword;
    DatabaseReference loginRef;
    boolean ownerFlag;
    private FirebaseAuth auth;
    boolean assigned;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ownerFlag = getIntent().getBooleanExtra("OWNER_FLAG",true);
        nameField = findViewById(R.id.user_name);
        passwordField = findViewById(R.id.user_password);
        loginButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.sign_up_button);
        signUpView = findViewById(R.id.register_text_view);
        auth = FirebaseAuth.getInstance();
        if(!ownerFlag) {
            loginRef = FirebaseDatabase.getInstance().getReference("Users").child("Employee").child(FirebaseAuth.getInstance().getUid()).child("assigned");
        }
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){

                    if(ownerFlag){
                        Intent intent;
                        intent = new Intent(LoginActivity.this, RestaurantActivity.class);
                        startActivity(intent);
                    }
                    else {

                        loginRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                assigned = dataSnapshot.getValue(Boolean.class);
                                Intent intent;
                                if(assigned){
                                    intent = new Intent(LoginActivity.this, EmployeeRestaurantActivity.class);
                                }
                                else {
                                    intent = new Intent(LoginActivity.this, EmployeeWaitActivity.class);
                                }
                                intent.putExtra("OWNER_FLAG",false);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                        //Toast.makeText(LoginActivity.this, "Employee", Toast.LENGTH_SHORT).show();
                        //intent = new Intent(LoginActivity.this,RestaurantActivity.class);
                    }
                    //
                }
            }
        };

        //signUpVisibility(ownerFlag);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                intent.putExtra("OWNER_FLAG",ownerFlag);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ownerName = nameField.getText().toString();
                ownerPassword = passwordField.getText().toString();
                if(TextUtils.isEmpty(ownerName)||TextUtils.isEmpty(ownerPassword)){
                    Toast.makeText(LoginActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.signInWithEmailAndPassword(ownerName, ownerPassword)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user1's information
                                        FirebaseUser user = auth.getCurrentUser();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    private void signUpVisibility(boolean ownerFlag) {
        if(ownerFlag==true){
            signUpView.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.VISIBLE);
        }
        else {
            signUpButton.setVisibility(View.GONE);
            signUpView.setVisibility(View.GONE);
        }
    }
}
