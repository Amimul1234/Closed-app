package com.rahi.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity{

    private ImageView tshirts, sportsTshirts, femaleDresses, sweters,
    glasses, hatsCaps, walletsBagsPurses, shoes, headPhoneHandFrees, Laptops,
    mobilePhones, watches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        tshirts = findViewById(R.id.shirts);
        sportsTshirts = findViewById(R.id.sports_t_shirts);
        femaleDresses = findViewById(R.id.female_deresses);
        sweters = findViewById(R.id.sweaters);
        glasses = findViewById(R.id.glasses);
        hatsCaps = findViewById(R.id.hats_caps);
        walletsBagsPurses = findViewById(R.id.purses_bags_wallet);
        shoes = findViewById(R.id.shoes);
        headPhoneHandFrees = findViewById(R.id.headphones_handfree);
        Laptops  = findViewById(R.id.laptop_pc);
        mobilePhones  = findViewById(R.id.mobilephones);
        watches = findViewById(R.id.watches);

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminProductActivity.class);
                intent.putExtra("category", "tShirts");
                startActivity(intent);
            }
        });

        sportsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminProductActivity.class);
                intent.putExtra("category", "SportsTShirts");
                startActivity(intent);
            }
        });

        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminProductActivity.class);
                intent.putExtra("category", "Female Dresses");
                startActivity(intent);
            }
        });

        sweters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminProductActivity.class);
                intent.putExtra("category", "Sweters");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminProductActivity.class);
                intent.putExtra("category", "Glasses");
                startActivity(intent);
            }
        });

        hatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminProductActivity.class);
                intent.putExtra("category", "Hats Caps");
                startActivity(intent);
            }
        });

        walletsBagsPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminProductActivity.class);
                intent.putExtra("category", "Wallets Bags Purses");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminProductActivity.class);
                intent.putExtra("category", "Shoes");
                startActivity(intent);
            }
        });

        headPhoneHandFrees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminProductActivity.class);
                intent.putExtra("category", "Headphones HandFree");
                startActivity(intent);
            }
        });

        Laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminProductActivity.class);
                intent.putExtra("category", "Laptops");
                startActivity(intent);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminProductActivity.class);
                intent.putExtra("category", "Watches");
                startActivity(intent);
            }
        });

       mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminProductActivity.class);
                intent.putExtra("category", "Mobile Phones");
                startActivity(intent);
            }
        });

    }

}