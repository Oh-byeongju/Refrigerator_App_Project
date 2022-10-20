package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeActivity2 extends AppCompatActivity {

    String[] nav_api = {"LuB8lrunrQGCZGBs38ie", "AgMbIUCTG_"};
    static ArrayList<String> mlist = new ArrayList<>();

    RecyclerView recyclerView;
    RecipeAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String ex = " 레시피";
        String title = name + ex;
        mlist.clear();

        Thread thread = new Thread() {
            public void run() {
                ApiExamSearchBlog api = new ApiExamSearchBlog();
                mlist = api.getNaverSearch(name);
            }
        };
        thread.start();

        while(true){
            if (mlist.size() == 30)
                break;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe2);
        Toast.makeText(getApplicationContext(), "검색 : " + name , Toast.LENGTH_LONG).show();

        TextView textView = (TextView)findViewById(R.id.toolbar_title);
        Log.d(this.getClass().getName(), (String)textView.getText());
        textView.setText(title + " 검색 결과");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new RecipeAdapter();

        String[] contents = mlist.toArray(new String[mlist.size()]);
        for(int i=0; i<1; i++){
            adapter.addItem(new Recipe_SearchItem(contents[i],contents[i+1],contents[i+2]));
            for(int j=1; j<10; j++){
                adapter.addItem(new Recipe_SearchItem(contents[i+j*3],contents[i+1+(j*3)],contents[i+2+(j*3)]));
            }
        }

        String[] links = new String[10];       //url들만 정리
        for(int i=0; i<1; i++){
            links[i] = contents[i+2];
            for (int j =1; j<10; j++){
                links[i+j] = contents[i+2+(j*3)];
            }
        }

        adapter.addlinks(links);    //url 링크만 따로 보냄

        recyclerView.setAdapter(adapter);



    }
}
