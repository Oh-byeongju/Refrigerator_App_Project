package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PhGridItemViewHolder {

    public ImageView ivIcon;
    public TextView tvName, tvName2;
    public final Button delete;


    public PhGridItemViewHolder(View a_view) {
        ivIcon = a_view.findViewById(R.id.iv_icon);
        tvName = a_view.findViewById(R.id.tv_name);
        tvName2 = a_view.findViewById(R.id.tv_name2);
        delete = a_view.findViewById(R.id.btn_delete);
    }
}
