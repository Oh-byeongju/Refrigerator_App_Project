package com.example.myapplication;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    ArrayList<Recipe_SearchItem> items = new ArrayList<Recipe_SearchItem>();
    static String[] links = new String[10];

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Recipe_SearchItem item = items.get(position);
        holder.setItem(item);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Recipe_SearchItem item) {
        items.add(item);
    }

    public static void addlinks(String[] arr)
    {
        for(int i = 0; i < arr.length; i++)
        {
            links[i] = arr[i];
        }
    }

    public void setItems(ArrayList<Recipe_SearchItem> items) {
        this.items = items;
    }

    public Recipe_SearchItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Recipe_SearchItem item) {
        items.set(position, item);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView contentText;
        TextView linkText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            contentText = itemView.findViewById(R.id.contentText);
            linkText = itemView.findViewById(R.id.linkText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse(links[pos]));
                    v.getContext().startActivity(intent);
                    if (pos != RecyclerView.NO_POSITION) {

                        // TODO : use pos.
                    }
                }
            });
        }

        public void setItem(Recipe_SearchItem item) {
            titleText.setText(item.getTitle());
            contentText.setText(item.getDescription());
            linkText.setText(item.getLink());
            linkText.setPaintFlags(linkText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}

