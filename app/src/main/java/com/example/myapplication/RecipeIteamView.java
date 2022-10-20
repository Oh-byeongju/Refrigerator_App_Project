package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class RecipeIteamView extends LinearLayout {

    TextView textView, textView2;
    ImageView imageView;
    // Generate > Constructor

    public RecipeIteamView(Context context) {
        super(context);
        init(context);
    }

    public RecipeIteamView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // singer_item.xml을 inflation
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_recipe_item, this, true);

        textView = (TextView) findViewById(R.id.textView);
    }

    public void setName(String name) {
        textView.setText(name);
    }

}
