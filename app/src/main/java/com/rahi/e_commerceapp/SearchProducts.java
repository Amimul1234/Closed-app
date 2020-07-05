package com.rahi.e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rahi.e_commerceapp.Model.Products;
import com.rahi.e_commerceapp.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

public class SearchProducts extends AppCompatActivity {

    private Button SearchProductButton;
    private EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

    //This class is for searching different thing in the products

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);

        inputText = findViewById(R.id.search_product_name);
        SearchProductButton = findViewById(R.id.search_product_button);
        searchList = findViewById(R.id.product_search_result);

        searchList.setLayoutManager(new GridLayoutManager(SearchProducts.this, 2));

        SearchProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInput = inputText.getText().toString();
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(reference.orderByChild("pname").startAt(SearchInput).endAt(SearchInput+"\uF8FF"), Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

                        String thumbnail = model.getImage();//Generating thumbnail images
                        thumbnail = thumbnail.replace(".jpg", "_200x200.jpg");
                        Picasso.get().load(thumbnail).into(holder.imageView);
                        holder.textProductName.setText(model.getPname());
                        holder.textProductPrice.setText("৳ "+model.getPrice());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {   //Adding click listener to products
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SearchProducts.this, ProductDetailsActivity.class);
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}