package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText studentID,pin;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentID = (EditText) findViewById(R.id.studentID);
        pin = (EditText) findViewById(R.id.pin);
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    String PIN;
    public void btnLogin_Click(View view) {

        PIN = pin.getText().toString();

        ref = ref.child(studentID.getText().toString());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Users user = dataSnapshot.getValue(Users.class);
                    if (PIN.equals(user.getPassword())) {
                        Toast.makeText(MainActivity.this,
                                "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent start = new Intent(MainActivity.this, info_activity.class);
                        start.putExtra("Stid",user.getStid());
                        start.putExtra("Email",user.getEmail());
                        start.putExtra("Name",user.getName());
                        startActivity(start);
                    }
                    else {
                        Toast.makeText(MainActivity.this,
                                "Enter Correct Pin...!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,
                            "Error logging in...!", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public void btnRegister_Click(View view) {
        Intent signUp = new Intent(MainActivity.this, SignUp.class);
        startActivity(signUp);
    }

    @Override
    public void onBackPressed()
    {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
    }
}


