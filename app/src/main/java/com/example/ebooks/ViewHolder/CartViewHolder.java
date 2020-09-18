package com.example.ebooks.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebooks.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductname, txtProductprice, txtProductquantity;
    private AdapterView.OnItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductname = itemView.findViewById(R.id.pid);
        txtProductprice = itemView.findViewById(R.id.price);
        txtProductquantity = itemView.findViewById(R.id.quantity);
    }


    @Override
    public void onClick(View view) {

        itemClickListener.onItemClick(getAdapterPosition(), view, false);

    }

    public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


}
