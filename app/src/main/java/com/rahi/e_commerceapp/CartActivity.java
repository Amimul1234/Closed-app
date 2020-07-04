package com.rahi.e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rahi.e_commerceapp.Model.Cart;
import com.rahi.e_commerceapp.Prevalent.Prevalent;
import com.rahi.e_commerceapp.ViewHolder.CartViewHolder;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount;

    private int totalPrice;

    //This is for navigating to cart menu......

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn = findViewById(R.id.next_button);
        txtTotalAmount = findViewById(R.id.total_price);

        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtTotalAmount.setText("Total price = "+String.valueOf(totalPrice));

                Intent intent = new Intent(getApplicationContext(), ConfirmFinalOrder.class);
                intent.putExtra("Total Price", String.valueOf(totalPrice));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User View")
                .child(Prevalent.currentOnlineUser.getPhone()).child("Products"), Cart.class).build();


        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder holder, int position, @NonNull final Cart model) {

                //Updating the cart items single card view

                String cart_product_image = model.getImage();
                cart_product_image = cart_product_image.replace(".jpg", "_200x200.jpg");
                Picasso.get().load(cart_product_image).into(holder.cart_image);
                holder.txtProductName.setText(model.getPname());
                holder.txtProductQuantity.setText("৳ "+model.getPrice()+" X "+model.getQuantity());
                int one_product_total_price = Integer.parseInt(model.getPrice()) * Integer.parseInt(model.getQuantity());
                holder.txtProductPrice.setText("৳ "+String.valueOf(one_product_total_price));
                holder.numberButton.setNumber(model.getQuantity());
                totalPrice += one_product_total_price;

                //If the user want to change the added items of the cart using elegant button

                holder.numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                    @Override
                    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                        int quantity = Integer.parseInt(holder.numberButton.getNumber());
                        model.setQuantity(String.valueOf(quantity));
                        holder.txtProductQuantity.setText("৳ "+model.getPrice()+" X "+model.getQuantity());
                        int one_product_total_price = Integer.parseInt(model.getPrice()) * Integer.parseInt(model.getQuantity());
                        holder.txtProductPrice.setText("৳ "+String.valueOf(one_product_total_price));
                    }
                });


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);//For giving product description to the user when clicks on a cart item
                        intent.putExtra("pid", model.getPid());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}