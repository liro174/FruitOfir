package com.example.fruitofir;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<FruitItem> itemList;

    public MyAdapter(List<FruitItem> itemList) {

        this.itemList = itemList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewItem;
        TextView textViewTitle;
        TextView textViewDescription;
        ImageView isFavorite;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewItem = itemView.findViewById(R.id.imageViewItem);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            isFavorite = itemView.findViewById(R.id.imageViewFavorite);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FruitItem currentItem = itemList.get(position);
        holder.imageViewItem.setImageResource(currentItem.getImageResource());
        holder.textViewTitle.setText(currentItem.getName());
        holder.textViewDescription.setText(currentItem.getDescription());




        // beginner - option
        // Set an OnClickListener on the itemView to handle clicks
        // better is use an interface for click events
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newStatus = !currentItem.getIsFavorite();
                currentItem.setFavorite(newStatus);

                // Change the heart icon
                if (newStatus) {
                    holder.isFavorite.setImageResource(R.drawable.redheart); // Full heart
                } else {
                    holder.isFavorite.setImageResource(R.drawable.emptyheart); // Empty heart
                }
                // Handle click here, you have access to currentItem or position
                Toast.makeText(v.getContext(), "Clicked: " + currentItem.getName(), Toast.LENGTH_SHORT).show();
                Log.d("Adapter", "Clicked: " + currentItem.getName());

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}