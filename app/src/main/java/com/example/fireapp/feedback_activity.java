package com.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class feedback_activity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditTextSubject;
    private EditText mEditTextMessage;
    String mails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_activity);
        Bundle b = getIntent().getExtras();
        mails = b.getString("mails");
        Toast.makeText(this, mails, Toast.LENGTH_LONG).show();

        mEditTextSubject = findViewById(R.id.edit_text_subject);
        mEditTextMessage = findViewById(R.id.edit_text_message);

        Button buttonSend = findViewById(R.id.sendmail_id);
        buttonSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        sendMail();
    }
//
    private void sendMail() {
        String[] recipients = {mails};

        String subject = mEditTextSubject.getText().toString();
        String message = mEditTextMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }

}