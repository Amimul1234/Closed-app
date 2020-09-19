package com.rahi.e_commerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterWithOtp extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_with_otp);

        editText = findViewById(R.id.customer_register_mobile);

        findViewById(R.id.send_otp_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 11) {
                    editText.setError("Please enter a valid number");
                    editText.requestFocus();
                    return;
                }

                //For checking existing user in the database

                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();

                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (!(dataSnapshot.child("Users").child(number).exists()))//For checking existing users.......
                        {
                            String phoneNumber = "+" + "88" + number;

                            Intent intent = new Intent(RegisterWithOtp.this, VerifyPhoneActivity.class);
                            intent.putExtra("phonenumber", phoneNumber);
                            startActivity(intent);
                        }

                        else {

                            Toast.makeText(getApplicationContext(), "Phone number already exists", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
         });
    }
}