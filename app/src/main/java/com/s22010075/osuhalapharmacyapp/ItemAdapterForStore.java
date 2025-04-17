package com.s22010075.osuhalapharmacyapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapterForStore extends RecyclerView.Adapter <ItemAdapterForStore.ItemViewHolder> {

    private Context context;
    private ArrayList<DataModelClassForStore> dataListForShop;


    // constructor
    public ItemAdapterForStore(Context context, ArrayList<DataModelClassForStore> dataListForShop) {
        this.context = context;
        this.dataListForShop = dataListForShop;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        DataModelClassForStore currentItem = dataListForShop.get(position);
        holder.itemName.setText(currentItem.getName());
        String formattedPrice = String.format("%.2f", currentItem.getPrice()); // convert float price to string
        holder.itemPrice.setText("Rs. " + formattedPrice);
        holder.itemAvailability.setText(currentItem.getAvailability());
        holder.itemRatingBar.setRating(currentItem.getRating());

        // glide - load image using glide
        Glide.with(holder.itemImage.getContext())
                .load(currentItem.getImageUrl())
                .placeholder(R.color.darkBlue)
                .centerCrop() // set image to rectangular shape
                .error(R.drawable.osuhala_logo_big)
                .into(holder.itemImage);

//        // item to open
//        holder.itemCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, Item.class);
//                putDataIntoIntent(intent, currentItem);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return dataListForShop.size();
    }

    private void putDataIntoIntent(Intent intent, DataModelClassForStore data) {
        intent.putExtra("Image", data.getImageUrl());
        intent.putExtra("Name", data.getName());
        intent.putExtra("Price", data.getPrice());
        intent.putExtra("Availability", data.getAvailability());
        intent.putExtra("Rating", data.getRating());
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemName, itemPrice, itemAvailability;
        RatingBar itemRatingBar;
        CardView itemCardView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = (ImageView) itemView.findViewById(R.id.itemImageForShop);
            itemName = (TextView) itemView.findViewById(R.id.itemNameForShop);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPriceForShop);
            itemAvailability = (TextView) itemView.findViewById(R.id.itemAvailabilityForShop);
            itemRatingBar = (RatingBar) itemView.findViewById(R.id.itemRatingBarForShop);
            itemCardView = (CardView) itemView.findViewById(R.id.recyclerViewForShop);
        }
    }
}


