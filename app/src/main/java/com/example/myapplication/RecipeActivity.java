package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    RecipeAdapter adapter;
    TextView tv_nr;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe1);

        editText = (EditText) findViewById(R.id.searchText);
        tv_nr = (TextView) findViewById(R.id.tv_nr);
        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayList<String> item = new ArrayList<String>();
        ArrayList<String> f_name = new ArrayList<String>();
        String ps;
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor res = db.p_select();

        while(res.moveToNext()){
            item.add(res.getString(1));
        }
        Recommend_recipe rr = new Recommend_recipe();
        f_name = rr.Return_recipe(item);
        if(f_name.size() == 0)
            tv_nr.setVisibility(View.VISIBLE);
        // 어댑터 안에 데이터 담기
        RecipeAdapter adapter = new RecipeAdapter();

        for(int i=0;i<f_name.size();i++){
            adapter.addItem(new RecipeItem(f_name.get(i)));
        }

        // 여기에 데이터 개수 받아서 처리
        // 0개면 이거 ?
        //adapter.addItem(new RecipeItem(" ", "추천드릴 레시피가 없습니다. ", R.drawable.blank ));
        //1개이상이면 여기서  한줄씩 추가
        //adapter.addItem(new RecipeItem("김치찌개", "설명 ~~~~", R.drawable.android1));
        //adapter.addItem(new RecipeItem("된장찌개", "설명 ~~~~", R.drawable.android2));
        //adapter.addItem(new RecipeItem("두루치기", "설명 ~~~~", R.drawable.android3));


        // 리스트 뷰에 어댑터 설정
        listView.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.button);

        // 이벤트 처리 리스너 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecipeItem item = (RecipeItem) adapter.getItem(position);
                editText.setText(item.getName());
                button.performClick();
                //Toast.makeText(getApplicationContext(), "선택 :" + item.getName(), Toast.LENGTH_LONG).show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();


                adapter.notifyDataSetChanged();


                Intent intent = new Intent(RecipeActivity.this, RecipeActivity2.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }


        class RecipeAdapter extends BaseAdapter {
            ArrayList<RecipeItem> items = new ArrayList<RecipeItem>();


            // Generate > implement methods
            @Override
            public int getCount() {
                return items.size();
            }

            public void addItem(RecipeItem item) {
                items.add(item);
            }

            @Override
            public Object getItem(int position) {
                return items.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // 뷰 객체 재사용
                RecipeIteamView view = null;
                if (convertView == null) {
                    view = new RecipeIteamView(getApplicationContext());
                } else {
                    view = (RecipeIteamView) convertView;
                }

                RecipeItem item = items.get(position);

                view.setName(item.getName());



                return view;
            }
        }
    }



