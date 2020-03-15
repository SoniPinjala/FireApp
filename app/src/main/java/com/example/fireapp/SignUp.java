package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    EditText stdID,password,email,name;
    private Users user;
    FirebaseDatabase database;
    DatabaseReference ref,ref1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        stdID = (EditText)findViewById(R.id.studentID);
        password = (EditText)findViewById(R.id.passwordid);
        email = (EditText)findViewById(R.id.emailid);
        name= (EditText)findViewById(R.id.nameid);
        user = new Users();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Users");
        ref1=ref;
    }

    public void btnRegister_Click(View view) {
        if(stdID.getText().toString().isEmpty()||password.getText().toString().isEmpty()||email.getText().toString().isEmpty()||name.getText().toString().isEmpty())
        {
            Toast.makeText(SignUp.this,
                    "Please fill all entries", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isValid(email.getText().toString().toLowerCase()))
        {
            Toast.makeText(this,"Please enter a valid email_ID", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isValidR(stdID.getText().toString().toLowerCase()))
        {
            Toast.makeText(this,"Proper example: cse17112", Toast.LENGTH_SHORT).show();
            return;
        }
        final String str="cb.en.u4"+stdID.getText().toString().toLowerCase();
        ref1=ref1.child(stdID.getText().toString().toLowerCase());
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Toast.makeText(SignUp.this,
                            str+" already exists", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
                else
                {
                    user.setStid(str);
                    user.setEmail(email.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setName(name.getText().toString());

                    ref.child(user.getStid().substring(8)).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUp.this,
                                                "Student Added Successfully", Toast.LENGTH_SHORT).show();
                                        Intent start = new Intent(SignUp.this, info_activity.class);
                                        startActivity(start);
                                    }else{
                                        Toast.makeText(SignUp.this,
                                                "failed...!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    static boolean isValid(String email)
    {
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    static boolean isValidR(String email)
    {
        String emailRegex="cse[0-9][0-9][0-9][0-9][0-9]";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
