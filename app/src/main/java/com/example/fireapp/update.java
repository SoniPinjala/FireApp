package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class update extends AppCompatActivity implements View.OnClickListener {
TextView t1,t2,t3;
CheckBox c1,c2,c3;
    private DatabaseReference ref;
    Users user;
    private String pass,name;
    boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Bundle b=getIntent().getExtras();
        t1=(TextView)findViewById(R.id.pid1);
        t2=(TextView)findViewById(R.id.pid2);
        t3=(TextView)findViewById(R.id.pid3);
        t1.setText(b.getString("Stid"));
        t2.setText(b.getString("Email"));
        t3.setText(b.getString("Name"));
        name=b.getString("Name").substring(0,b.getString("Name").indexOf('{')+1);
        c1 = (CheckBox) findViewById(R.id.checkid1);
        c2 = (CheckBox) findViewById(R.id.checkid2);
        c3 = (CheckBox) findViewById(R.id.checkid3);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        user = new Users();
    }

    public void onclickbuttonMethod(View view)
    {
        if(c1.isChecked())
        {
            name+=c1.getText().toString().trim()+",";
        }
        if(c2.isChecked())
        {
            name+=c2.getText().toString().trim()+",";
        }
        if(c3.isChecked())
        {
            name+=c3.getText().toString().trim()+",";
        }
        name=name.substring(0,name.length()-1);
        name+="}";
        t3.setText(name);
//        ref = ref.child(t1.getText().toString().substring(8));
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot)
//            {
//                Users temp = dataSnapshot.getValue(Users.class);
//                pass=temp.getPassword();
//                user.setStid(t1.getText().toString());
//                user.setEmail(t2.getText().toString());
//                user.setPassword(pass);
//                user.setName(name);
//                ref = FirebaseDatabase.getInstance().getReference().child("Users");
//                ref.child(user.getStid().substring(8)).setValue(user)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(update.this,
//                                            "Student Updated Successfully", Toast.LENGTH_SHORT).show();
//                                    Intent start = getIntent();
//                                    start.putExtra("Stid",user.getStid());
//                                    start.putExtra("Email",user.getEmail());
//                                    start.putExtra("Name",user.getName());
//                                    finish();
//                                    startActivity(start);
//                                }else{
//                                    Toast.makeText(update.this,
//                                            "failed...!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
    }

    @Override
    public void onClick(View view)
    {

    }
    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent start = new Intent(update.this, MainActivity.class);
            startActivity(start);
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to LogOut", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
