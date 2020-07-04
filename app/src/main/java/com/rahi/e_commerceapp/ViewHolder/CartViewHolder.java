package com.rahi.e_commerceapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.rahi.e_commerceapp.Interface.ItemClickListener;
import com.rahi.e_commerceapp.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    public ImageView cart_image;
    private ItemClickListener itemClickListener;
    public ElegantNumberButton numberButton;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        cart_image = itemView.findViewById(R.id.cart_product_image);
        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
        numberButton = itemView.findViewById(R.id.cart_item_change_btn);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
