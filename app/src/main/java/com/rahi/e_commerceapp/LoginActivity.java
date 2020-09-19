package com.rahi.e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rahi.e_commerceapp.Model.Users;
import com.rahi.e_commerceapp.Prevalent.Prevalent;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText InputNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingbar;
    private CheckBox rememberMe;
    private ImageView visibility;
    private Boolean isShowPin = false;
    private TextView ForgetPin, SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = findViewById(R.id.login_btn);
        InputNumber = findViewById(R.id.customer_mobile);
        InputPassword = findViewById(R.id.customer_pin);
        SignUp=(TextView)findViewById(R.id.sign_up);
        ForgetPin=(TextView)findViewById(R.id.forget_pin);
        rememberMe = findViewById(R.id.remember_me);
        visibility = findViewById(R.id.show_pin);
        loadingbar = new ProgressDialog(this);
        Paper.init(this);

        ForgetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //it will added later
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterWithOtp.class);
                startActivity(intent);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isShowPin) {
                    InputPassword.setTransformationMethod(new PasswordTransformationMethod());
                    visibility.setImageResource(R.drawable.ic_visibility_off);
                    isShowPin = false;

                }else{
                    InputPassword.setTransformationMethod(null);
                    visibility.setImageResource(R.drawable.ic_visibility);
                    isShowPin = true;
                }
            }
        });

    }

    private void LoginUser() {

        String phone = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if(phone.isEmpty())
        {
            InputNumber.setError("Please enter your mobile number");
            InputNumber.requestFocus();
        }

        else if(phone.length() != 11)
        {
            InputNumber.setError("Please enter a valid mobile number");
            InputNumber.requestFocus();
        }

        else if(password.isEmpty())
        {
            InputPassword.setError("Please enter your pin");
            InputPassword.requestFocus();
        }

        else
        {
            loadingbar.setTitle("Login Account");
            loadingbar.setMessage("Please wait while we are checking the credentials....");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            AllowAccessToAccount(phone, password);
        }
    }

    private void AllowAccessToAccount(final String phone, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        final Query query = RootRef.child("Users").orderByChild("phone").equalTo(phone);//Checking for user

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    Users users = dataSnapshot.child(phone).getValue(Users.class);

                    if(users.getPhone().equals(phone))//If admin disables the phone number......
                    {
                        if(users.getPassword().equals(password))
                        {

                            if(rememberMe.isChecked())//Writing user data on android storage
                            {
                                Paper.book().write(Prevalent.UserPhoneKey, phone);
                                Paper.book().write(Prevalent.UserPasswordKey, password);
                            }

                            Toast.makeText(getApplicationContext(), "Log in successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            Prevalent.currentOnlineUser = users;
                            startActivity(intent);
                        }

                        else
                        {
                            Toast.makeText(LoginActivity.this, "Please enter correct pin number", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                    }
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Account with this phone number do not exists", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}