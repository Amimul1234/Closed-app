package com.rahi.e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword, InputConfirmPassword;
    private TextView termsConditions;
    private ProgressDialog loadingbar;
    private ImageView newCustomerPic,showPin, showConfirmPin;
    private Boolean isShowPin = false, isShowConfirmPin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountButton = findViewById(R.id.create_account_btn);
        InputName = findViewById(R.id.new_customer_name);
        //InputPhoneNumber = findViewById(R.id.register_phone_number_input);
        InputPassword = findViewById(R.id.new_customer_pin);
        InputConfirmPassword = findViewById(R.id.new_customer_confirm_pin);
        termsConditions=(TextView)findViewById(R.id.terms_conditions);
        showPin=(ImageView)findViewById(R.id.show_pin);
        showConfirmPin=(ImageView)findViewById(R.id.show_confirmed_pin);
        newCustomerPic=(ImageView)findViewById(R.id.new_customer_pic);
        loadingbar = new ProgressDialog(this);

        termsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,TermsConditionsActivity.class);
                startActivity(intent);
            }
        });

        showPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isShowPin) {
                    InputPassword.setTransformationMethod(new PasswordTransformationMethod());
                    showPin.setImageResource(R.drawable.ic_visibility_off);
                    isShowPin = false;

                }else{
                    InputPassword.setTransformationMethod(null);
                    showPin.setImageResource(R.drawable.ic_visibility);
                    isShowPin = true;
                }
            }
        });

        showConfirmPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isShowConfirmPin) {
                    InputConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    showConfirmPin.setImageResource(R.drawable.ic_visibility_off);
                    isShowConfirmPin = false;

                }else{
                    InputConfirmPassword.setTransformationMethod(null);
                    showConfirmPin.setImageResource(R.drawable.ic_visibility);
                    isShowConfirmPin = true;
                }
            }
        });

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount()
    {

        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if(name.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please write your name....", Toast.LENGTH_SHORT).show();
        }

        else if(phone.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please write your phone number....", Toast.LENGTH_SHORT).show();
        }

        else if(password.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please write down password....", Toast.LENGTH_SHORT).show();
        }

        else
        {
            loadingbar.setTitle("Create Account");
            loadingbar.setMessage("Please wait while we are checking the credentials");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            ValidatephoneNumber(name, phone, password);
        }

    }

    private void ValidatephoneNumber(final String name, final String phone, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Users").child(phone).exists()))//For checking existing users.......
                {
                    HashMap <String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Congratulations ! Your account created successfully", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }

                            else
                            {
                                loadingbar.dismiss();
                                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Phone number already exists", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(getApplicationContext(), "Please try again with another number", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}